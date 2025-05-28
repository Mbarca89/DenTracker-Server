package com.mbarca89.DenTracker.service.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    // Guarda un único archivo en la carpeta de un paciente
    public String savePatientFile(String patientFolder, MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
        Path patientDir = Paths.get(uploadDir).resolve(patientFolder);

        if (!Files.exists(patientDir)) {
            Files.createDirectories(patientDir);
        }

        Path targetPath = patientDir.resolve(filename);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        return filename;
    }

    // Guarda varios archivos (ej: full, thumb, HD) en una carpeta de un paciente
    public List<String> savePatientFiles(String patientFolder, MultipartFile[] files) throws IOException {
        Path patientDir = Paths.get(uploadDir).resolve(patientFolder);

        if (!Files.exists(patientDir)) {
            Files.createDirectories(patientDir);
        }

        List<String> paths = new ArrayList<>();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                throw new RuntimeException("Archivo vacío");
            }
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String newFilename = timestamp + "_" + originalFilename;
            Path targetPath = patientDir.resolve(newFilename);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            paths.add(targetPath.toString());
        }
        return paths;
    }

    // Guarda un archivo a partir de bytes[] (para el ImageCompressor)
    public String saveBytes(String patientFolder, byte[] data, String filename) throws IOException {
        Path patientDir = Paths.get(uploadDir).resolve(patientFolder);
        if (!Files.exists(patientDir)) {
            Files.createDirectories(patientDir);
        }
        Path targetPath = patientDir.resolve(filename);
        Files.write(targetPath, data);
        return targetPath.toString();
    }

    public Path loadPatientFile(String patientFolder, String filename) {
        return Paths.get(uploadDir).resolve(patientFolder).resolve(filename);
    }

    public String getPatientFolder(Long clientId, Long patientId, String subfolder) {
        return "client_" + clientId + "/patient_" + patientId + "/" + subfolder;
    }

    public void deleteAllPatientFiles(Long clientId, Long patientId) {
        Path patientDir = Paths.get(uploadDir).resolve("client_" + clientId).resolve("patient_" + patientId);

        if (Files.exists(patientDir)) {
            try {
                Files.walk(patientDir)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(java.io.File::delete);
            } catch (IOException e) {
                throw new RuntimeException("Error al eliminar los archivos del paciente: " + e.getMessage());
            }
        }
    }

    public List<String> saveFiles(MultipartFile[] files, String patientFolder) {
        List<String> savedFilePaths = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                String savedFile = savePatientFile(patientFolder, file);
                savedFilePaths.add(savedFile);
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar archivo: " + file.getOriginalFilename(), e);
            }
        }
        return savedFilePaths;
    }

}

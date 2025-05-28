package com.mbarca89.DenTracker.service.patient.impl;

import com.mbarca89.DenTracker.dto.response.patient.GalleryResponseDto;
import com.mbarca89.DenTracker.entity.patient.Gallery;
import com.mbarca89.DenTracker.entity.patient.Patient;
import com.mbarca89.DenTracker.exception.ResourceNotFoundException;
import com.mbarca89.DenTracker.mapper.patient.GalleryMapper;
import com.mbarca89.DenTracker.repository.patient.GalleryRepository;
import com.mbarca89.DenTracker.repository.patient.PatientRepository;
import com.mbarca89.DenTracker.service.file.FileStorageService;
import com.mbarca89.DenTracker.service.patient.GalleryService;
import com.mbarca89.DenTracker.utils.ImageCompressor;
import com.mbarca89.DenTracker.utils.Images;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {

    private final GalleryRepository galleryRepository;
    private final PatientRepository patientRepository;
    private final ImageCompressor imageCompressor;
    private final FileStorageService fileStorageService;
    private final GalleryMapper galleryMapper;

    @Transactional
    @Override
    public void uploadImage(Long patientId, String imageName, MultipartFile file) throws Exception {
        Gallery gallery = galleryRepository.findByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la galería del paciente"));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("El paciente no existe"));

        String patientName = (patient.getLastName() + "_" + patient.getFirstName()).replaceAll("[^a-zA-Z0-9_-]", "_");

        Images images = imageCompressor.compressImage(file.getBytes(), true, imageName, patientName);

        List<String> thumbnails = new ArrayList<>(List.of(images.getPaths().get(1)));
        List<String> fullImages = new ArrayList<>(List.of(images.getPaths().get(0)));
        List<String> HDImages = new ArrayList<>(List.of(images.getPaths().get(images.getPaths().size() - 1)));

        String baseName = getBaseName(imageName);
        if (baseName == null) {
            throw new IllegalArgumentException("Error al cargar la imagen: tipo no reconocido");
        }

        try {
            Method getThumbMethod = Gallery.class.getMethod("get" + baseName + "_thumb");
            Method getFullMethod = Gallery.class.getMethod("get" + baseName);
            Method getHDMethod = Gallery.class.getMethod("get" + baseName + "_HD");

            Method setThumbMethod = Gallery.class.getMethod("set" + baseName + "_thumb", List.class);
            Method setFullMethod = Gallery.class.getMethod("set" + baseName, List.class);
            Method setHDMethod = Gallery.class.getMethod("set" + baseName + "_HD", List.class);

            thumbnails.addAll((List<String>) getThumbMethod.invoke(gallery));
            fullImages.addAll((List<String>) getFullMethod.invoke(gallery));
            HDImages.addAll((List<String>) getHDMethod.invoke(gallery));

            setThumbMethod.invoke(gallery, thumbnails);
            setFullMethod.invoke(gallery, fullImages);
            setHDMethod.invoke(gallery, HDImages);

        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la galería", e);
        }

        galleryRepository.save(gallery);
    }

    @Override
    public GalleryResponseDto getAllThumbsByPatientId(Long patientId, String study) {
        Gallery gallery = galleryRepository.findByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la galería del paciente"));

        Map<String, List<String>> thumbs = getThumbsMap(gallery, study);

        GalleryResponseDto dto = galleryMapper.toDto(gallery);
        dto.setImages(thumbs);
        return dto;
    }

    @Override
    public GalleryResponseDto getSingleGalleryByPatientId(Long patientId, String study) {
        Gallery gallery = galleryRepository.findByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la galería del paciente"));

        Map<String, List<String>> images = getSingleGalleryMap(gallery, study);

        GalleryResponseDto dto = galleryMapper.toDto(gallery);
        dto.setImages(images);
        return dto;
    }

    private Map<String, List<String>> getThumbsMap(Gallery gallery, String study) {
        Map<String, List<String>> thumbsMap = new HashMap<>();
        switch (study) {
            case "Extraorales":
                thumbsMap.put("extraoralFront_thumb", gallery.getExtraoralFront_thumb());
                thumbsMap.put("extraoralMax_thumb", gallery.getExtraoralMax_thumb());
                thumbsMap.put("extraoralLeft_thumb", gallery.getExtraoralLeft_thumb());
                thumbsMap.put("extraoralRight_thumb", gallery.getExtraoralRight_thumb());
                break;
            case "Intraorales":
                thumbsMap.put("intraoralFront_thumb", gallery.getIntraoralFront_thumb());
                thumbsMap.put("intraoralBlackBackground_thumb", gallery.getIntraoralBlackBackground_thumb());
                thumbsMap.put("intraoralLeft_thumb", gallery.getIntraoralLeft_thumb());
                thumbsMap.put("intraoralRight_thumb", gallery.getIntraoralRight_thumb());
                break;
            case "Arco dentario":
                thumbsMap.put("arcTop_thumb", gallery.getArcTop_thumb());
                thumbsMap.put("arcBottom_thumb", gallery.getArcBottom_thumb());
                break;
            case "Zona a tratar":
                thumbsMap.put("oclusal_thumb", gallery.getOclusal_thumb());
                thumbsMap.put("vestibular_thumb", gallery.getVestibular_thumb());
                break;
            case "Digitalización":
                thumbsMap.put("panoramic_thumb", gallery.getPanoramic_thumb());
                thumbsMap.put("xray_thumb", gallery.getXray_thumb());
                break;
            default:
                throw new ResourceNotFoundException("Estudio no encontrado");
        }
        return thumbsMap;
    }

    private Map<String, List<String>> getSingleGalleryMap(Gallery gallery, String study) {
        Map<String, List<String>> galleryMap = new HashMap<>();

        String baseName = getBaseName(study);
        if (baseName == null) {
            throw new ResourceNotFoundException("Estudio no encontrado");
        }

        try {
            Method getThumbMethod = Gallery.class.getMethod("get" + baseName + "_thumb");
            Method getFullMethod = Gallery.class.getMethod("get" + baseName);
            Method getHDMethod = Gallery.class.getMethod("get" + baseName + "_HD");

            List<String> thumbnails = (List<String>) getThumbMethod.invoke(gallery);
            List<String> originals = (List<String>) getFullMethod.invoke(gallery);
            List<String> hdImages = (List<String>) getHDMethod.invoke(gallery);

            galleryMap.put("thumbnail", thumbnails);
            galleryMap.put("original", originals);
            galleryMap.put("HD", hdImages);

        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la galería", e);
        }

        return galleryMap;
    }

    private String getBaseName(String name) {
        switch (name) {
            case "Extraoral-frente": return "ExtraoralFront";
            case "Extraoral-izquierdo": return "ExtraoralLeft";
            case "Extraoral-derecho": return "ExtraoralRight";
            case "Extraoral-sonrisa-máxima": return "ExtraoralMax";
            case "Intraoral-frente": return "IntraoralFront";
            case "Intraoral-fondo-negro": return "IntraoralBlackBackground";
            case "Intraoral-izquierdo": return "IntraoralLeft";
            case "Intraoral-derecho": return "IntraoralRight";
            case "Arco-dentario-superior": return "ArcTop";
            case "Arco-dentario-inferior": return "ArcBottom";
            case "Zona-a-tratar-oclusal": return "Oclusal";
            case "Zona-a-tratar-vestibular": return "Vestibular";
            case "Digitalización-panorámica": return "Panoramic";
            case "Digitalización-radiografías": return "Xray";
            default: return null;
        }
    }
}

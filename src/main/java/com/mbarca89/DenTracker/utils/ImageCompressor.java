package com.mbarca89.DenTracker.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.mbarca89.DenTracker.service.file.FileStorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageCompressor {

    private final FileStorageService fileStorageService;

    /**
     * Procesa una imagen: la rota (según metadatos), la convierte a JPG y guarda 3 versiones (full, thumb, HD).
     */
    @Transactional
    public Images compressImage(byte[] imageData, boolean saveFile, String fileName, String patientName) throws IOException {
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageData));

        // Rotar según EXIF
        BufferedImage rotatedImage = rotateImage(originalImage, imageData);

        // Redimensionar
        BufferedImage fullImage = resizeImage(rotatedImage, 640, 360);
        BufferedImage thumbImage = resizeImage(rotatedImage, 320, 184);

        // Convertir a JPG
        byte[] fullImageBytes = convertToJPG(fullImage);
        byte[] thumbBytes = convertToJPG(thumbImage);

        if (saveFile) {
            String subDir = patientName + "/Imagenes/" + fileName;
            List<String> paths = saveImages(fullImageBytes, thumbBytes, imageData, subDir, fileName);
            return new Images(fullImageBytes, thumbBytes, paths);
        } else {
            return new Images(fullImageBytes, thumbBytes);
        }
    }

    private BufferedImage rotateImage(BufferedImage originalImage, byte[] imageData) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(new ByteArrayInputStream(imageData));
            Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

            if (directory != null && directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) {
                int orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
                return switch (orientation) {
                    case 3 -> Scalr.rotate(originalImage, Scalr.Rotation.CW_180);
                    case 6 -> Scalr.rotate(originalImage, Scalr.Rotation.CW_90);
                    case 8 -> Scalr.rotate(originalImage, Scalr.Rotation.CW_270);
                    default -> originalImage;
                };
            }
        } catch (Exception ignored) {
            // Si no se puede leer metadatos, no rotamos
        }
        return originalImage;
    }

    private BufferedImage resizeImage(BufferedImage image, int targetWidth, int targetHeight) {
        return Scalr.resize(image, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, targetWidth, targetHeight);
    }

    private byte[] convertToJPG(BufferedImage image) throws IOException {
        // Convertir a RGB si tiene transparencia
        if (image.getType() == BufferedImage.TYPE_INT_ARGB || image.getType() == BufferedImage.TYPE_4BYTE_ABGR) {
            BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = newImage.createGraphics();
            g.setComposite(AlphaComposite.Src);
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, image.getWidth(), image.getHeight());
            g.drawImage(image, 0, 0, null);
            g.dispose();
            image = newImage;
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext()) {
            throw new IllegalStateException("No se encontraron escritores de imágenes JPG");
        }

        ImageWriter writer = writers.next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.85f);

        try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputStream)) {
            writer.setOutput(ios);
            writer.write(null, new IIOImage(image, null, null), param);
        } finally {
            writer.dispose();
        }
        return outputStream.toByteArray();
    }

    private List<String> saveImages(byte[] fullImage, byte[] thumbnail, byte[] originalImage, String subDir, String imageName) throws IOException {
        MultipartFile fullImageFile = new CustomMultipartFile(fullImage, "fullImage", imageName + "_full.jpg", "image/jpeg");
        MultipartFile thumbFile = new CustomMultipartFile(thumbnail, "thumbnail", imageName + "_thumb.jpg", "image/jpeg");
        MultipartFile hdFile = new CustomMultipartFile(originalImage, "HD", imageName + "_HD.jpg", "image/jpeg");

        return fileStorageService.savePatientFiles(subDir, new MultipartFile[]{fullImageFile, thumbFile, hdFile});
    }
}

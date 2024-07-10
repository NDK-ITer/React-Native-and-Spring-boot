package com.example.business.application.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public class FileMethod {
    public static String setDefaultImage(
            String newFileName,
            String fileName
        ) {
        Path source = Paths.get(String.format("src/main/java/com/example/business/application/images/%s", fileName));
        Path destination = Paths.get(String.format("src/main/resources/public/%s", newFileName));
        try {
            // Create if not exist
            Files.createDirectories(destination.getParent());
            // Copy and change file name
            Files.copy(source, destination);
            return newFileName;
        } catch (IOException e) {
            return null;
        }
    }

    public static String saveMultipartFile(MultipartFile file, String newFileName) {
        Path destination = Paths.get(String.format("src/main/resources/public/%s", newFileName));
        try {
            Files.createDirectories(destination.getParent());
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            return newFileName;
        } catch (IOException e) {
            return null;
        }
    }
}

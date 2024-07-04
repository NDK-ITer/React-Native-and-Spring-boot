package com.example.business.application.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class FileMethod {
    public static String copyAndRenameImage(
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
}

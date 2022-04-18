package com.experiment.streaming.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.UUID;

public class ContentUtils {
    private static final HashMap<String, String> contentTypeExtensionMap;


    static {
        contentTypeExtensionMap = new HashMap<>();
        contentTypeExtensionMap.put("video/mp4", "mp4");
        contentTypeExtensionMap.put("image/png","png");
        contentTypeExtensionMap.put("image/jpeg","jpeg");
        if (!Files.exists(Paths.get(PathUtils.UPLOAD_RAW_VIDEO_DIR))) {
            try {
                Files.createDirectories(Paths.get(PathUtils.UPLOAD_RAW_VIDEO_DIR));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!Files.exists(Paths.get(PathUtils.UPLOAD_IMAGE_DIR))) {
            try {
                Files.createDirectories(Paths.get(PathUtils.UPLOAD_IMAGE_DIR));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getExtensionByContentType(String contentType) {
        return contentTypeExtensionMap.get(contentType);
    }

    public static String generateRandomName(String contentType){
        return UUID.randomUUID() + "." + getExtensionByContentType(contentType);
    }

    public static void copyContentToServer(File file, Path filePath) throws IOException{
        if(!Files.exists(filePath))
            Files.createFile(filePath);
        Files.write(filePath,Files.readAllBytes(file.toPath()));

    }

    public static File convertToFile(MultipartFile multipartFile){
        File convertedFile = new File(multipartFile.getName());
        try {
            FileOutputStream outputStream = new FileOutputStream(convertedFile);
            outputStream.write( multipartFile.getBytes());
            outputStream.close();
            return convertedFile;
        } catch ( IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

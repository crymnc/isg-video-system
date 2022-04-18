package com.experiment.streaming.utils.uploader;

import com.experiment.streaming.utils.ContentUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public abstract class Uploader<T extends Uploader> {
    protected File file;
    protected Path uploadPath;

    public void upload() throws IOException{
        ContentUtils.copyContentToServer(file, uploadPath);
    }

    public static VideoUploader getVideoUploader(MultipartFile file) throws IOException{
        return VideoUploader.getInstance(file);
    }

    public static ImageUploader getImageUploader(MultipartFile file) throws IOException{
        return ImageUploader.getInstance(file);
    }

    public Path getUploadPath() {
        return uploadPath;
    }
}

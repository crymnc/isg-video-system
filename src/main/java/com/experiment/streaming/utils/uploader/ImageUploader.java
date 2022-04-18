package com.experiment.streaming.utils.uploader;

import com.experiment.streaming.utils.ContentUtils;
import com.experiment.streaming.utils.PathUtils;
import org.imgscalr.Scalr;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageUploader extends Uploader<ImageUploader>{
    private String contentName;
    private Path shortUploadPath;
    private String contentType;

    private ImageUploader(MultipartFile multipartFile){
        this.file = ContentUtils.convertToFile(multipartFile);
        this.contentType = multipartFile.getContentType();
        this.contentName = ContentUtils.generateRandomName(multipartFile.getContentType());
        this.uploadPath = Paths.get(PathUtils.UPLOAD_IMAGE_DIR + contentName);
        this.shortUploadPath = Paths.get(PathUtils.UPLOAD_IMAGE_SHORT_DIR + contentName);
    }

    protected static ImageUploader getInstance(MultipartFile file) throws IOException {
        return new ImageUploader(file);
    }

    @Override
    public void upload() throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(file));
        BufferedImage resizedImage = simpleResizeImage(bufferedImage,300);
        File resizedFile = new File(uploadPath.toString());
        ImageIO.write(resizedImage,ContentUtils.getExtensionByContentType(contentType),resizedFile);
        resizedImage.flush();
        bufferedImage.flush();
        this.file = resizedFile;
        super.upload();
    }

    BufferedImage simpleResizeImage(BufferedImage originalImage, int targetWidth) {
        return Scalr.resize(originalImage, targetWidth);
    }

    public Path getShortUploadPath(){
        return this.shortUploadPath;
    }

    public String getContentName(){return this.contentName;}

}

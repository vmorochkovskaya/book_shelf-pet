package org.example.app.service;

import liquibase.util.file.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Service
public class ResourceStorage {

    @Value("${upload.path}")
    String uploadPath;

    public String saveNewBookImage(MultipartFile file, String slug) throws IOException {
        String resourceURI= null;
        if(!file.isEmpty()){
            if(!new File(uploadPath).exists()){
                Logger.getLogger(this.getClass().getSimpleName()).info("Creating image folder in " + uploadPath);
                Files.createDirectories(Paths.get(uploadPath));
            }
            String coverName = slug + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            Path coverPath = Paths.get(uploadPath, coverName);
            resourceURI = "/covers/" + coverName;
            file.transferTo(coverPath);// uploading user file here
        }

        return resourceURI;
    }
}

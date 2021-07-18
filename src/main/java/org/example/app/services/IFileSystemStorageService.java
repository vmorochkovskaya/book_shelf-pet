package org.example.app.services;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface IFileSystemStorageService {
    void saveFile(MultipartFile file);
    void loadFile(String fileName, HttpServletResponse response);
}

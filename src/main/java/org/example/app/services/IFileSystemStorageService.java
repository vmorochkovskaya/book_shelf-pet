package org.example.app.services;

import org.example.web.dto.FileData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IFileSystemStorageService {
    List<FileData> getAllFiles();
    void saveFile(MultipartFile file);
    void loadFile(String fileName, HttpServletResponse response);
}

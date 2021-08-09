package org.example.app.service;

import org.example.web.dto.FileDataDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IFileSystemStorageService {
    List<FileDataDto> getAllFiles();
    void saveFile(MultipartFile file);
    void loadFile(String fileName, HttpServletResponse response);
}

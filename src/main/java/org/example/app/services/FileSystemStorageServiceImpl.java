package org.example.app.services;

import org.example.web.dto.FileData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.example.app.exceptions.FileNotFoundException;

import javax.servlet.http.HttpServletResponse;


@Service
public class FileSystemStorageServiceImpl implements IFileSystemStorageService {
    private final Path dirLocation;

    @Autowired
    public FileSystemStorageServiceImpl() {
        this.dirLocation = Paths.get(System.getProperty("catalina.home") + File.separator + "external_uploads").
                toAbsolutePath()
                .normalize();
    }

    @Override
    public List<FileData> getAllFiles() {
        File dir = new File(this.dirLocation.toString());
        if (!dir.exists()) {
            return new ArrayList();
        }
        return Stream.of(dir.listFiles())
                .filter(file -> !file.isDirectory())
                .map(f -> new FileData(f.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public void saveFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new org.example.app.exceptions.FileNotFoundException(e.getMessage());
        }

        //create dir
        File dir = new File(this.dirLocation.toString());
        if (!dir.exists()) {
            dir.mkdirs();
        }

        //create file
        File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(serverFile))) {
            bos.write(bytes);
        } catch (IOException e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }

    @Override
    public void loadFile(String fileName, HttpServletResponse response) {
        String fullPath = this.dirLocation.toString() + File.separator + fileName;
        Path file = Paths.get(fullPath).
                toAbsolutePath();
        try {
            if (Files.exists(file)) {
                response.addHeader("Content-Disposition", "attachment; filename=" + fileName);

                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } else {
                throw new org.example.app.exceptions.FileNotFoundException("File [" + fileName + "] doesn't exist on server");
            }
            // get output stream of the response
            try (OutputStream outStream = response.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
// write bytes read from the input stream into the output stream
                try (FileInputStream fi = new FileInputStream(fullPath)) {
                    while (fi.available() > 0) {
                        bytesRead = fi.read(buffer);
                        outStream.write(buffer, 0, bytesRead);
                    }
                }
            }
        } catch (IOException e) {
            throw new org.example.app.exceptions.FileNotFoundException(e.getMessage());
        }
    }
}

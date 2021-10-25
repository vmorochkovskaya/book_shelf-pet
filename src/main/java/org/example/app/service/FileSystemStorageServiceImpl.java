package org.example.app.service;

import org.apache.log4j.Logger;
import org.example.web.dto.FileDataDto;
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

import org.example.app.exception.FileNotFoundException;

import javax.servlet.http.HttpServletResponse;


@Service
public class FileSystemStorageServiceImpl implements IFileSystemStorageService {
    private final Path dirLocation;
    private final Logger logger = Logger.getLogger(FileSystemStorageServiceImpl.class);
    private static final int AMOUNT_OF_BYTES_TO_READ = 4096;

    @Autowired
    public FileSystemStorageServiceImpl() {
        this.dirLocation = Paths.get(System.getProperty("catalina.home") + File.separator + "external_uploads").
                toAbsolutePath()
                .normalize();
    }

    @Override
    public List<FileDataDto> getAllFiles() {
        File dir = new File(this.dirLocation.toString());
        if (!dir.exists()) {
            return new ArrayList<>();
        }

        File[] dirFiles = dir.listFiles();
        if (dirFiles != null) {
            return Stream.of(dirFiles)
                    .filter(file -> !file.isDirectory())
                    .map(f -> new FileDataDto(f.getName()))
                    .collect(Collectors.toList());
        } else {
            logger.warn(String.format("Something went wrong while trying to find files in  %1$s", dir));
            return new ArrayList<>();
        }
    }

    @Override
    public void saveFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new org.example.app.exception.FileNotFoundException(e.getMessage());
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
                throw new org.example.app.exception.FileNotFoundException("File [" + fileName + "] doesn't exist on server");
            }
            // get output stream of the response
            try (OutputStream outStream = response.getOutputStream()) {
                byte[] buffer = new byte[AMOUNT_OF_BYTES_TO_READ];
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
            throw new org.example.app.exception.FileNotFoundException(e.getMessage());
        }
    }
}

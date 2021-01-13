package com.example.multiplefileload.services;

import com.example.multiplefileload.services.apis.FileServiceAPI;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class FileServiceImpl implements FileServiceAPI {

    private final Path rootFolder = Paths.get("uploads");


    @Override
    public void saveFile(MultipartFile file) throws IOException {
        Files.copy(file.getInputStream(), this.rootFolder.resolve(Objects.requireNonNull(file.getOriginalFilename())));
    }

    @Override
    public Resource loadFile(String name) throws Exception {
        Path file = rootFolder.resolve(name);
        return new UrlResource(file.toUri());
    }

    @Override
    public void saveListFiles(List<MultipartFile> files) throws IOException {
        for (MultipartFile file : files) {
            saveFile(file);
        }
    }

    @Override
    public Stream<Path> loadAllFiles() throws Exception {
        return Files.walk(rootFolder, 1)
                .filter(path -> !path.equals(rootFolder))
                .map(rootFolder::relativize);
    }
}

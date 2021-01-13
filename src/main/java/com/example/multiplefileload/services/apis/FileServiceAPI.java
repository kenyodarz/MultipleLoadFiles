package com.example.multiplefileload.services.apis;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface FileServiceAPI {

    void saveFile(MultipartFile file) throws Exception;

    Resource loadFile(String name) throws Exception;

    void saveListFiles(List<MultipartFile> files) throws Exception;

    Stream<Path> loadAllFiles() throws Exception;

}

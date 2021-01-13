package com.example.multiplefileload.controllers;

import com.example.multiplefileload.messages.Response;
import com.example.multiplefileload.models.File;
import com.example.multiplefileload.services.apis.FileServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
public class FileRestController {

    private final FileServiceAPI serviceAPI;


    @Autowired
    public FileRestController(FileServiceAPI serviceAPI) {
        this.serviceAPI = serviceAPI;
    }

    @PostMapping("/upload")
    public ResponseEntity<Response> uploadFiles(@RequestParam("files") List<MultipartFile> files) throws Exception {
        serviceAPI.saveListFiles(files);

        return ResponseEntity.ok(new Response("Los Archivos fueron Cargados Correctamente en el servidor"));
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws Exception {
        Resource resource = serviceAPI.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("all")
    public ResponseEntity<List<File>> getFiles() throws Exception {
        List<File> files = serviceAPI.loadAllFiles().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder.fromMethodName(
                    FileRestController.class,
                    "getFile",
                    path.getFileName().toString()).build().toString();
            return new File(filename, url);
        }).collect(Collectors.toList());
        return ResponseEntity.ok(files);
    }

}

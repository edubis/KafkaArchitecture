package com.example.kafka.architecture.icompras.faturamento.controller;


import com.example.kafka.architecture.icompras.faturamento.bucket.BucketFile;
import com.example.kafka.architecture.icompras.faturamento.bucket.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/bucket")
@RequiredArgsConstructor
public class BucketController {

    private final BucketService service;

    @PostMapping
    public ResponseEntity<String> uploadFile(
            @RequestParam("file")MultipartFile file
            ){
        try(InputStream is = file.getInputStream()){
            MediaType type = MediaType.parseMediaType(file.getContentType());
            var bucketFile = new BucketFile(file.getOriginalFilename(), is, type, file.getSize());
            service.upload(bucketFile);

            return ResponseEntity.ok("Upload de Arquivo realizado com sucesso!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public ResponseEntity<String> getUrl(@RequestParam String filename){
        try{
            String url = service.getUrl(filename);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

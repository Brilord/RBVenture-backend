package org.example.venture.controllers;


import org.example.venture.model.BlogNode;
import org.example.venture.repository.FileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/blognode")
public class BlogNodeController {

    private FileRepository fileRepository;

    public BlogNodeController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @PostMapping
    public int add(@RequestBody BlogNode question) {
        try {
            return fileRepository.add(question);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public List<BlogNode> findAll() {
        try {
            return fileRepository.findAllBlogNodes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/search")
    public List<BlogNode> find(@RequestParam String answer) {
        try {
            return fileRepository.find(answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public BlogNode get(@PathVariable int id) {
        try {
            return fileRepository.get(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{id}/image")
    public boolean addImage(@PathVariable int id,
                               @RequestParam MultipartFile file) {
        try {
            return fileRepository.addImagePng(id, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/{id}/image")
    public boolean updateImage(@PathVariable int id, @RequestParam MultipartFile file) {
        try {
            return fileRepository.updateImagePng(id, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<?> getImage(@PathVariable int id) {
        try {
            byte[] image = fileRepository.getImage(id);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BlogNode> delete(@PathVariable int id) {
        try {
            BlogNode existingBlogNode = fileRepository.get(id);
            if (existingBlogNode == null) {
                return ResponseEntity.notFound().build();
            }
            fileRepository.delete(id);
            return ResponseEntity.ok(existingBlogNode);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

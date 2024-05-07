package org.example.venture.controllers;

import org.example.venture.model.Blog;
import org.example.venture.repository.FileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/blog")
public class BlogController {

    private final FileRepository fileRepository; // Assuming this is your data access abstraction

    // Constructor with dependency injection for the repository
    public BlogController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @PostMapping
    public int addBlog(@RequestBody Blog blog) {
        try {
            return fileRepository.addBlog(blog);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public List<Blog> findAllBlogs() {
        try {
            return fileRepository.getAllBlogs();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public Blog getBlogById(@PathVariable int id) {
        try {
            return fileRepository.getBlog(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable int id, @RequestBody Blog updatedBlog) {
        try {
            Blog existingBlog = fileRepository.getBlog(id);
            if (existingBlog == null) {
                return ResponseEntity.notFound().build();
            }
            if (updatedBlog.getTitle() != null) {
                existingBlog.setTitle(updatedBlog.getTitle());
            }
            if (updatedBlog.getBlogNodeIds() != null) {
                existingBlog.setBlogNodeIds(updatedBlog.getBlogNodeIds());
            }

            fileRepository.updateBlog(existingBlog);
            return ResponseEntity.ok(existingBlog);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

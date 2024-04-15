package edu.iu.c322.test3.controllers;

import edu.iu.c322.test3.model.Quiz;
import edu.iu.c322.test3.repository.FileRepository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/quizzes")
public class QuizController {

    private FileRepository fileRepository;

    public QuizController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    @PostMapping
    public int add(@RequestBody Quiz quiz) {
        try {
            return fileRepository.add(quiz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public List<Quiz> findAll() {
        try {
            return fileRepository.findAllQuizzes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public int update(@PathVariable int id, @RequestBody Quiz quiz) {
        try {
            return fileRepository.update(id, quiz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public Quiz get(@PathVariable int id) {
        try {
            return fileRepository.getTheQuiz(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

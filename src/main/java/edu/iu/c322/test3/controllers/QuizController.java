package edu.iu.c322.test3.controllers;

import edu.iu.c322.test3.model.Quiz;
import edu.iu.c322.test3.repository.FileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/quizzes")
public class QuizController {

    private final FileRepository fileRepository; // Assuming this is your data access abstraction

    // Constructor with dependency injection for the repository
    public QuizController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    @PostMapping
    public int addQuiz(@RequestBody Quiz quiz) {
        try {
            return fileRepository.addQuiz(quiz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping
    public List<Quiz> findAllQuizzes(Quiz quiz) {
        try {
            return fileRepository.getAllQuizzes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //    @GetMapping("/{id}")
//    public ResponseEntity<Quiz> getQuizById(@PathVariable int id) {
//        // Assuming you have a method to find a quiz by ID
//        Optional<Quiz> quiz = fileRepository.getQuiz(id);
//        if (quiz.isPresent()) {
//            return new ResponseEntity<>(quiz.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    @GetMapping("/{id}")
    public Quiz getQuizById(@PathVariable int id) {
        try {
            return fileRepository.getQuiz(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable int id, @RequestBody Quiz updatedQuiz) {
        try {
            Quiz existingQuiz = fileRepository.getQuiz(id);
            if (existingQuiz == null) { return ResponseEntity.notFound().build();}
            if (updatedQuiz.getTitle() != null) {existingQuiz.setTitle(updatedQuiz.getTitle());}
            if (updatedQuiz.getQuestionIds() != null) {
                existingQuiz.setQuestionIds(updatedQuiz.getQuestionIds());}


            fileRepository.updateQuiz(existingQuiz);
            return ResponseEntity.ok(existingQuiz);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

package org.example.venture.repository;


import org.example.venture.model.BlogNode;
import org.example.venture.model.Blog;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class FileRepository {
    private String IMAGES_FOLDER_PATH = "blog/blognode/images";
    private static final String NEW_LINE = System.lineSeparator();
    private static final String QUESTION_DATABASE_NAME = "blog/blognode.txt";
    private static final String QUIZ_DATABASE_NAME = "blog/blognode.txt";
    private static final String QUIZ_FOLDER_PATH = "blog/blognode";

    public FileRepository() {
        File imagesDirectory = new File(IMAGES_FOLDER_PATH);
        if(!imagesDirectory.exists()) {
            imagesDirectory.mkdirs();
        }
    }

    private static void appendToFile(Path path, String content)
            throws IOException {
        Files.write(path,
                content.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }

    public int add(BlogNode question) throws IOException {
        Path path = Paths.get(QUESTION_DATABASE_NAME);
        List<BlogNode> questions = findAllQuestions();
        int id = 0;
        for(BlogNode q : questions) {
            if(q.getId() > id) {
                id = q.getId();
            }
        }
        id = id + 1;
        question.setId(id);
        String data = question.toLine();
        appendToFile(path, data + NEW_LINE);
        return id;
    }



    public int addBlog(Blog quiz) throws IOException {
        List<Blog> quizzes = getAllQuizzes();
        int id = 0;
        for(Blog q : quizzes) {
            if(q.getId() > id) {
                id = q.getId();
            }
        }
        id++;

        quiz.setId(id);

        Path path = Paths.get(QUIZ_DATABASE_NAME);
        String data = quiz.toLine(id);
        System.out.println(data);
        appendToFile(path, data + NEW_LINE);
        System.out.println(id);
        return id;
    }



    public List<BlogNode> findAllQuestions() throws IOException {
        List<BlogNode> result = new ArrayList<>();
        Path path = Paths.get(QUESTION_DATABASE_NAME);
        if (Files.exists(path)) {
            List<String> data = Files.readAllLines(path);
            for (String line : data) {
                if(line.trim().length() != 0) {
                    BlogNode q = BlogNode.fromLine(line);
                    result.add(q); //
                }
            }
        }
        return result;
    }

    public List<Blog> getAllQuizzes() throws IOException{
        // get all the quizzes from the database

        List<Blog> quizzes = new ArrayList<>();
        Path path = Paths.get(QUIZ_DATABASE_NAME);
        if (Files.exists(path)) {
            List<String> data = Files.readAllLines(path);
            for (String line : data) {
                if (line.trim().length() != 0) {
                    Blog q = Blog.fromLine(line);
                    // Fetch questions associated with this quiz
                    List<BlogNode> questions = find(q.getQuestionIds());
                    q.setQuestions(questions);
                    quizzes.add(q);
                }
            }
        }
        return quizzes;

    }

    public List<BlogNode> find(String answer) throws IOException {
        List<BlogNode> animals = findAllQuestions();
        List<BlogNode> result = new ArrayList<>();
        for (BlogNode question : animals) {
            if (answer != null && !question.getAnswer().trim().equalsIgnoreCase(answer.trim())) {
                continue;
            }
            result.add(question);
        }
        return result;
    }

    public List<BlogNode> find(List<Integer> ids) throws IOException {
        List<BlogNode> questions = findAllQuestions();
        List<BlogNode> result = new ArrayList<>();
        for (int id : ids) {
            Optional<BlogNode> optionalQuestion = questions.stream().filter(x -> x.getId() == id).findFirst();
            optionalQuestion.ifPresent(result::add);
        }
        return result;
    }

    public BlogNode get(Integer id) throws IOException {
        List<BlogNode> questions = findAllQuestions();
        for (BlogNode question : questions) {
            if (question.getId() == id) {
                return question;
            }
        }
        return null;
    }
    public Blog getQuiz(Integer id) throws IOException {
        List<Blog> quizzes = getAllQuizzes();
        for (Blog quiz : quizzes) {
            if (quiz.getId() == id) {
                return quiz;
            }
        }
        return null;
    }

    public boolean updateImage(int id, MultipartFile file) throws IOException {
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getContentType());

        String fileExtension = ".png";
        Path path = Paths.get(IMAGES_FOLDER_PATH
                + "/" + id + fileExtension);
        System.out.println("The file " + path + " was saved successfully.");
        file.transferTo(path);
        return true;
    }
//    public boolean updateQuiz(int id, Quiz updatedQuiz) throws IOException {
//        List<Quiz> quizzes = getAllQuizzes();
//        for (int i = 0; i < quizzes.size(); i++) {
//            if (quizzes.get(i).getId() == id) {
//                quizzes.set(i, updatedQuiz);
//                writeQuizzesToFile(quizzes); // Write the updated list of quizzes back to the file
//                return true;
//            }
//        }
//        return false; // Quiz with the given ID not found
//    }

    private void writeQuizzesToFile(List<Blog> quizzes) throws IOException {
        Path path = Paths.get(QUIZ_DATABASE_NAME);
        Files.write(path, "".getBytes()); // Clear the file
        for (Blog quiz : quizzes) {
            String data = quiz.toLine(quiz.getId());
            appendToFile(path, data + NEW_LINE); // Append each quiz to the file
        }
    }


    public byte[] getImage(int id) throws IOException {
        String fileExtension = ".png";
        Path path = Paths.get(IMAGES_FOLDER_PATH
                + "/" + id + fileExtension);
        byte[] image = Files.readAllBytes(path);
        return image;
    }
    //    public void updateQuiz(Quiz updatedQuiz) throws IOException {
//        List<Quiz> quizzes = getAllQuizzes();
//        boolean found = false;
//        for (int i = 0; i < quizzes.size(); i++) {
//            Quiz quiz = quizzes.get(i);
//            if (quiz.getId() == updatedQuiz.getId()) {
//                quizzes.set(i, updatedQuiz);
//                found = true;
//                break;
//            }
//        }
//        if (!found) {
//            throw new IllegalArgumentException("Quiz with id " + updatedQuiz.getId() + " not found");
//        }
//        Path path = Paths.get(QUIZ_DATABASE_NAME);
//        Files.deleteIfExists(path);
//        for (Quiz quiz : quizzes) {
//            appendToFile(path, quiz.toLine(quiz.getId()));
//        }
//    }
    public void updateQuiz(Blog updatedQuiz) throws IOException {
        List<Blog> quizzes = getAllQuizzes();
        boolean found = false;
        for (int i = 0; i < quizzes.size(); i++) {
            Blog quiz = quizzes.get(i);
            if (quiz.getId() == updatedQuiz.getId()) {
                quizzes.set(i, updatedQuiz);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Quiz with id " + updatedQuiz.getId() + " not found");
        }

        // Rewrite the entire database file with updated quiz information
        Path path = Paths.get(QUIZ_DATABASE_NAME);
        List<String> lines = new ArrayList<>();
        for (Blog quiz : quizzes) {
            lines.add(quiz.toLine(quiz.getId()));
        }
        Files.write(path, lines, StandardCharsets.UTF_8);
    }





}

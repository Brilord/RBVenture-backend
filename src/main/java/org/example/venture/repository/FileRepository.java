package org.example.venture.repository;

;
import org.example.venture.model.Question;
import org.example.venture.model.Quiz;
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
    private String IMAGES_FOLDER_PATH = "quizzes/questions/images";
    private static final String NEW_LINE = System.lineSeparator();
    private static final String QUESTION_DATABASE_NAME = "quizzes/questions.txt";
    private static final String QUIZ_DATABASE_NAME = "quizzes/quizzes.txt";
    private static final String QUIZ_FOLDER_PATH = "quizzes/quiz";

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

    public int add(Question question) throws IOException {
        Path path = Paths.get(QUESTION_DATABASE_NAME);
        List<Question> questions = findAllQuestions();
        int id = 0;
        for(Question q : questions) {
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



    public int addQuiz(Quiz quiz) throws IOException {
        List<Quiz> quizzes = getAllQuizzes();
        int id = 0;
        for(Quiz q : quizzes) {
            if(q.getId() > id) {
                id = q.getId();
            }
        }
        id++;

        // get all the questions
        // then select the ids for the questions using List qustionid
        // add the quiz to the database.
        quiz.setId(id);
        // Now, we need to add the quiz to the database file
        Path path = Paths.get(QUIZ_DATABASE_NAME);
        String data = quiz.toLine(id);
        System.out.println(data);
        appendToFile(path, data + NEW_LINE);
        System.out.println(id);
        return id;
    }



    public List<Question> findAllQuestions() throws IOException {
        List<Question> result = new ArrayList<>();
        Path path = Paths.get(QUESTION_DATABASE_NAME);
        if (Files.exists(path)) {
            List<String> data = Files.readAllLines(path);
            for (String line : data) {
                if(line.trim().length() != 0) {
                    Question q = Question.fromLine(line);
                    result.add(q); //
                }
            }
        }
        return result;
    }

    public List<Quiz> getAllQuizzes() throws IOException{
        // get all the quizzes from the database

        List<Quiz> quizzes = new ArrayList<>();
        Path path = Paths.get(QUIZ_DATABASE_NAME);
        if (Files.exists(path)) {
            List<String> data = Files.readAllLines(path);
            for (String line : data) {
                if (line.trim().length() != 0) {
                    Quiz q = Quiz.fromLine(line);
                    // Fetch questions associated with this quiz
                    List<Question> questions = find(q.getQuestionIds());
                    q.setQuestions(questions);
                    quizzes.add(q);
                }
            }
        }
        return quizzes;

    }

    public List<Question> find(String answer) throws IOException {
        List<Question> animals = findAllQuestions();
        List<Question> result = new ArrayList<>();
        for (Question question : animals) {
            if (answer != null && !question.getAnswer().trim().equalsIgnoreCase(answer.trim())) {
                continue;
            }
            result.add(question);
        }
        return result;
    }

    public List<Question> find(List<Integer> ids) throws IOException {
        List<Question> questions = findAllQuestions();
        List<Question> result = new ArrayList<>();
        for (int id : ids) {
            Optional<Question> optionalQuestion = questions.stream().filter(x -> x.getId() == id).findFirst();
            optionalQuestion.ifPresent(result::add);
        }
        return result;
    }




    public Question get(Integer id) throws IOException {
        List<Question> questions = findAllQuestions();
        for (Question question : questions) {
            if (question.getId() == id) {
                return question;
            }
        }
        return null;
    }
    public Quiz getQuiz(Integer id) throws IOException {
        List<Quiz> quizzes = getAllQuizzes();
        for (Quiz quiz : quizzes) {
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

    private void writeQuizzesToFile(List<Quiz> quizzes) throws IOException {
        Path path = Paths.get(QUIZ_DATABASE_NAME);
        Files.write(path, "".getBytes()); // Clear the file
        for (Quiz quiz : quizzes) {
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
    public void updateQuiz(Quiz updatedQuiz) throws IOException {
        List<Quiz> quizzes = getAllQuizzes();
        boolean found = false;
        for (int i = 0; i < quizzes.size(); i++) {
            Quiz quiz = quizzes.get(i);
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
        for (Quiz quiz : quizzes) {
            lines.add(quiz.toLine(quiz.getId()));
        }
        Files.write(path, lines, StandardCharsets.UTF_8);
    }





}

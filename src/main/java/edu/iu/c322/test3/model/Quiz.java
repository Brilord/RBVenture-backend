package edu.iu.c322.test3.model;

import java.util.Arrays;
import java.util.List;

public class Quiz {

    private Integer id;
    private String title;
    private List<Integer> questionIds;

    private List<Question> questions;

    public Quiz(Integer id, String quizTitle, List<Integer> questionIds) {
        this.id = id;
        this.title = quizTitle;
        this.questionIds = questionIds;
    }

    public String toLine(int quizId) {
        String questionIds = String.join(",", getQuestionIds().stream().map(String::valueOf).toList());
        String line = String.format("%1s,%2s, %3s",
                quizId,
                getTitle(),
                              questionIds);
        return line;
    }

    public static Quiz fromLine(String line) {
        String[] tokens = line.split(",");
        List<Integer> ids = Arrays.stream(Arrays.copyOfRange(tokens, 2, tokens.length))
                .map(x -> Integer.valueOf(x.trim())).toList();
        Quiz q = new Quiz(Integer.valueOf(tokens[0]),
                tokens[1],
                ids);
        return q;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Integer> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<Integer> questionIds) {
        this.questionIds = questionIds;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}

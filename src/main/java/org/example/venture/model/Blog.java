package org.example.venture.model;

import java.util.Arrays;
import java.util.List;

public class Blog {

    private Integer id;
    private String title;
    private List<Integer> questionIds;

    private List<BlogNode> questions;

    public Blog(Integer id, String quizTitle, List<Integer> questionIds) {
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

    public static Blog fromLine(String line) {
        String[] tokens = line.split(",");
        List<Integer> ids = Arrays.stream(Arrays.copyOfRange(tokens, 2, tokens.length))
                .map(x -> Integer.valueOf(x.trim())).toList();
        Blog q = new Blog(Integer.valueOf(tokens[0]),
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

    public List<BlogNode> getQuestions() {
        return questions;
    }

    public void setQuestions(List<BlogNode> questions) {
        this.questions = questions;
    }
}

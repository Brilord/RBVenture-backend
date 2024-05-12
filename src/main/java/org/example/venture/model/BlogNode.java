package org.example.venture.model;

import java.util.Arrays;

public class BlogNode {

    public BlogNode(Integer id, String head, String description, String date, String[] choices) {
        this.id = id;
        this.head = head;
        this.description = description;
        this.answer = date;
        this.choices = choices;
    }

    private Integer id;
    private String description;
    private String answer;
    private String[] choices;
    private String head;

    public String toLine() {
        String choicesAsString = String.join(",", getChoices());
        String line = String.format("%1s,%2s,%3s,%4s",
                getId(),
                getHead().trim(),
                getDescription().trim(),
                getAnswer().trim(),
                choicesAsString.trim());
        return line;
    }

    public static BlogNode fromLine(String line) {
      String[] tokens = line.split(",");
      String[] choiceTokens = Arrays.copyOfRange(tokens, 4, tokens.length);
      BlogNode q = new BlogNode(Integer.parseInt(tokens[0]),
              tokens[1].trim(),
              tokens[2].trim(),
              tokens[3].trim(),
              choiceTokens);
      return q;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

}

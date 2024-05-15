package org.example.venture.model;

import java.util.Arrays;

public class BlogNode {

    public BlogNode(Integer id, String userEmailID,String head, String description, String date, String imageId) {
        this.id = id;
        this .userEmailID = userEmailID;
        this.head = head;
        this.description = description;
        this.answer = date;
        this.imageId = imageId;

    }

    private Integer id;
    private String userEmailID;
    private String description;
    private String answer;
    private String head;
    private String imageId;

    public String toLine() {
        String line = String.format("%1s,%2s,%3s,%4s,%5s,%6s",
                getId(),
                getUserEmailID(),
                getHead().trim(),
                getDescription().trim(),
                getAnswer().trim(),
                getImageId().trim());
        return line;
    }

    public static BlogNode fromLine(String line) {
        String[] tokens = line.split(",");
        BlogNode blogNode = new BlogNode(
                Integer.parseInt(tokens[0].trim()),
                tokens[1].trim(),
                tokens[2].trim(),
                tokens[3].trim(),
                tokens[4].trim(),
                tokens[5].trim()
        );
        return blogNode;
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

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getUserEmailID() {
        return userEmailID;
    }

    public void setUserEmailID(String userEmailID) {
        this.userEmailID = userEmailID;
    }



}

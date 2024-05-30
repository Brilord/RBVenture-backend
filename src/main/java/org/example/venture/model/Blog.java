package org.example.venture.model;

import java.util.Arrays;
import java.util.List;

public class Blog {

    private Integer id;

    private String userEmailID;
    private String title;
    private String description;
    private String dateAndTime;
    private List<Integer> blogNodeIds;

    private List<BlogNode> blogNodes;

    public Blog(Integer id, String userEmailID, String blogTitle, String description, String dateAndTime, List<Integer> blogNodeIds) {
        this.id = id;
        this.userEmailID = userEmailID;
        this.title = blogTitle;
        this.description = description;
        this.dateAndTime = dateAndTime;
        this.blogNodeIds = blogNodeIds;
    }

    public String toLine(int blogId) {
        String blogNodeIds = "";
        if (getBlogNodeIds() != null && !getBlogNodeIds().isEmpty()) {
            blogNodeIds = String.join(",", getBlogNodeIds().stream().map(String::valueOf).toList());
            blogNodeIds = "," + blogNodeIds;
        }
        String line = String.format("%1s,%2s,%3s,%4s,%5s%6s",
                blogId,
                userEmailID,
                getTitle(),
                getDescription(),
                getDateAndTime(),
                blogNodeIds);
        return line;
    }



    public static Blog fromLine(String line) {
        String[] tokens = line.split(",");
        List<Integer> ids = Arrays.stream(Arrays.copyOfRange(tokens, 5, tokens.length))
                .map(x -> Integer.valueOf(x.trim())).toList();
        Blog blog = new Blog(Integer.valueOf(tokens[0]),
                tokens[1], tokens[2], tokens[3], tokens[4],
                ids);
        return blog;
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

    public List<Integer> getBlogNodeIds() {
        return blogNodeIds;
    }

    public void setBlogNodeIds(List<Integer> blogNodeIds) {
        this.blogNodeIds = blogNodeIds;
    }

    public List<BlogNode> getBlogNodes() {
        return blogNodes;
    }

    public void setBlogNodes(List<BlogNode> blogNodes) {
        this.blogNodes = blogNodes;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserEmailID() {
        return userEmailID;
    }

    public void setUserEmailID(String userEmailID) {
        this.userEmailID = userEmailID;
    }

}

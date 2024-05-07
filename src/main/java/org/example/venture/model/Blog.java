package org.example.venture.model;

import java.util.Arrays;
import java.util.List;

public class Blog {

    private Integer id;
    private String title;
    private List<Integer> blogNodeIds;

    private List<BlogNode> blogNodes;

    public Blog(Integer id, String blogTitle, List<Integer> blogNodeIds) {
        this.id = id;
        this.title = blogTitle;
        this.blogNodeIds = blogNodeIds;
    }

    public String toLine(int blogId) {
        String blogNodeIds = String.join(",", getBlogNodeIds().stream().map(String::valueOf).toList());
        String line = String.format("%1s,%2s, %3s",
                blogId,
                getTitle(),
                blogNodeIds);
        return line;
    }

    public static Blog fromLine(String line) {
        String[] tokens = line.split(",");
        List<Integer> ids = Arrays.stream(Arrays.copyOfRange(tokens, 2, tokens.length))
                .map(x -> Integer.valueOf(x.trim())).toList();
        Blog blog = new Blog(Integer.valueOf(tokens[0]),
                tokens[1],
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
}

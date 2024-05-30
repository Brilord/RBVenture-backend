package org.example.venture.repository;

import org.example.venture.model.BlogNode;
import org.example.venture.model.Blog;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
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
    private static final String BLOGNODE_DATABASE_NAME = "blog/blognode.txt";
    private static final String BLOG_DATABASE_NAME = "blog/blog.txt";
    private static final String BLOG_FOLDER_PATH = "blog/blognode";



    public FileRepository() {
        File imagesDirectory = new File(IMAGES_FOLDER_PATH);
        if (!imagesDirectory.exists()) {
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

    public int add(BlogNode blognode) throws IOException {
        Path path = Paths.get(BLOGNODE_DATABASE_NAME);
        List<BlogNode> blognodes = findAllBlogNodes();
        int id = 0;
        for (BlogNode q : blognodes) {
            if (q.getId() > id) {
                id = q.getId();
            }
        }
        id = id + 1;
        blognode.setId(id);
        String data = blognode.toLine();
        appendToFile(path, data + NEW_LINE);
        return id;
    }

    public int addBlog(Blog blog) throws IOException {
        List<Blog> blogs = getAllBlogs();
        int id = 0;
        for (Blog q : blogs) {
            if (q.getId() > id) {
                id = q.getId();
            }
        }
        id++;
        blog.setId(id);
        Path path = Paths.get(BLOG_DATABASE_NAME);
        String data = blog.toLine(id);
        appendToFile(path, data + NEW_LINE);
        return id;
    }

    public List<BlogNode> findAllBlogNodes() throws IOException {
        List<BlogNode> result = new ArrayList<>();
        Path path = Paths.get(BLOGNODE_DATABASE_NAME);
        if (Files.exists(path)) {
            List<String> data = Files.readAllLines(path);
            for (String line : data) {
                if (line.trim().length() != 0) {
                    BlogNode q = BlogNode.fromLine(line);
                    result.add(q);
                }
            }
        }
        return result;
    }

    public List<Blog> getAllBlogs() throws IOException {
        List<Blog> blogs = new ArrayList<>();
        Path path = Paths.get(BLOG_DATABASE_NAME);
        if (Files.exists(path)) {
            List<String> data = Files.readAllLines(path);
            for (String line : data) {
                if (line.trim().length() != 0) {
                    Blog q = Blog.fromLine(line);
                    List<BlogNode> blogNodes = find(q.getBlogNodeIds());
                    q.setBlogNodes(blogNodes);
                    blogs.add(q);
                }
            }
        }
        return blogs;
    }

    public List<BlogNode> find(String answer) throws IOException {
        List<BlogNode> blognodes = findAllBlogNodes();
        List<BlogNode> result = new ArrayList<>();
        for (BlogNode blognode : blognodes) {
            if (answer != null && !blognode.getAnswer().trim().equalsIgnoreCase(answer.trim())) {
                continue;
            }
            result.add(blognode);
        }
        return result;
    }

    public List<BlogNode> find(List<Integer> ids) throws IOException {
        List<BlogNode> blognodes = findAllBlogNodes();
        List<BlogNode> result = new ArrayList<>();
        for (int id : ids) {
            Optional<BlogNode> optionalBlogNode = blognodes.stream().filter(x -> x.getId() == id).findFirst();
            optionalBlogNode.ifPresent(result::add);
        }
        return result;
    }

    public BlogNode get(Integer id) throws IOException {
        List<BlogNode> blognodes = findAllBlogNodes();
        for (BlogNode blognode : blognodes) {
            if (blognode.getId() == id) {
                return blognode;
            }
        }
        return null;
    }

    public Blog getBlog(Integer id) throws IOException {
        List<Blog> blogs = getAllBlogs();
        for (Blog blog : blogs) {
            if (blog.getId() == id) {
                return blog;
            }
        }
        return null;
    }

    public boolean addImagePng(int id, MultipartFile file) throws IOException {
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getContentType());

        String fileExtension = ".png";
        Path path = Paths.get(IMAGES_FOLDER_PATH + "/"
                 + id + fileExtension);
        System.out.println("The file " + path + " was saved successfully.");
        file.transferTo(path);
        return true;
    }
    public boolean updateImagePng(int id, MultipartFile file) throws IOException {
        String fileExtension = ".png";
        Path imagePath = Paths.get(IMAGES_FOLDER_PATH + "/" + id + fileExtension);

        // Check if the image file exists
        if (!Files.exists(imagePath)) {
            throw new FileNotFoundException("Image with id " + id + " not found");
        }

        // Delete the existing image
        Files.delete(imagePath);

        // Save the new image with the same id
        file.transferTo(imagePath);

        return true;
    }


    private void writeBlogsToFile(List<Blog> blogs) throws IOException {
        Path path = Paths.get(BLOG_DATABASE_NAME);
        Files.write(path, "".getBytes()); // Clear the file
        for (Blog blog : blogs) {
            String data = blog.toLine(blog.getId());
            appendToFile(path, data + NEW_LINE); // Append each blog to the file
        }
    }

    public byte[] getImage(int id) throws IOException {
        String fileExtension = ".png";
        Path path = Paths.get(IMAGES_FOLDER_PATH + "/" + id + fileExtension);
        return Files.readAllBytes(path);
    }

    public void updateBlog(Blog updatedBlog) throws IOException {
        List<Blog> blogs = getAllBlogs();
        boolean found = false;
        for (int i = 0; i < blogs.size(); i++) {
            Blog blog = blogs.get(i);
            if (blog.getId() == updatedBlog.getId()) {
                blogs.set(i, updatedBlog);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Blog with id " + updatedBlog.getId() + " not found");
        }

        // Rewrite the entire database file with updated blog information
        writeBlogsToFile(blogs);
    }

    public void deleteBlog(int id) {
        try {
            List<Blog> blogs = getAllBlogs();
            boolean found = false;
            for (int i = 0; i < blogs.size(); i++) {
                Blog blog = blogs.get(i);
                if (blog.getId() == id) {
                    blogs.remove(i);
                    found = true;
                    // After removing the blog, reassign IDs to ensure sequential numbering
                    reassignIds(blogs);
                    break;
                }
            }
            if (!found) {
                throw new IllegalArgumentException("Blog with id " + id + " not found");
            }

            writeBlogsToFile(blogs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // After removing a blog, reassign IDs sequentially

    private void reassignIds(List<Blog> blogs) {
        for (int i = 0; i < blogs.size(); i++) {
            blogs.get(i).setId(i + 1);
        }
    }

    public void deleteAllBlogs() {
        try {
            List<Blog> blogs = getAllBlogs();
            blogs.clear();
            writeBlogsToFile(blogs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {


    }
}

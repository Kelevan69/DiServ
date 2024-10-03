package ru.netology.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PostController {
    private final PostService service;

    @Autowired
    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try (var writer = response.getWriter()) {
            writer.write(service.all().toString());
        }
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        var post = service.getById(id);
        if (post.isPresent()) {
            response.setContentType("application/json");
            try (var writer = response.getWriter()) {
                writer.write(post.get().toString());
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var post = new Post(0, request.getReader().readLine()); // Simplified for demo purposes
        var savedPost = service.save(post);
        response.setContentType("application/json");
        try (var writer = response.getWriter()) {
            writer.write(savedPost.toString());
        }
    }

    public void removeById(long id, HttpServletResponse response) {
        if (service.removeById(id)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

package ru.netology.controller;

import org.springframework.web.bind.annotation.*;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public void all(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try (var writer = response.getWriter()) {
            writer.write(service.all().toString());
        }
    }

    @GetMapping("/{id}")
    public void getById(@PathVariable long id, HttpServletResponse response) throws IOException {
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

    @PostMapping
    public void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var reader = request.getReader();
        var content = reader.readLine();
        var post = new Post(0, content);
        service.save(post);
        response.setContentType("application/json");
        try (var writer = response.getWriter()) {
            writer.write(post.toString());
        }
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable long id, HttpServletResponse response) {
        if (service.removeById(id)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

package ru.netology.repository;

import ru.netology.model.Post;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PostRepositoryStubImpl implements PostRepository {
    private final List<Post> posts = Collections.emptyList();

    @Override
    public List<Post> all() {
        return posts;
    }

    @Override
    public Optional<Post> getById(long id) {
        return posts.stream().filter(post -> post.getId() == id).findFirst();
    }

    @Override
    public Post save(Post post) {
        return post;
    }

    @Override
    public boolean removeById(long id) {
        return posts.removeIf(post -> post.getId() == id);
    }
}

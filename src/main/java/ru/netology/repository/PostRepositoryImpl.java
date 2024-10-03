package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PostRepositoryImpl implements PostRepository {
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private long currentId = 0;

    @Override
    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    @Override
    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    @Override
    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(++currentId);
        }
        posts.put(post.getId(), post);
        return post;
    }

    @Override
    public void removeById(long id) {
        posts.remove(id);
    }
}

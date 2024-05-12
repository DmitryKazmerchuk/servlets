package ru.netology.repository;

import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
 AtomicLong counter = new AtomicLong();
  ConcurrentHashMap<Long, Post> postMap = new ConcurrentHashMap<>();
  public List<Post> all() {
    return new ArrayList<>(postMap.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(postMap.get(id));
  }

  public Post save(Post post) {
    if (post.getId() == 0){
      postMap.put(counter.incrementAndGet(), post);
    } else if (postMap.containsKey(post.getId())){
      postMap.put(post.getId(), post);
    }
    return post;
  }

  public void removeById(long id) {
    if (postMap.containsKey(id)){
      postMap.remove(id);
    }
  }
}

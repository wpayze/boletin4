package es.uv.garcosda.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import es.uv.garcosda.domain.Post;

public interface PostRepository extends ReactiveCrudRepository<Post, Integer> {

}

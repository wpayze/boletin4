package es.uv.garcosda.repositories;

import es.uv.garcosda.domain.Post;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PostRepository extends ReactiveCrudRepository<Post, Integer> {

}

package es.uv.garcosda.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import es.uv.garcosda.domain.Post;
import reactor.core.publisher.Flux;

public interface PostRepository extends ReactiveCrudRepository<Post, Integer> {

}

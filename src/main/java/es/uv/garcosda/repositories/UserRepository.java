package es.uv.garcosda.repositories;

import es.uv.garcosda.domain.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Integer> {
    Mono<User> findByUsername(String username);

    Mono<User> findByUsernameAndPassword(String username, String password);
}

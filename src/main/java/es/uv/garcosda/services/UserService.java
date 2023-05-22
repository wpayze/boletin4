package es.uv.garcosda.services;

import es.uv.garcosda.domain.User;
import es.uv.garcosda.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository ur;

    public Mono<User> findByUsername(String username) {
        return this.ur.findByUsername(username);
    }

    public Mono<User> insert(User user) {
        return this.ur.save(user);
    }
}

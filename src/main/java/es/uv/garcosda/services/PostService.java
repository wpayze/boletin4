package es.uv.garcosda.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uv.garcosda.domain.Post;
import es.uv.garcosda.repositories.PostRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Mono<Post> findById(Integer id) {
        return this.postRepository.findById(id);
    }

    public Flux<Post> findAll() {
        return this.postRepository.findAll();
    }

    public Mono<Post> update(Post post) {
        return this.postRepository.save(post);
    }

    public Mono<Post> insert(Post post) {
        return this.postRepository.save(post);
    }

    public Flux<Post> createAll(List<Post> posts) {
        return this.postRepository.saveAll(posts);
    }

    public Mono<Void> deleteById(Integer id) {
        return this.postRepository.deleteById(id);
    }
}

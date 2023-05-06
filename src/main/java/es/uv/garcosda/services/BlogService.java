package es.uv.garcosda.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uv.garcosda.domain.Post;
import es.uv.garcosda.repositories.PostRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class BlogService {
	@Autowired
	PostRepository postRepository;

	public Flux<Post> findPosts() {;
		return  postRepository.findAll();
	}

	public Mono<Post> findPostById(int postId) {
		return postRepository.findById(postId);
	}

	public Mono<Post> createPost(Post post) {
		return postRepository.save(post);
	}

	public Mono<Void> deletePost(Integer postId) {
		return postRepository.deleteById(postId);
	}
}

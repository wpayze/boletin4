package es.uv.garcosda.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uv.garcosda.domain.Post;
import es.uv.garcosda.repositories.PostRepository;

@Service
@Transactional
public class BlogService {
	@Autowired
	PostRepository postRepository;

	public List<Post> findPosts() {;
		return  postRepository.findAll();
	}

	public Optional<Post> findPostById(int postId) {
		return postRepository.findById(postId);
	}

	public Post createPost(Post post) {
		return postRepository.save(post);
	}

	public void deletePost(Integer postId) {
		postRepository.deleteById(postId);
	}
}

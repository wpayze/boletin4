package es.uv.garcosda.endpoints;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.uv.garcosda.domain.Post;
import es.uv.garcosda.services.BlogService;


@RestController
@RequestMapping("/api/v1")
public class BlogRestController {
	private final static Logger LOGGER = LoggerFactory.getLogger(BlogRestController.class);
	
	@Autowired private BlogService blogService;
	
	@GetMapping("posts")
	public List<Post> findPosts() {
		LOGGER.debug("View all posts");
		return blogService.findPosts();
	}
	
	@GetMapping("posts/{id}")
	public Optional<Post> findPostById(@PathVariable("id") Integer id) {
		LOGGER.debug("View Post id: "+id);
		Optional<Post> post = blogService.findPostById(id);
		return post;
	}
	
	@PostMapping("posts")
	public ResponseEntity<Post> createPost(@RequestBody Post post) {
		LOGGER.debug("Create post");
		Post createdPost = blogService.createPost(post);
		return new ResponseEntity<>(createdPost, HttpStatus.OK);
	}
			
	@DeleteMapping("posts/{id}")
	public void deletePostById(@PathVariable("id") Integer id) {
		LOGGER.debug("Delete Post id: "+id);
		blogService.deletePost(id);
	}
	
}

package es.uv.garcosda.endpoints;

import es.uv.garcosda.domain.Post;
import es.uv.garcosda.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/posts")
@CrossOrigin
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("{id}")
    public Mono<Post> getOne(@PathVariable("id") Integer id) {
        return this.postService.findById(id);
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Post> getAll() {
        return this.postService.findAll();
    }

    @PostMapping()
    public Mono<Post> insert(@RequestBody Post post) {
        post.setCreatedOn(new Date().toString());
        return this.postService.insert(post);
    }

    @PutMapping()
    public Mono<Post> update(@RequestBody Post post) {
        return this.postService.update(post);
    }

    @DeleteMapping("{id}")
    public Mono<Void> delete(@PathVariable("id") Integer id) {
        return this.postService.deleteById(id);
    }
}

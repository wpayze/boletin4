package es.uv.garcosda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uv.garcosda.domain.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

}

package es.uv.garcosda.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uv.garcosda.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);
	Optional<User> findByUsernameAndPassword(String username, String password);
}

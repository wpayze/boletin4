package es.uv.garcosda.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uv.garcosda.domain.User;
import es.uv.garcosda.repositories.UserRepository;


@Service
@Transactional
public class UserService {
	
	@Autowired UserRepository userRepository;
	
	public Optional<User> login(String username, String password) {
		return userRepository.findByUsernameAndPassword(username, password);
	}
	
	public User createUser(User user) {
		return userRepository.save(user);
	}
	
}

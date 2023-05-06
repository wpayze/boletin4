package es.uv.garcosda.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uv.garcosda.domain.User;
import es.uv.garcosda.repositories.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class UserService {
	
	@Autowired 
	UserRepository ur;
	
	public Mono<User> login(String username, String password) {
		return ur.findByUsernameAndPassword(username, password);
	}
	
	public Flux<User> findAll(){
		return this.ur.findAll();
	}
	
	public Mono<User> findByUsername(String username){
		return this.ur.findByUsername(username);
	}
	
	public Mono<User> findByUsernameAndPassword(String username, String password) {
		return this.ur.findByUsernameAndPassword(username, password);
	}
	
	public Mono<User> createUser(User user) {
		return this.ur.save(user);
	}
	
	public Mono<User> insert(User user){
		return this.ur.save(user);
	}
	
	public Mono<Void> deleteAll() {
		return this.ur.deleteAll();
	}
	
}

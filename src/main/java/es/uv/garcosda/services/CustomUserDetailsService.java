package es.uv.garcosda.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.uv.garcosda.domain.User;
import reactor.core.publisher.Mono;

@Service
public class CustomUserDetailsService implements ReactiveUserDetailsService {
	
    @Autowired
    private UserService us;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
    	return us.findByUsername(username)
       		 .switchIfEmpty(Mono.defer(() ->	
       						Mono.error(new UsernameNotFoundException("User Not Found"))))
       		 .map(User::toAuthUser);
    }
    
}

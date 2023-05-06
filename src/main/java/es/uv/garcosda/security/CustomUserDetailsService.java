package es.uv.garcosda.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.uv.garcosda.domain.User;
import es.uv.garcosda.repositories.UserRepository;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired 
	UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = repo.findByUsername(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), 
														              user.getPassword(),
														              getAuthorities(user));
    }

	private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        String[] userRoles = user.getRoles()
                                 .stream()
                                 .map((role) -> role.getName()).toArray(String[]::new);
        
        System.out.println(userRoles);
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }

}

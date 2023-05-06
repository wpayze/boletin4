package es.uv.garcosda.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import es.uv.garcosda.security.CustomAuthenticationFilter;
import es.uv.garcosda.security.CustomAuthorizationFilter;
import es.uv.garcosda.security.CustomUserDetailsService;
import es.uv.garcosda.services.JwtService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{	
	
	@Autowired
    private CustomUserDetailsService userDetailsService;
	
	@Autowired
    private JwtService jwtService;
	
	@Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public AuthenticationManager authenticationManager(){
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return new ProviderManager(authProvider);
	}
		
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter(authenticationManager(), jwtService);
    	authenticationFilter.setFilterProcessesUrl("/api/v1/login");
    	
    	CustomAuthorizationFilter authorizationFilter = new CustomAuthorizationFilter(jwtService);
		
		http.csrf().disable()
			.cors().disable()
			.sessionManagement().sessionCreationPolicy(STATELESS)
			.and()
			.authorizeHttpRequests()
				.requestMatchers("/api/v1/login", "/api/v1/login/refresh", "/api/v1/bla").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/posts", "/api/v1/posts/**", "/api/v1/users/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
	    		.requestMatchers(HttpMethod.POST, "/api/v1/posts").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
	    		.requestMatchers(HttpMethod.DELETE, "/api/v1/posts/**").hasAuthority("ROLE_ADMIN")
			.and()
			.addFilter(authenticationFilter)
			.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
		
}

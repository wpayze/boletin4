package es.uv.garcosda.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import es.uv.garcosda.security.AuthenticationManager;
import es.uv.garcosda.security.SecurityContextRepository;
import reactor.core.publisher.Mono;

@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
@Configuration
public class WebConfigSecurity {

	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityContextRepository securityContextRepository;
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
			
	@Bean
	public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
		return http.csrf().disable()
				   .formLogin().disable()
				   .logout().disable()
				   .authenticationManager(authenticationManager)
	               .securityContextRepository(securityContextRepository)
				   .authorizeExchange(exchanges -> exchanges
					   .pathMatchers("/api/v1/authenticate").permitAll()
					   .pathMatchers("/api/v1/refresh").permitAll()
				       .anyExchange().authenticated()
				   )
				   .httpBasic()
				   .and()
               	   .exceptionHandling()
               	       .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() -> {
               			    swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
               		    }))
               	       .accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> {
               			    swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
               		    }))
				   .and()
				   .build();
	}
	
}

package es.uv.garcosda.endpoints;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.uv.garcosda.security.CustomUserDetailsService;
import es.uv.garcosda.services.JwtService;

@RestController
@RequestMapping("api/v1/login/refresh")
public class RefreshController {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtService jwtService;
	
	@GetMapping()
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws StreamWriteException, DatabindException, IOException{
		String authHeader = request.getHeader(AUTHORIZATION);
		
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			try {
				String token = this.jwtService.getTokenFromHeader(authHeader);
				String username = this.jwtService.getUsernameFromToken(token);
				UserDetails user = this.customUserDetailsService.loadUserByUsername(username);
				
				String access_token = jwtService.generateAccessToken(user.getUsername(), 
						 user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
				
				response.setHeader("access_token", access_token);
				response.setHeader("refresh_token", token);
				Map<String, String> tokens = new HashMap<>();
				tokens.put("access_token", access_token);
				tokens.put("refresh_token", token);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
			}
			catch(Exception exception) {
				response.setHeader("error", exception.getMessage());
				//response.sendError(403);
				response.setStatus(403);
				Map<String, String> error = new HashMap<>();
				error.put("error_msg", exception.getMessage());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
		}
		else {
			throw new RuntimeException("missing refresh token");
		}
	}
}

package es.uv.garcosda.endpoints;

import es.uv.garcosda.dao.AuthenticationRequest;
import es.uv.garcosda.services.JwtService;
import es.uv.garcosda.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/v1")
public class AuthorizationController {

    @Autowired
    private PasswordEncoder pe;

    @Autowired
    private UserService us;

    @Autowired
    private JwtService tp;

    @PostMapping("authenticate")
    public Mono<ResponseEntity<?>> login(@RequestBody AuthenticationRequest req) {

        return us.findByUsername(req.getUsername())
                .map(user -> {
                    if (pe.matches(req.getPassword(), user.getPassword())) {
                        Map<String, String> tokens = new HashMap<>();
                        String accessToken = this.tp.generateAccessToken(user.getUsername(), Collections.singletonList(user.getRole()));
                        String refreshToken = this.tp.generateRefreshToken(user.getUsername(), Collections.singletonList(user.getRole()));
                        tokens.put("access_token", accessToken);
                        tokens.put("refresh_token", refreshToken);
                        HttpHeaders headers = new HttpHeaders();
                        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
                        return ResponseEntity.ok()
                                .headers(headers)
                                .body(tokens);
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                    }
                }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("refresh")
    public Mono<ResponseEntity<?>> refresh(ServerWebExchange exchange) {
        String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        try {
            assert header != null;
            String refreshToken = tp.getTokenFromHeader(header);
            if (Boolean.FALSE.equals(tp.isTokenExpired(tp.getTokenFromHeader(header)))) {
                String accessToken = this.tp.generateAccessToken(tp.getUsernameFromToken(refreshToken), Arrays.asList(tp.getRolesFromToken(refreshToken)));
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                return Mono.just(ResponseEntity.ok()
                        .headers(headers)
                        .body(tokens));
            } else {
                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token"));
            }
        } catch (Exception e) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token"));
        }
    }
}

package es.uv.garcosda.models;

import java.util.ArrayList;
import java.util.List;

public class AuthenticatedUser {

    private final String email;
    private List<String> roles = new ArrayList<>();

    public AuthenticatedUser(String email, List<String> roles) {
        this.email = email;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getRoles() {
        return roles;
    }

}

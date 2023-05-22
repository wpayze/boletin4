package es.uv.garcosda.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.uv.garcosda.domain.Post;
import es.uv.garcosda.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Service
public class ImportService {

    @Autowired
    private PostService ps;

    @Autowired
    private UserService us;

    private List<Post> generateDocs(List<String> lines) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        List<Post> docs = new ArrayList<>();
        for (String e : lines) {
            Post x = mapper.readValue(e, Post.class);
            x.setId(null);
            docs.add(x);
        }
        System.out.println("[I] ImportService: readed " + docs.size() + " items");
        return docs;
    }

    private int insert(List<Post> mongoDocs) {
        try {
            Flux<Post> a = ps.createAll(mongoDocs);
            a.blockLast();
            return 1;
        } catch (DataIntegrityViolationException e) {
            return 0;
        }
    }

    private void insertUsers() {
        List<User> users = new ArrayList<>(
                Arrays.asList(new User("user", "user", new BCryptPasswordEncoder().encode("1234"), "ROLE_USER"),
                        new User("admin", "admin", new BCryptPasswordEncoder().encode("1234"), "ROLE_ADMIN")));
        for (User u : users) {
            this.us.insert(u).block();
        }
    }

    public int doImport(Resource resource) throws JsonMappingException, JsonProcessingException {
        ArrayList<String> jsonlines = new ArrayList<>();
        try (Scanner s = new Scanner(resource.getFile())) {
            while (s.hasNext()) {
                jsonlines.add(s.nextLine());
            }
        } catch (IOException e) {
            System.out.println("[ERROR] data file not found");
        }

        this.insertUsers();
        List<Post> docs = generateDocs(jsonlines);
        return insert(docs);
    }
}

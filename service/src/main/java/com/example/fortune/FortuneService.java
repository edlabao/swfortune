package com.example.fortune;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FortuneService {

    @Value("${swfortune.version:0}")
    private String version;

    private List<Fortune> fortunes;

    /**
     * Perform initialization functions.
     *
     * The method performs initialization functions such as initializing members and loading
     * configuration data. The @PostConstruct annotation ensures that this is run only after all
     * beans are initialized.
     */
    @PostConstruct
    private void onInit() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Fortune>> typeReference = new TypeReference<List<Fortune>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/fortunes.json");
        try {
            fortunes = mapper.readValue(inputStream, typeReference);
        } catch (IOException err) {
            System.out.println(String.format("**debug** %s", err.getMessage()));
        }
    }

    public Fortune getFortune() {
        Random rand = new Random();
        Fortune fortune = fortunes.get(rand.nextInt(fortunes.size()));
        fortune.setVersion(version);
        return fortune;
    }

    public List<Fortune> getFortunes() {
        return fortunes;
    }

    public void setFortunes(List<Fortune> fortunes) {
        this.fortunes = fortunes;
    }
}

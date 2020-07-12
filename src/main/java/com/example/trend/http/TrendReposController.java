package com.example.trend.http;
import com.example.trend.model.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1")
public class TrendReposController {

    @GetMapping(path = "/trends", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response trends() {
        RestTemplate restTemplate = new RestTemplate();
        LocalDateTime ldt = LocalDateTime.now().minusDays(30);
        String todayDt = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US).format(ldt);
        String githubRepoUrl = "https://api.github.com/search/repositories?q=created:>" + todayDt + "&sort=stars&order=desc&per_page=100";
        ResponseEntity<String> response = restTemplate.getForEntity(githubRepoUrl, String.class);
        ObjectMapper mapper = new ObjectMapper();
        Response resp = new Response();
        try {
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode items = root.path("items");
            System.out.println("Size : " + items.size());
            Iterator<JsonNode> itemsList = items.elements();
            int i = 0;
            while (itemsList.hasNext()) {
                JsonNode item = itemsList.next();
                System.out.println("item name : " + item.path("name").asText());
                System.out.println("item language : " + item.path("language").asText());
                if (item.path("language").asText() != "null")
                    resp.addLanguage(item.path("language").asText(), item.path("name").asText());
                i++;
            }
            // sorting languages by numer of repo
            resp.getLanguages().sort((a, b) -> b.getTotalRepo() - a.getTotalRepo());
            System.out.println("counts : " + i);

        } catch (Exception e) {
            System.out.println("Messsssage : " + e.getCause());
        }
        return resp;
    }

}
package com.sample.movie_catalog.controllers;

import com.sample.common.Movie;
import com.sample.movie_catalog.ApplicationProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("movies")
public class MovieController {
    private final ApplicationProperties props;
    private final WebClient.Builder webClient;

    @GetMapping("")
    private String list(Model model) {
        //one way with restTemplate
        //List<Movie> movies = Arrays.asList(Objects.requireNonNull(restTemp.getForEntity(props.getList(), Movie[].class).getBody()));
        List<Movie> movies = webClient.build().get()
                .uri(props.getList())
                .retrieve()
                .bodyToFlux(Movie.class)
                .buffer().blockFirst();
        model.addAttribute("models", movies);
        return "index";
    }
}

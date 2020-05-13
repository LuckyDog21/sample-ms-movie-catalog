package com.sample.movie_catalog.controllers;

import com.sample.common.Movie;
import com.sample.movie_catalog.ApplicationProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
@RequestMapping("movies")
public class MovieController {
    private final ApplicationProperties props;

    @GetMapping("")
    private String list(Model model) {
        List<Movie> movies = Arrays.asList(Objects.requireNonNull(new RestTemplate().getForEntity(props.getList(), Movie[].class).getBody()));
        model.addAttribute("models", movies);
        return "index";
    }
}

package com.sample.movie_catalog.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sample.common.Movie;
import com.sample.movie_catalog.ApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Collections;
import java.util.List;

@Controller
@RefreshScope
@RequestMapping("movies")
public class MovieController {
    public MovieController(ApplicationProperties props, WebClient.Builder webClient) {
        this.props = props;
        this.webClient = webClient;
    }

    private final ApplicationProperties props;
    private final WebClient.Builder webClient;
    @Value("${first.block.hello: default value}")
    private String some;

    @GetMapping("")
    @HystrixCommand(fallbackMethod = "getFallBackList",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "1000")
            })
    public String list(Model model) {
        List<Movie> movies = webClient.build().get()
                .uri(props.getList())
                .retrieve()
                .bodyToFlux(Movie.class)
                .buffer().blockFirst();
        model.addAttribute("models", movies);
        return "index";
    }

    public String getFallBackList(Model model) {
        model.addAttribute("models", Collections.singletonList(new Movie("no value", "no value")));
        return "index";
    }
}

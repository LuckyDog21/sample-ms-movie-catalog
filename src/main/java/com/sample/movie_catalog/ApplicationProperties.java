package com.sample.movie_catalog;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("app.props")
@Component
public class ApplicationProperties {
    @Getter
    private final String list = "http://movie-info/api/v1/movies";
}

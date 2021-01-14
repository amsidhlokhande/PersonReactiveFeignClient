package com.amsidh.mvc.router;

import com.amsidh.mvc.handler.PersonHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class PersonRouter {

    @Bean
    public RouterFunction getPersonRouters(PersonHandler personHandler) {
        return route(GET("/persons").and(accept(APPLICATION_JSON)), personHandler.findAllPersons()).and(
                route(GET("/persons/{personId}").and(accept(APPLICATION_JSON)), personHandler.findPersonById()));
    }

}

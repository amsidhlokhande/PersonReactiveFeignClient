package com.amsidh.mvc.router;

import com.amsidh.mvc.handler.AddressHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AddressRouter {
    @Bean
    public RouterFunction<ServerResponse> getAddressRouters(AddressHandler addressHandler){
        return route(GET("/addresses").and(accept(APPLICATION_JSON)), addressHandler.getAllAddress()).and(
                route(GET("/addresses/{addressId}").and(accept(APPLICATION_JSON)), addressHandler.getAddressById())).and(
                route(GET("/addresses/{personId}/addresses").and(accept(APPLICATION_JSON)), addressHandler.getAddressesByPersonId()));
    }

}

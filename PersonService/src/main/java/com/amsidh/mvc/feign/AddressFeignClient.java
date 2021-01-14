package com.amsidh.mvc.feign;

import com.amsidh.mvc.document.Address;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "address-ws", url = "localhost:8282")
public interface AddressFeignClient {

    @GetMapping("/addresses/{personId}/addresses")
    Flux<Address> getAddressesByPersonId(@PathVariable( value = "personId") String personId);
}

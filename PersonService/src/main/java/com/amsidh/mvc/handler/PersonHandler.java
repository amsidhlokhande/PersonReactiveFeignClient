package com.amsidh.mvc.handler;

import com.amsidh.mvc.document.Address;
import com.amsidh.mvc.document.PersonInfo;
import com.amsidh.mvc.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@Data
@AllArgsConstructor
public class PersonHandler {

    private PersonRepository personRepository;
    private RestTemplate restTemplate;

    public HandlerFunction<ServerResponse> findPersonById() {
        return request -> ok().body(personRepository.findById(request.pathVariable("personId")).flatMap(person -> {
            String addressUrl = String.format("http://localhost:59271/addresses/%s/addresses", person.get_id());
            ResponseEntity<List<Address>> responseEntity = restTemplate.exchange(addressUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Address>>() {
            });
            person.setAddresses(responseEntity.getBody());
            return Mono.justOrEmpty(person);
        }), PersonInfo.class);

    }

    public HandlerFunction<ServerResponse> findAllPersons() {
        return request -> ok().body(personRepository.findAll(), PersonInfo.class);
    }


}

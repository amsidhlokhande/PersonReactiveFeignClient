package com.amsidh.mvc.handler;

import com.amsidh.mvc.document.Address;
import com.amsidh.mvc.document.PersonInfo;
import com.amsidh.mvc.feign.AddressFeignClient;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@Data
@AllArgsConstructor
public class PersonHandler {

    public static final String ADDRESS_URL = "http://localhost:8282/addresses/%s/addresses";
    private PersonRepository personRepository;
    private RestTemplate restTemplate;
    private AddressFeignClient addressFeignClient;

    public HandlerFunction<ServerResponse> findPersonById() {
        return request -> ok().body(personRepository.findById(request.pathVariable("personId")).flatMap(person -> addressFeignClient.getAddressesByPersonId(person.get_id()).collectList().flatMap(addresses -> {
            person.setAddresses(addresses);
            return Mono.justOrEmpty(person);
        })).switchIfEmpty(Mono.empty()), PersonInfo.class);

    }

    public HandlerFunction<ServerResponse> findAllPersons() {
        return request -> ok().body(personRepository.findAll().flatMap(personInfo -> {
            setAddressesToPerson(personInfo);
            return Mono.justOrEmpty(personInfo);
        }), PersonInfo.class);
    }

    private void setAddressesToPerson(PersonInfo person) {
        String addressUrl = String.format(ADDRESS_URL, person.get_id());
        ResponseEntity<List<Address>> responseEntity = restTemplate.exchange(addressUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Address>>() {
        });
        person.setAddresses(responseEntity.getBody());
    }


}

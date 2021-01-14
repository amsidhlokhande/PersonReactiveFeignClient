<b> Process to Build and Up the Service  </b>
1) Maven clean install build -> start Eureka Server -> Address Service -> Person Service
<br>

Eureka Server  http://localhost:8010/
Person Service http://192.168.1.5:59655/persons
Address Service http://localhost:59271/addresses
<br>
Person-Address api Call http://localhost:59271/addresses/1/addresses

Initially we are using RestTemplate to call address service but this is synchronous non reactive and blocking call.

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate getRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;

    }
}



And In Service/Handler


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

    public static final String ADDRESS_URL = "http://localhost:59271/addresses/%s/addresses";
    private PersonRepository personRepository;
    private RestTemplate restTemplate;

    public HandlerFunction<ServerResponse> findPersonById() {
        return request -> ok().body(personRepository.findById(request.pathVariable("personId")).flatMap(person -> {
            setAddressesToPerson(person);
            return Mono.justOrEmpty(person);
        }), PersonInfo.class);

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


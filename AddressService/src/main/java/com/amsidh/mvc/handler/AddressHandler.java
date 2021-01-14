package com.amsidh.mvc.handler;

import com.amsidh.mvc.documet.Address;
import com.amsidh.mvc.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
@AllArgsConstructor
public class AddressHandler {

    private final AddressRepository addressRepository;

    public HandlerFunction<ServerResponse> getAllAddress() {
        return request -> ServerResponse.ok().body(addressRepository.findAll(), Address.class);
    }

    public HandlerFunction<ServerResponse> getAddressById() {
        return request -> ServerResponse.ok().body(addressRepository.findById(request.pathVariable("addressId")), Address.class);
    }

    public HandlerFunction<ServerResponse> getAddressesByPersonId() {
        return request -> ServerResponse.ok().body(addressRepository.findByPersonId(request.pathVariable("personId")), Address.class);
    }
}

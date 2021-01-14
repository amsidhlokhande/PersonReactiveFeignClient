package com.amsidh.mvc.repository;

import com.amsidh.mvc.documet.Address;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface AddressRepository extends ReactiveMongoRepository<Address, String> {

    Flux<Address> findByPersonId(String personId);
}

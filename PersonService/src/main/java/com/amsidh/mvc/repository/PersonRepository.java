package com.amsidh.mvc.repository;

import com.amsidh.mvc.document.PersonInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PersonRepository extends ReactiveMongoRepository<PersonInfo,String> {
}

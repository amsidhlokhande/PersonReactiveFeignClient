package com.amsidh.mvc;

import com.amsidh.mvc.document.PersonInfo;
import com.amsidh.mvc.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {PersonRepository.class})
@EnableEurekaClient
public class PersonServiceApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository personRepository;

    public static void main(String[] args) {
        SpringApplication.run(PersonServiceApplication.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
    	personRepository.findAll().hasElements().subscribe(flag->{
    		if(!flag){
				List<PersonInfo> personInfos = Arrays.asList(
						new PersonInfo("1", "Amisdh",null),
						new PersonInfo("2", "Adithi", null),
						new PersonInfo("3", "Aditya",null),
						new PersonInfo("4", "Anjali",null));
				personRepository.saveAll(personInfos).subscribe(System.out::println);
			}
		});

	}
}

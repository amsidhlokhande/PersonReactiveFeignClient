package com.amsidh.mvc;

import com.amsidh.mvc.documet.Address;
import com.amsidh.mvc.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;

@SpringBootApplication
@EnableEurekaClient
@EnableMongoRepositories(basePackageClasses = {AddressRepository.class})
public class AddressServiceApplication implements CommandLineRunner {

    @Autowired
    private AddressRepository addressRepository;

    public static void main(String[] args) {
        SpringApplication.run(AddressServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        addressRepository.findAll().hasElements().subscribe(flag -> {
            if (!flag) {
                addressRepository.saveAll(Arrays.asList(
                        //Amsidh
                        new Address("1", "Pune", "1"),
                        new Address("2", "Bijapur", "1"),
                        new Address("3", "Satara", "1"),
                        new Address("4", "Satara", "1"),

                        //Adithi
                        new Address("5", "Mumbai", "2"),
                        new Address("6", "Panvel", "2"),

                        //Aditya
                        new Address("7", "Pune", "3"),
                        new Address("8", "Mumbai", "3"),

                        //Anjali
                        new Address("9", "Satara", "4"),
                        new Address("10", "Satara", "1")

                )).subscribe(System.out::println);
            }
        });

    }
}

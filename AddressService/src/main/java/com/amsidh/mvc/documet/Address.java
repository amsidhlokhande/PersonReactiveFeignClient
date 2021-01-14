package com.amsidh.mvc.documet;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Document("address")
public class Address implements Serializable {
    @Id
    private String _id;
    private String city;
    private String personId;

}

package com.amsidh.mvc.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Document("person")
public class PersonInfo implements Serializable {
    @Id
    private String _id;
    private String name;
    @Transient
    private List<Address> addresses = new ArrayList<>();
}

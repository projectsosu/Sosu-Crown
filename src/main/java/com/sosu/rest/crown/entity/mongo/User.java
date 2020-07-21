package com.sosu.rest.crown.entity.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Document(collection = "users")
@Data
public class User {

    @Id
    private String id;

    private LocalDate birthDate;

    private String email;

    private String name;

    private String password;

    private String surname;

    private String username;

    private String image;

    private Boolean validated = Boolean.FALSE;
}

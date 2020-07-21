package com.sosu.rest.crown.entity.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Document(collection = "securities")
public class Security {

    @Id
    private String id;

    private String username;

    private String token;

    @Field(name = "token_date")
    private LocalDateTime tokenDate;

    private Long ttl;
}

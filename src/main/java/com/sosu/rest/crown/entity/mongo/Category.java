package com.sosu.rest.crown.entity.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@Document(collection = "categories")
public class Category {

    @Id
    private String id;

    private String lang;

    private String name;

    private String parent_id;
}

package com.sosu.rest.crown.entity.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Data
@Document(collection = "categories")
public class Category {

    @Id
    private String id;

    private String lang;

    private String name;

    @Field(name = "parent_id")
    private String parentId;

    @Field(name = "default_category")
    private String defaultCategory;
}

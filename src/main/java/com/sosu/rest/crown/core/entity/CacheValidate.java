package com.sosu.rest.crown.core.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "cache_update")
public class CacheValidate {

    @Id
    private Long id;

    private String cacheName;

    private String cache_key;

}

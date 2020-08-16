/**
 * @author : Oguz Kahraman
 * @since : 16.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.core.entity.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@Document(collection = "langs")
public class SupportedLangs {

    @Id
    private String id;

    private String name;

}

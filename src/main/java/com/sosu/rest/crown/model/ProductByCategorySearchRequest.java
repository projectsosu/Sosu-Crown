package com.sosu.rest.crown.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductByCategorySearchRequest {

    @NotBlank
    private String category_id;

    @NotBlank
    private int pageSize;

    @NotBlank
    private String sortBy;

    @NotBlank
    private boolean desc;

}

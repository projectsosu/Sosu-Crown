/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.enums;

public enum ProductField {

    NAME("name"),
    ID("id"),
    DESCRIPTION("description"),
    YEAR("year"),
    STATUS("status"),
    MIN("min");

    public final String label;

    ProductField(String label) {
        this.label = label;
    }

}

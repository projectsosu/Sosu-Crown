package com.sosu.rest.crown.enums;

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

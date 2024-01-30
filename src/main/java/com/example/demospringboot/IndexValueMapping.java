package com.example.demospringboot;

public record IndexValueMapping(int index, String newValue) {
    public int getIndex() {
        return index;
    }
    public String getValue() {
        return newValue;
    }
}

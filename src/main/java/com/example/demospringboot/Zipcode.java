package com.example.demospringboot;

public class Zipcode {
    private String area;
    private Integer code;
    public Zipcode(String area, Integer code) {
        this.area = area;
        this.code = code;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

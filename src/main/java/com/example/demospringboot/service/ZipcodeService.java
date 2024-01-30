package com.example.demospringboot.service;

import com.example.demospringboot.Zipcode;
import com.example.demospringboot.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ZipcodeService {
    @Autowired
    private final Dao dao;
    private void validate(Zipcode zipcode) throws Exception {
        if (zipcode.getArea() == null || zipcode.getArea().isBlank() || zipcode.getArea().contains(",") || zipcode.getCode() <= 0) {
            throw new Exception();
        }
    }
    public ZipcodeService() {
        dao = Dao.getInstance();
    }
    public void newZipcode(Zipcode zipcode) throws Exception {
        validate(zipcode);
        dao.newEntry(zipcode);
    }
    public void delete(String area) {
        dao.delete(area);
    }
    public void update(Zipcode zipcode) throws Exception {
        validate(zipcode);
        dao.update(zipcode);
    }
    public Map<String, Integer> getZipcodes() {
        return dao.getZipcodes();
    }
}

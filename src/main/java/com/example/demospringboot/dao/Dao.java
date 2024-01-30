package com.example.demospringboot.dao;

import com.example.demospringboot.Zipcode;
import com.example.demospringboot.util.Datei;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class Dao {
    private static Dao INSTANCE;
    private final Datei saveFile;
    private Map<String, Integer> zipcodes;
    private Dao() {
        zipcodes = new HashMap<>();
        saveFile = new Datei("data.csv");
        readFromFile();
    }
    public static Dao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Dao();
        }
        return INSTANCE;
    }

    public void update(Zipcode overwrite) {
        zipcodes.put(overwrite.getArea(), overwrite.getCode());
        writeToFile();
    }

    public void newEntry(Zipcode zipcode) {
        zipcodes.put(zipcode.getArea(), zipcode.getCode());
        writeToFile();
    }
    public void delete(String area) {
        System.out.println("Deleting Zipcode entry with area " + area);
        zipcodes.remove(area);
        writeToFile();
    }
    public Integer get(String area) {
        return zipcodes.get(area);
    }

    public Map<String, Integer> getZipcodes() {
        return zipcodes;
    }

    private void readFromFile() {
        String[] lines = saveFile.lese().split("\n");
        for (String line: lines) {
            String[] zipcodeData = line.split(",");
            zipcodes.put(zipcodeData[0], Integer.valueOf(zipcodeData[1]));
        }
    }
    private void writeToFile() {
        saveFile.schreibe("");
        for (Map.Entry<String, Integer> zipcodeEntry: zipcodes.entrySet()) {
            String area = zipcodeEntry.getKey();
            Integer code = zipcodeEntry.getValue();
            saveFile.schreibe(area + "," + code + "\n", true);
        }
    }
}

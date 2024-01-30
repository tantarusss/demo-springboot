package com.example.demospringboot.util;

import java.io.*;

public class Datei {
    private String dateiName;

    public Datei(String dateiName) {
        this.dateiName = dateiName;
    }

    public void schreibe(String zeile) {
        schreibe(zeile, false);
    }

    public void schreibe(String zeile, boolean append) {
        File datei = null;
        datei = new File(dateiName);
        try (FileWriter outStream = new FileWriter(datei, append)) {

            outStream.write(zeile);

        } catch (IOException e) {
            // Fehlerbehandlung
            e.printStackTrace();
        }
    }

    public String lese() {
        StringBuilder inhalt = new StringBuilder();
        File datei = null;
        BufferedReader reader = null;
        // einlesen der Datei
        datei = new File(dateiName); // Erzeuge ein Datei-Objekt
        try (FileReader inStream = new FileReader(datei)) {
            reader = new BufferedReader(inStream);
            String zeile = "";
            while ((zeile = reader.readLine()) != null) // bis alles drin ist
            {
                if (!inhalt.isEmpty()) {
                    inhalt.append("\n");
                }
                inhalt.append(zeile);
            }
        }
        // Etwas schief gegangen?
        catch (IOException e) {
            e.printStackTrace();
        }
        return inhalt.toString();
    }
}

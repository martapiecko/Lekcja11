package com.example.x.lekcja11;

public class Telefon {
    private long id;
    private String nazwa;
    private String model;
    private String opis;

    public Telefon(long id, String nazwa, String model, String opis){
        this.id = id;
        this.nazwa = nazwa;
        this.model = model;
        this.opis = opis;
    }

    public long getId() {
        return id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getModel() {
        return model;
    }

    public String getOpis() {
        return opis;
    }
}

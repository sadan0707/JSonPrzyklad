package com.example.surykatka.jsonprzyklad.Modele;

/**
 * Created by Surykatka on 2016-02-13.
 */
public class ModelSprzety {
    private String nazwa;
    private String model;
    private String producent;
    private String fotka;

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    private String serial_number;
    private int rok_prod;


    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {

        this.nazwa = nazwa;
    }

    public String getModel() {

        return model;
    }

    public void setModel(String model) {

        this.model = model;
    }

    public String getProducent() {

        return producent;
    }

    public void setProducent(String producent) {

        this.producent = producent;
    }

    public String getFotka() {

        return fotka;
    }

    public void setFotka(String fotka) {

        this.fotka = fotka;
    }

    public int getRok_prod() {

        return rok_prod;
    }

    public void setRok_prod(int rok_prod) {

        this.rok_prod = rok_prod;
    }
}

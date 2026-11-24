package com.mycompany.tecnolibro.model;

public class Tablet extends Equipo {

    private int tamanioPantalla;

    public Tablet(String codigo, String marca, String modelo, int anioAdquisicion, int tamanioPantalla) {
        super(codigo, marca, modelo, anioAdquisicion);
        this.tamanioPantalla = tamanioPantalla;
        tipo = "Tablet";
    }

    public int getTamanioPantalla() {
        return tamanioPantalla;
    }

    public void setTamanioPantalla(int tamanioPantalla) {
        if(tamanioPantalla <= 0){
            throw new IllegalArgumentException("Pantalla debe ser mayor a cero!.");
        }
        this.tamanioPantalla = tamanioPantalla;
    }

    @Override
    public int getCostoReposicion() {
        return 180000 + (tamanioPantalla <= 10 ? 0 : 30000);
    }
}

package com.mycompany.tecnolibro.model;

public class Notebook extends Equipo {

    private int memoriaRAM;
    private boolean incluyeCargador;

    public Notebook(String codigo, String marca, String modelo, int anioAdquisicion, int memoriaRAM, boolean incluyeCargador) {
        super(codigo, marca, modelo, anioAdquisicion);
        this.memoriaRAM = memoriaRAM;
        this.incluyeCargador = incluyeCargador;
        tipo = "Notebook";
    }

    public int getMemoriaRAM() {
        return memoriaRAM;
    }

    public void setMemoriaRAM(int memoriaRAM) {
        if (memoriaRAM <= 0) {
            throw new IllegalArgumentException("Memoria ram debe ser numero positivo!.");
        }
        this.memoriaRAM = memoriaRAM;
    }

    public boolean isIncluyeCargador() {
        return incluyeCargador;
    }

    public void setIncluyeCargador(boolean incluyeCargador) {
        this.incluyeCargador = incluyeCargador;
    }

    @Override
    public int getCostoReposicion() {
        return 450000 + (incluyeCargador ? 0 : 20000);
    }
}


package com.mycompany.tecnolibro.model;

public abstract class Equipo {

    private String codigo;
    private String marca;
    private String modelo;
    private int anioAdquisicion;
    protected String tipo;

    public Equipo(String codigo, String marca, String modelo, int anioAdquisicion) {
        setCodigo(codigo);
        setMarca(marca);
        setModelo(modelo);
        setAnioAdquisicion(anioAdquisicion);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public final void setCodigo(String codigo) {
        if(codigo == null || codigo.isBlank()){
            throw new IllegalArgumentException("Código no puede estar vacío!.");
        }
        this.codigo = codigo;
    }

    public String getMarca() {
        return marca;
    }

    public final void setMarca(String marca) {
        if(marca == null || marca.isBlank()){
            throw new IllegalArgumentException("Marca no puede estar vacío!.");
        }
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public final void setModelo(String modelo) {
        if(modelo == null || modelo.isBlank()){
            throw new IllegalArgumentException("Modelo no puede estar vacío!.");
        }
        this.modelo = modelo;
    }

    public int getAnioAdquisicion() {
        return anioAdquisicion;
    }

    public final void setAnioAdquisicion(int anioAdquisicion) {
        if(anioAdquisicion < 2015 || anioAdquisicion > 2026){
            throw new IllegalArgumentException("Anio de adquisición debe estar entre 2015 y 2026!.");
        }
        this.anioAdquisicion = anioAdquisicion;
    }

    public abstract int getCostoReposicion();

}

package com.mycompany.tecnolibro.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mycompany.tecnolibro.model.Equipo;
import com.mycompany.tecnolibro.model.Notebook;
import com.mycompany.tecnolibro.model.Tablet;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAOJson implements EquipoDAO {

    private final String RUTA = "C://datos/datos.json";
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(Equipo.class, (JsonDeserializer<Equipo>) (json, type, context) -> {
                JsonObject obj = json.getAsJsonObject();
                if (obj.get("tipo").getAsString().equals("Notebook")) {
                    return context.deserialize(obj, Notebook.class);
                } else {
                    return context.deserialize(obj, Tablet.class);
                }
            }).create();

    private List<Equipo> cargarDatos() {
        File file = new File(RUTA);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (Reader reader = new FileReader(file)) {
            Type tipo = new TypeToken<List<Equipo>>() {}.getType();
            return gson.fromJson(reader, tipo);
        } catch (IOException ioe) {
            throw new RuntimeException("Error al cargar JSON.");
        }
    }

    private void guardarDatos(List<Equipo> equipos) {
        File file = new File(RUTA);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(equipos, writer);
        } catch (IOException ioe) {
            throw new RuntimeException("Error al guardar JSON.");
        }
    }

    @Override
    public List<Equipo> listar() {
        return cargarDatos();
    }

    @Override
    public void agregar(Equipo nuevo) {
        List<Equipo> equipos = cargarDatos();
        equipos.add(nuevo);
        guardarDatos(equipos);
    }

    @Override
    public void eliminar(String codigo) {
        List<Equipo> equipos = cargarDatos();
        equipos.removeIf(p -> p.getCodigo().equals(codigo));
        guardarDatos(equipos);
    }

}

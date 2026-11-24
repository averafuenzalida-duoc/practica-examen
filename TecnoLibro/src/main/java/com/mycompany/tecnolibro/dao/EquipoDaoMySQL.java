package com.mycompany.tecnolibro.dao;

import com.mycompany.tecnolibro.model.Equipo;
import com.mycompany.tecnolibro.model.Notebook;
import com.mycompany.tecnolibro.model.Tablet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class EquipoDaoMySQL implements EquipoDAO {
    
    private final String URL = "jdbc:mysql://localhost:3307/tecnolibro";
    private final String USER = "root";
    private final String PASS = "";
    
    @Override
    public List<Equipo> listar() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            String sql = "SELECT * FROM EQUIPO";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    List<Equipo> equipos = new ArrayList<>();
                    while (rs.next()) {
                        String tipo = rs.getString("tipo");
                        Equipo nuevo;
                        if (tipo.equalsIgnoreCase("notebook")) {
                            nuevo = new Notebook(
                                    rs.getString("codigo"),
                                    rs.getString("marca"),
                                    rs.getString("modelo"),
                                    rs.getInt("anio_adquisicion"),
                                    rs.getInt("memoria_ram"),
                                    rs.getBoolean("incluye_cargador"));
                        } else {
                            nuevo = new Tablet(
                                    rs.getString("codigo"),
                                    rs.getString("marca"),
                                    rs.getString("modelo"),
                                    rs.getInt("anio_adquisicion"),
                                    rs.getInt("tamanio_pantalla"));
                        }
                        equipos.add(nuevo);
                    }
                    return equipos;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return new ArrayList<>();
        }
    }
    
    @Override
    public void agregar(Equipo nuevo) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            String sql = "INSERT INTO EQUIPO VALUES(?,?,?,?,?,?,?,?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, nuevo.getCodigo());
                ps.setString(2, nuevo.getMarca());
                ps.setString(3, nuevo.getModelo());
                ps.setInt(4, nuevo.getAnioAdquisicion());
                ps.setString(5, nuevo.getTipo());
                if (nuevo instanceof Notebook) {
                    ps.setInt(6, ((Notebook) nuevo).getMemoriaRAM());
                    ps.setBoolean(7, ((Notebook) nuevo).isIncluyeCargador());
                    ps.setString(8, null);
                } else {
                    ps.setString(6, null);
                    ps.setString(7, null);
                    ps.setInt(8, ((Tablet) nuevo).getTamanioPantalla());
                }
                ps.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    
    public boolean pregunta(String texto) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(texto);
        Optional<ButtonType> resp = alert.showAndWait();
        if (resp.isPresent()) {
            return resp.get().getText().equals("Aceptar");
        }
        return false;
    }
    
    @Override
    public void eliminar(String codigo) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            String sql = "DELETE FROM EQUIPO WHERE CODIGO = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, codigo);
                if (pregunta("tay seguro maquina?")) {
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    
}

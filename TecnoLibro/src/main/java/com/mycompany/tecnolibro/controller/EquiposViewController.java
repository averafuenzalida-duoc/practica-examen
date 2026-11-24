/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.tecnolibro.controller;

import com.mycompany.tecnolibro.dao.EquipoDAO;
import com.mycompany.tecnolibro.dao.EquipoDAOJson;
import com.mycompany.tecnolibro.dao.EquipoDaoMySQL;
import com.mycompany.tecnolibro.model.Equipo;
import com.mycompany.tecnolibro.model.Notebook;
import com.mycompany.tecnolibro.model.Tablet;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author MP-Alumno
 */
public class EquiposViewController implements Initializable {

    @FXML
    private TableColumn<Equipo, String> colTipo;
    @FXML
    private TableColumn<Equipo, String> colCodigo;
    @FXML
    private TableColumn<Equipo, String> colMarca;
    @FXML
    private TableColumn<Equipo, String> colModelo;
    @FXML
    private TableColumn<Equipo, Integer> colAnio;
    @FXML
    private TableColumn<Equipo, Integer> colCosto;
    @FXML
    private ComboBox<String> comboTipo;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtMarca;
    @FXML
    private TextField txtModelo;
    @FXML
    private TextField txtAnio;
    @FXML
    private TextField txtRam;
    @FXML
    private CheckBox checkCargador;
    @FXML
    private TextField txtPantalla;
    @FXML
    private TableView<Equipo> tablaEquipos;
    @FXML
    private Label lblMensajes;

    EquipoDAO json = new EquipoDaoMySQL();

    private void mostrarExito(String mensaje) {
        lblMensajes.setStyle("-fx-text-fill: green;");
        lblMensajes.setText(mensaje);
    }

    private void mostrarError(String mensaje) {
        lblMensajes.setStyle("-fx-text-fill: red;");
        lblMensajes.setText(mensaje);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtRam.setDisable(true);
        checkCargador.setDisable(true);
        txtPantalla.setDisable(true);
        comboTipo.setItems(FXCollections.observableArrayList("Notebook", "Tablet"));
        colAnio.setCellValueFactory(new PropertyValueFactory<>("anioAdquisicion"));
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colCosto.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getCostoReposicion()).asObject());
        colTipo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getClass().getSimpleName()));
        tablaEquipos.getItems().setAll(json.listar());
    }

    @FXML
    private void comboTipoOnAction(ActionEvent event) {
        boolean note = comboTipo.getValue().equalsIgnoreCase("Notebook");
        txtRam.setDisable(!note);
        checkCargador.setDisable(!note);
        txtPantalla.setDisable(note);
    }

    @FXML
    private void btnAgregarOnAction(ActionEvent event) {
        try {
            Equipo nuevo;
            if (comboTipo.getValue() == null) {
                mostrarError("Seleccione un tipo de equipo!.");
                return;
            }
            if (comboTipo.getValue().equalsIgnoreCase("Notebook")) {
                nuevo = new Notebook(
                        txtCodigo.getText(),
                        txtMarca.getText(),
                        txtModelo.getText(),
                        Integer.parseInt(txtAnio.getText()),
                        Integer.parseInt(txtRam.getText()),
                        checkCargador.isSelected()
                );
                json.agregar(nuevo);
                tablaEquipos.getItems().setAll(json.listar());
                mostrarExito("Equipo agregado correctamente!.");
            } else {
                nuevo = new Tablet(
                        txtCodigo.getText(),
                        txtMarca.getText(),
                        txtModelo.getText(),
                        Integer.parseInt(txtAnio.getText()),
                        Integer.parseInt(txtPantalla.getText())
                );
                json.agregar(nuevo);
                tablaEquipos.getItems().setAll(json.listar());
                mostrarExito("Equipo agregado correctamente!.");
            }
        } catch (NumberFormatException nfe) {
            mostrarError("Ingrese valores numericos válidos!.");
        } catch (IllegalArgumentException iae) {
            mostrarError(iae.getMessage());
        } catch (RuntimeException re) {
            mostrarError(re.getMessage());
        }
    }

    @FXML
    private void btnEliminarOnAction(ActionEvent event) {
        Equipo seleccionado = tablaEquipos.getSelectionModel().getSelectedItem();
        if(seleccionado==null){
            mostrarError("Seleccione un equipo de la lista!.");
            return;
        }
        json.eliminar(seleccionado.getCodigo());
        tablaEquipos.getItems().setAll(json.listar());
        mostrarExito("Equipo eliminado correctamente!.");        
    }

}

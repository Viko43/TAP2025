package com.example.demo1.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Cliente extends Stage {

    private Button btnGuarsdar;
    private TextField txtNomCte, txtDireccion, txttelCte, textEmail;
    private VBox vBox;
    private Scene escena;


    public Cliente() {
        this.setTitle("Registrar Cliente");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI(){
        txtNomCte = new TextField();
        txtDireccion = new TextField();
        txttelCte = new TextField();
        textEmail = new TextField();
        btnGuarsdar = new Button("Guardar");
        vBox = new VBox(txtNomCte,txtDireccion,txttelCte,textEmail,btnGuarsdar);
        escena = new Scene(vBox, 120, 150);
    }
}

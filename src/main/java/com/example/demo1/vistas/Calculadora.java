package com.example.demo1.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculadora extends Stage{
    public char operador;
    public double b, a, c;
    private boolean nuevoNumero = true;
    private Scene escena;
    private TextField txtDisplay;
    private VBox vBox;
    private GridPane gdpTeclado;
    private Button[][] arBtnTeclado;
    String strTeclas[] = {"7","8","9","*","4","5","6","/","1","2","3","+","=","0","C","-"};

    public void CrearUI(){
        CrearKeyboard();
        txtDisplay = new TextField("0");
        //txtDisplay.setPromptText("Teclea tu operación");
        txtDisplay.setEditable(false);
        txtDisplay.setAlignment(Pos.BASELINE_RIGHT);
        vBox = new VBox(txtDisplay,gdpTeclado);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        escena = new Scene(vBox,200,200);
    }

    public void CrearKeyboard(){
        arBtnTeclado = new Button[4][4];
        gdpTeclado = new GridPane();
        gdpTeclado.setHgap(5);
        gdpTeclado.setVgap(5);
        int pos = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arBtnTeclado[i][j] = new Button(strTeclas[pos]);
                int finalPos = pos;
                arBtnTeclado[i][j].setOnAction(event -> EventoTeclado(strTeclas[finalPos]));
                arBtnTeclado[i][j].setPrefSize(50,50);
                gdpTeclado.add(arBtnTeclado[i][j],j,i);
                pos++;
            }
        }
    }

    private void EventoTeclado(String strTecla){
        if (strTecla.matches("[0-9]")){
            if (nuevoNumero){
                txtDisplay.setText(strTecla);
                nuevoNumero = false;
            }else{
                txtDisplay.appendText(strTecla);
            }
        }else if (strTecla.matches("[+\\-*/]")){
            a = Double.parseDouble(txtDisplay.getText());
            operador = strTecla.charAt(0);
            nuevoNumero = true;
        }else if (strTecla.equals("=")){
            b = Double.parseDouble(txtDisplay.getText());
            double resultado = aplicarOperacion();
            txtDisplay.setText(String.valueOf(resultado));
            nuevoNumero = true;
        }else if (strTecla.equals("C")){
            txtDisplay.setText("0");
            a = b = 0;
            nuevoNumero = true;
        }
    }

    private void calcularResultado(){
        String expresion = txtDisplay.getText();
        if (expresion.endsWith("+") || expresion.endsWith("-") || expresion.endsWith("*") || expresion.endsWith("/")) {
            txtDisplay.setText("Error");
            return;
        }
    }

    private int prioridad(char operador){
        if (operador == '+' || operador == '-') return 1;
        if (operador == '*' || operador == '/') return 2;
        return 0;
    }

    private double aplicarOperacion(){
        switch (operador) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return (b != 0) ? a / b : Double.NaN;

            default: return 0;
        }
    }


    public Calculadora(){
        CrearUI();
        this.setScene(escena);
        this.setTitle("Calculadora");
        this.show();
    }
}
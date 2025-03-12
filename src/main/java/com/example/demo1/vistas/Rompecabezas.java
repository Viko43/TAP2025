package com.example.demo1.vistas;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;

public class Rompecabezas extends Application {
    public static final int TILE_SIZE = 100;
    private int gridSize = 3;
    private List<Piezas> tiles = new ArrayList<>();
    private GridPane puzzleGrid;
    private long startTime;
    private Timer timer;
    private Stage primaryStage;
    private Button startButton;
    private ComboBox<String> sizeSelector;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Juego Rompecabezas");


        sizeSelector = new ComboBox<>();
        sizeSelector.getItems().addAll("3x3", "4x4", "5x5");
        sizeSelector.setValue("3x3");


        startButton = new Button("Iniciar Juego");
        startButton.setOnAction(e -> {
            System.out.println(" Botón 'Iniciar Juego' fue presionado.");
            Platform.runLater(this::startGame);
        });


        puzzleGrid = new GridPane();
        puzzleGrid.setAlignment(Pos.CENTER);
        puzzleGrid.setHgap(2);
        puzzleGrid.setVgap(2);

        VBox root = new VBox(10, sizeSelector, startButton, puzzleGrid);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        System.out.println(" Ventana mostrada correctamente.");
    }

    private void startGame() {
        System.out.println(" Iniciando nuevo juego...");
        puzzleGrid.getChildren().clear();
        tiles.clear();

        gridSize = Integer.parseInt(sizeSelector.getValue().split("x")[0]);

        List<Piezas> tempTiles = new ArrayList<>();
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                String imagePath = "row-" + row + "-column-" + col + ".png";

                Piezas piece = new Piezas(imagePath, row, col, this);
                tempTiles.add(piece);
            }
        }

        Collections.shuffle(tempTiles);
        tiles.addAll(tempTiles);

        Platform.runLater(() -> {
            int index = 0;
            for (int row = 0; row < gridSize; row++) {
                for (int col = 0; col < gridSize; col++) {
                    puzzleGrid.add(tiles.get(index), col, row);
                    index++;
                }
            }
            System.out.println(" Tablero generado con imágenes.");
        });

        if (timer != null) {
            timer.cancel();
        }
        startTime = System.currentTimeMillis();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> primaryStage.setTitle("Tiempo: " + ((System.currentTimeMillis() - startTime) / 1000) + "s"));
            }
        }, 0, 1000);

        System.out.println(" Temporizador iniciado.");
    }

    public boolean isPuzzleSolved() {
        System.out.println(" Verificando si el rompecabezas está resuelto...");
        for (Piezas piece : tiles) {
            Integer currentRow = GridPane.getRowIndex(piece);
            Integer currentCol = GridPane.getColumnIndex(piece);
            int correctRow = piece.getCorrectRow();
            int correctCol = piece.getCorrectCol();

            System.out.println(" PIEZA: Esperado (" + correctRow + ", " + correctCol + ") - Actual (" + currentRow + ", " + currentCol + ")");

            if (currentRow == null || currentCol == null || currentRow != correctRow || currentCol != correctCol) {
                return false;
            }
        }

        timer.cancel();
        long timeTaken = (System.currentTimeMillis() - startTime) / 1000;
        saveTime(timeTaken);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Felicidades!");
        alert.setHeaderText(null);
        alert.setContentText("Has completado el rompecabezas en " + timeTaken + " segundos.");
        alert.showAndWait();
        return true;
    }

    private void saveTime(long time) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/times.txt", true))) {
            writer.write("Tiempo: " + time + " segundos\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getGridSize() {
        return gridSize;
    }
}
































package com.example.simuladorestetica.monitors;

import com.example.simuladorestetica.threads.Estilista;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Estilistas {
    public boolean[] estilistasDisponibles;
    private Estetica estetica;
    private AnchorPane anchorPane;
    @FXML
    private ImageView estilista0;
    @FXML
    private ImageView estilista1;
    @FXML
    private ImageView estilista2;

    public Estilistas(AnchorPane anchorPane, Estetica estetica, ImageView estilista0, ImageView estilista1, ImageView estilista2) {
        this.estetica = estetica;
        this.anchorPane = anchorPane;
        estilistasDisponibles = new boolean[3];
        for (int i = 0; i < 3; i++) {
            estilistasDisponibles[i] = true;
        }
        this.estilista0 = estilista0;
        this.estilista1 = estilista1;
        this.estilista2 = estilista2;
    }

    public void enviarEstilista(int x, int y) {
        ImageView imageView = estilista0;
        int id = 0;
        boolean flag = false;

        while (!flag) {
            if (estilistasDisponibles[0]) {
                estilistasDisponibles[0] = false;
                flag = true;
            } else if (estilistasDisponibles[1]) {
                imageView = estilista1;
                id = 1;
                estilistasDisponibles[1] = false;
                flag = true;
            } else if (estilistasDisponibles[2]) {
                imageView = estilista2;
                id = 2;
                estilistasDisponibles[2] = false;
                flag = true;
            }
        }
        System.out.println("Estilista no. " + id+1 + " atendiendo a " + Thread.currentThread().getName());
        Estilista estilista = new Estilista(anchorPane, imageView, x, y, this, id);
        Thread thread = new Thread(estilista);
        thread.start();
    }
}

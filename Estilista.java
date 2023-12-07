package com.example.simuladorestetica.threads;

import com.example.simuladorestetica.monitors.Estetica;
import com.example.simuladorestetica.monitors.Estilistas;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class Estilista implements Runnable {
    private AnchorPane anchorPane;
    private int positionX;
    private int positionY;
    private ImageView imageView;

    private Estilistas estilistas;
    private int id;

    public Estilista(AnchorPane anchorPane, ImageView imageView, int x, int y, Estilistas claseEstilistas, int id) {
        this.anchorPane = anchorPane;
        this.imageView = imageView;
        this.positionX = x;
        this.positionY = y;
        this.estilistas = claseEstilistas;
        this.id = id;
    }

    @Override
    public void run() {
        if (id == 2) {
            estilistas.estilistasDisponibles[0] = true;
            estilistas.estilistasDisponibles[1] = true;
        }

        this.positionX = this.positionX - ((int) imageView.getLayoutX()) + 25;
        this.positionY = this.positionY - ((int) imageView.getLayoutY()) - 10;

        TranslateTransition transition0 = new TranslateTransition(Duration.seconds(0.5), imageView);
        transition0.setToX(this.positionX);
        transition0.setToY(this.positionY);
        transition0.play();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(0.5), imageView);
        transition1.setToX(0);
        transition1.setToY(0);
        transition1.play();

        estilistas.estilistasDisponibles[2] = true;
    }
}

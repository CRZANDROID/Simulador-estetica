package com.example.simuladorestetica.threads;

import com.example.simuladorestetica.monitors.Estetica;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class Cliente implements Runnable {
    private final AnchorPane anchorPane;
    private final Estetica estetica;
    private static String[] posicionesSillas;
    private final boolean genero;
    private String[] posicion;

    public Cliente(AnchorPane anchorPane, Estetica estetica) {
        this.anchorPane = anchorPane;
        this.estetica = estetica;
        posicionesSillas = new String[]{"249 540", "368 540", "483 540", "602 540", "716 540"};
        genero = new Random().nextBoolean();
    }

    @Override
    public void run() {
        Image clienteImage = null;
        try {
            if (genero) {
                clienteImage = new Image(new FileInputStream("src/main/resources/assets/cliente-m.png"));
            } else {
                clienteImage = new Image(new FileInputStream("src/main/resources/assets/cliente-h.png"));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ImageView imageView = new ImageView(clienteImage);

        entrar(imageView);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int idSilla = obtenerLugar(imageView);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        estetica.solicitarServicio(idSilla, posicion);

        try {
            int randomTime = new Random().nextInt(5) + 10;
            Thread.sleep(randomTime * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        estetica.salir(idSilla, Thread.currentThread().getName());

        try {
            salir(imageView);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void entrar(ImageView imageView) {
        imageView.setFitWidth(33);
        imageView.setFitHeight(70);
        Platform.runLater(() -> {
            imageView.setLayoutX(440);
            imageView.setLayoutY(-10);
            anchorPane.getChildren().add(imageView);
        });

        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), imageView);
        transition.setToY(132);
        transition.play();
    }

    private int obtenerLugar(ImageView imageView) {
        int idSilla = estetica.entrarYObtenerLugar(Thread.currentThread().getName());
        posicion = posicionesSillas[idSilla].split(" ");

        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), imageView);
        transition.setToX(Integer.parseInt(posicion[0]) - 440);
        transition.setToY(Integer.parseInt(posicion[1]));
        transition.play();

        return idSilla;
    }

    private void salir(ImageView imageView) throws InterruptedException {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), imageView);
        transition.setToX(-20);
        transition.setToY(-100);
        transition.play();
        Thread.sleep(1000);
        Platform.runLater(() -> {
            anchorPane.getChildren().remove(imageView);
        });
    }
}

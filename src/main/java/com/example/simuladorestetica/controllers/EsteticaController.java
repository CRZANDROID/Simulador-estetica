

package com.example.simuladorestetica.controllers;

import com.example.simuladorestetica.monitors.Estetica;
import com.example.simuladorestetica.monitors.Estilistas;
import com.example.simuladorestetica.threads.CrearClientes;
import com.example.simuladorestetica.threads.Estilista;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.Observable;
import java.util.Observer;

public class EsteticaController implements Observer {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button botonInicio;
    @FXML
    private ImageView estilista0;
    @FXML
    private ImageView estilista1;
    @FXML
    private ImageView estilista2;
    private Estilistas estilistas;

    @FXML
    void iniciar(ActionEvent event) {
        Estetica estetica = new Estetica();
        botonInicio.setDisable(true);
        estetica.addObserver(this);

        estilistas = new Estilistas(anchorPane, estetica, estilista0, estilista1, estilista2);
        CrearClientes crearClientes = new CrearClientes(anchorPane, estetica);

        Thread createClientesThread = new Thread(crearClientes);

        createClientesThread.setDaemon(true);
        createClientesThread.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        synchronized (this) {
            if (((String) arg).contains("servicio")) {
                String[] cadena = ((String) arg).split(" ");
                int x = Integer.parseInt(cadena[1]);
                int y = Integer.parseInt(cadena[2]);
                enviarEstilista(x, y);  
            }
        }
    }

    private void enviarEstilista(int x, int y) {
        estilistas.enviarEstilista(x, y);
    }
}

package com.example.simuladorestetica.threads;

import com.example.simuladorestetica.monitors.Estetica;
import javafx.scene.layout.AnchorPane;

import java.util.concurrent.ThreadLocalRandom;

public class CrearClientes implements Runnable {
    private final AnchorPane anchor;
    private final Estetica estetica;

    public CrearClientes(AnchorPane anchor, Estetica estetica) {
        this.anchor = anchor;
        this.estetica = estetica;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            Cliente cliente = new Cliente(anchor, estetica);
            Thread guestThread = new Thread(cliente);
            guestThread.setName("Cliente " + (i + 1));

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(4000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            guestThread.setDaemon(true);
            guestThread.start();
        }
    }
}

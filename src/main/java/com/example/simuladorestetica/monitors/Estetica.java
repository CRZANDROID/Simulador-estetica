package com.example.simuladorestetica.monitors;

import java.util.Arrays;
import java.util.Observable;

public class Estetica extends Observable {
    public int maxNumClientes;
    public boolean[] sillasOcupadas;
    public int numClientes;

    public Estetica() {
        sillasOcupadas = new boolean[5];
        Arrays.fill(sillasOcupadas, false);
        maxNumClientes = 5;
        numClientes = 0;
    }

    public synchronized int entrarYObtenerLugar(String idCliente) {
        int idSilla = 0;
        try {
            while (numClientes >= maxNumClientes) {
                wait();
            }
            numClientes++;
            for (int i = 0; i < 20; i++) {
                if (!sillasOcupadas[i]) {
                    idSilla = i;
                    sillasOcupadas[i] = true;
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Silla no. " + (idSilla + 1) + " asignada a " + idCliente);
        return idSilla;
    }

    public void solicitarServicio(int idSilla, String[] ubicacion) {
        synchronized (this) {
            String x = ubicacion[0];
            String y = ubicacion[1];
            setChanged();
            notifyObservers("servicio " + x + " " + y);
        }
    }

    public void salir(int idSilla, String idCliente) {
        synchronized (this) {
            numClientes--;
            System.out.println(idCliente + " ha salido de la estetica.");
            sillasOcupadas[idSilla] = false;
            notifyAll();
        }
    }
}

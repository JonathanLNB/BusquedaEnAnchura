package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    Canvas cCanvas;
    @FXML
    Button bIniciar;
    ArrayList<int[][]> respuestas;
    Objeto respuesta;
    Simulador juego;
    /*int[][] mapa = {
            {0,0,0,0,0,0,0,0,0,3},
            {0,3,0,0,0,0,0,0,3,0},
            {0,0,3,0,0,0,0,3,0,0},
            {0,0,0,3,0,-1,3,0,0,0},
            {0,-1,0,0,3,3,1,0,0,0},
            {0,0,0,0,3,3,0,0,0,0},
            {0,0,0,3,0,0,3,0,0,0},
            {0,0,3,0,0,0,0,3,0,0},
            {0,3,0,0,0,0,0,0,3,0},
            {0,0,0,0,0,0,0,0,0,0}
    };*/
    int[][] mapa = {
            {3, 3, 3, 3, -1},
            {0, 0, 0, 0, 0},
            {0, 3, 3, 3, 3},
            {0, 0, 3, 3, 3},
            {1, 3, 3, 3, 3}
    };

    //int[][] mapa = {{3, 3, -1}, {3, 0, 3}, {1, 3, 3}};
    //int[][] mapa = {{3, 3, 3, 0, 0, 0}, {3, -1, 3, 0, 3, 0}, {3, 0, 0, 0, 3, 0}, {3, 3, 3, 3, 0, 0}, {3, 0, 0, 0, 0, 3}, {3, 1, 3, 3, 3, 3}};
    //int[][] mapa = {{3, 3, 3, 3, 3, 3}, {3, 0, 3, 3, 3, 3}, {3, 0, 3, 3, -1, 0}, {0, 0, 0, 0, 3, 0}, {1, 3, 3, 0, 0, 0}, {3, 3, 3, 3, 3, 3}};
    int cont = -1;
    boolean bandera = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        respuestas = new ArrayList<>();
        Image suelo = new Image("Imagenes/sSuelo.png");
        Image pasto = new Image("Imagenes/sArbusto.png");
        Image personaje = new Image("Imagenes/abajo.gif");
        Image tumba = new Image("Imagenes/tumba.png");
        for (int i = 0; i < mapa.length; i++) {
            for (int e = 0; e < mapa.length; e++) {
                cCanvas.getGraphicsContext2D().drawImage(suelo, i * 32, e * 32);
                if (mapa[i][e] == 3)
                    cCanvas.getGraphicsContext2D().drawImage(pasto, i * 32, e * 32);
                if (mapa[i][e] == 1)
                    cCanvas.getGraphicsContext2D().drawImage(personaje, i * 32, e * 32);
                if (mapa[i][e] == -1)
                    cCanvas.getGraphicsContext2D().drawImage(tumba, i * 32, e * 32);
            }
        }
        cCanvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (bandera)
                    dibujar();
                else {
                    iniciar();
                    bIniciar.setText("¡Siguiente paso!");
                    bandera = !bandera;
                }
            }
        });
    }

    public void iniciar() {
        Objeto primero = new Objeto(mapa);
        try {
            juego = new Simulador(cCanvas, mapa.length);
            respuesta = juego.resolver(primero);
            while (respuesta.padre != null) {
                respuestas.add(respuesta.getMatriz());
                respuesta = respuesta.padre;
            }
            cont = respuestas.size();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Este laberinto no tiene solución \uD83D\uDE30");
        }
    }

    public void dibujar() {
        if (cont > 0) {
            cont--;
            juego.imprimeMovimiento(respuestas.get(cont));
        } else if (cont == 0)
            JOptionPane.showMessageDialog(null, "El Foráneo encontró su cerveza ahora es Happy, Happy :3 \uD83D\uDE2D");
    }
}

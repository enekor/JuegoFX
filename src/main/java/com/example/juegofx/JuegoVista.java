package com.example.juegofx;


import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class JuegoVista extends BorderPane {
    private Rectangle paredIzda;
    private Rectangle paredDcha;
    private Rectangle paredInferior;
    private Rectangle paredSuperior;
    private Rectangle tanque;
    private StackPane pista;
    JuegoController controlador;

    public JuegoVista() {
        this.paredIzda = new Rectangle();
        this.paredDcha = new Rectangle();
        this.paredInferior = new Rectangle();
        this.paredSuperior = new Rectangle();
        this.pista= new StackPane();
        this.tanque = new Rectangle();

        paredIzda.setFill(Color.RED);
        paredIzda.heightProperty().bind(pista.heightProperty());
        paredIzda.widthProperty().bind(pista.widthProperty().divide(20));

        paredDcha.setFill(Color.RED);
        paredDcha.heightProperty().bind(pista.heightProperty());
        paredDcha.widthProperty().bind(pista.widthProperty().divide(20));

        paredInferior.setFill(Color.RED);
        paredInferior.heightProperty().bind(pista.heightProperty().divide(20));
        paredInferior.widthProperty().bind(pista.widthProperty());

        paredSuperior.setFill(Color.RED);
        paredSuperior.heightProperty().bind(pista.heightProperty().divide(20));
        paredSuperior.widthProperty().bind(pista.widthProperty());

        tanque.setFill(Color.BLUE);
        tanque.heightProperty().bind(paredIzda.widthProperty());
        tanque.widthProperty().bind(paredIzda.widthProperty());

        pista.getChildren().addAll(paredIzda,paredDcha,tanque,paredInferior,paredSuperior);
        pista.setAlignment(paredIzda, Pos.CENTER_LEFT);
        pista.setAlignment(paredDcha, Pos.CENTER_RIGHT);
        pista.setAlignment(tanque, Pos.CENTER);
        pista.setAlignment(paredInferior,Pos.BOTTOM_CENTER);
        pista.setAlignment(paredSuperior,Pos.TOP_CENTER);
        this.setCenter(pista);
        /*pista.setFocusTraversable(Boolean.TRUE);
        this.setFocusTraversable(Boolean.TRUE);*/

        this.controlador = new JuegoController(paredIzda,paredDcha,paredInferior,paredSuperior,tanque,pista);
    }
}

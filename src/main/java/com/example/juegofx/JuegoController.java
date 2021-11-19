package com.example.juegofx;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class JuegoController {
    private Rectangle paredIzda;
    private Rectangle paredDcha;
    private Rectangle paredInferior;
    private Rectangle paredSuperior;
    private Rectangle tanque;
    private double desplazamiento;
    private Timeline animacion;
    private StackPane pista;
    private double desplazamientoX,desplazamientoY;

    public JuegoController(Rectangle paredIzda, Rectangle paredDcha,
                           Rectangle paredInferior, Rectangle paredSuperior, Rectangle tanque, StackPane pista) {
        this.paredIzda = paredIzda;
        this.paredDcha = paredDcha;
        this.paredInferior = paredInferior;
        this.paredSuperior = paredSuperior;
        this.tanque = tanque;
        this.desplazamiento = 2.0;
        this.pista= pista;


        initGame();
    }

    private void initControls() {
        pista.setOnKeyPressed(e->{
            System.out.println(e.getCode());
            switch(e.getCode()){
                case SPACE:{
                    play();
                    break;
                }
                case LEFT:{
                    desplazamientoX=1;//+=1 para inercia
                    break;
                }
                case RIGHT:{
                    desplazamientoX=-1;
                    break;
                }
                case UP:{
                    desplazamientoY=-1;
                    break;
                }
                case DOWN:{
                    desplazamientoY=1;
                    break;
                }
            }
        });
    }

    /**
     * game loop
     */
    private void initGame(){

        pista.setFocusTraversable(Boolean.TRUE);
        animacion = new Timeline(new KeyFrame(Duration.millis(17),t->{
            initControls();
            moverTanque();
            detectarColision();
        }));
        animacion.setCycleCount(Animation.INDEFINITE);
    }

    private void moverTanque() {
        tanque.setTranslateX(tanque.getTranslateX() + desplazamientoX);
        tanque.setTranslateY(tanque.getTranslateX() + desplazamientoY);
    }

    private void play(){
        animacion.play();
    }

    /**
     * detectamos las colisiones comparando los limites de cada forma (bounds), usamos inParent para que los coja
     * del que se mueve y no de la posicion fija (inLocal)
     */
    private void detectarColision(){

        if(tanque.getBoundsInParent().intersects(paredIzda.getBoundsInParent())
            || tanque.getBoundsInParent().intersects(paredDcha.getBoundsInParent())
            || tanque.getBoundsInParent().intersects(paredInferior.getBoundsInParent())
            || tanque.getBoundsInParent().intersects(paredSuperior.getBoundsInParent()))
            {
            System.out.println("colision");
        }

    }
}

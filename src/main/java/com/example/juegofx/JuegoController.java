package com.example.juegofx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicBoolean;

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
        pista.setFocusTraversable(Boolean.TRUE);
        pista.setOnKeyPressed(e->{
            System.out.println(e.getCode());
            switch(e.getCode()){
                case LEFT:{
                    desplazamientoX=-2;//+=1 para inercia
                    play();
                    break;
                }
                case RIGHT:{
                    desplazamientoX=2;
                    play();
                    break;
                }
                case UP:{
                    desplazamientoY=-2;
                    play();
                    break;
                }
                case DOWN:{
                    desplazamientoY=2;
                    play();
                    break;
                }
            }
        });
    }

    /**
     * game loop, movemos tanque y detectamos colision, una vez detectada o no la colision ponemos el movimiento a 0 para que no queden residuos de los movimientos anteriores
     * y no vaya en diagonal siempre que demos movimiento al eje y
     */
    private void initGame(){
        initControls();
        animacion = new Timeline(new KeyFrame(Duration.millis(17),t->{
            moverTanque();
            detectarColision();
            noMoverTanque();

        }));
        animacion.setCycleCount(1);
    }

    private void moverTanque() {
        tanque.setTranslateX(tanque.getTranslateX() + desplazamientoX);
        tanque.setTranslateY(tanque.getTranslateY() + desplazamientoY);
    }
    private void noMoverTanque() {
        desplazamientoX=0;
        desplazamientoY=0;
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
            popoutDialog();
        }

    }

    private void popoutDialog(){
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        //dialog.initOwner(primaryStage);
        VBox gameOverBox = new VBox();
        HBox botones = new HBox();
        Label text = new Label("GAME OVER");
        Button exit = new Button("EXIT");
        Button reset = new Button("RESET");

        botones.getChildren().addAll(exit,reset);
        gameOverBox.getChildren().addAll(text,botones);
        Scene escena = new Scene(gameOverBox,300,300);

        dialog.setScene(escena);
        dialog.show();

        exit.setOnMouseClicked(v-> {
            System.out.println("exit");
            exitReset(true);
            dialog.close();
        });
        reset.setOnMouseClicked(v->{
            System.out.println("reset");
            exitReset(false);
            dialog.close();
        });
    }

    private void exitReset(Boolean b){
        if(b){
            System.exit(0);
        }
        tanque.setTranslateX(0);
        tanque.setTranslateY(0);
    }
}

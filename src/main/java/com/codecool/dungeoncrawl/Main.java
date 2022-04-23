package com.codecool.dungeoncrawl;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.text.Font;

import static java.lang.Double.MAX_VALUE;
import static javafx.application.Platform.exit;


public class Main extends Application {
    public Game game;
    private final Button exitButton;
    private final Button startButton;
    private final Button optionsButton;
    private final Pane vbox;

    public static void main(String[] args) {
        launch(args);
    }

    public Main() {
        this.exitButton = new Button("EXIT GAME");
        this.startButton = new Button("START GAME");
        this.optionsButton = new Button("OPTIONS");
        this.vbox = new VBox();
    }

    @Override
    public void start(Stage primaryStage) {
        game = new Game();
        primaryStage.setTitle("Fabulous Octopus");

        setMenuLayout();

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();

        buttonsEvent(exitButton, primaryStage);
        buttonsEvent(startButton, primaryStage);

    }

    private void buttonsEvent(Button button, Stage primaryStage) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (button.equals(exitButton)) {
                    exit();
                } else if (button.equals(startButton)) {
                    game.windowGame("Fabulous Octopus");
                    primaryStage.close();

                }

            }
        });
    }

    private void setMenuLayout() {
        vbox.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.valueOf("#472D3C"), CornerRadii.EMPTY, Insets.EMPTY)));

        vbox.setPrefWidth(300);
        vbox.setPrefHeight(0);


        Text text = new Text("FABULOUS OCTOPUS");
        text.setFill(Color.WHITE);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
        vbox.getChildren().add(text);

        startButton.setPrefWidth(MAX_VALUE);
        optionsButton.setPrefWidth(MAX_VALUE);
        exitButton.setPrefWidth(MAX_VALUE);
        startButton.setPrefHeight(MAX_VALUE);
        optionsButton.setPrefHeight(MAX_VALUE);
        exitButton.setPrefHeight(MAX_VALUE);

        startButton.setFont(Font.font("verdana", FontWeight.MEDIUM, FontPosture.REGULAR, 15));
        optionsButton.setFont(Font.font("verdana", FontWeight.MEDIUM, FontPosture.REGULAR, 15));
        exitButton.setFont(Font.font("verdana", FontWeight.MEDIUM, FontPosture.REGULAR, 15));

        vbox.getChildren().addAll(startButton, optionsButton, exitButton);
    }

}

package com.company;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Group;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CoordinateDrawingFX extends Application {
    public static void main(String[] args) {
        launch();
    }

    private GraphicsContext gc;
    private int startX, startY;

    @Override
    public void start(Stage primaryStage) {
        Group group = new Group();
        Canvas canvas = new Canvas(800, 800);
        group.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();

        Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        primaryStage.setTitle("PlotFX");
        primaryStage.show();

        readAndPlot("commandDoc.txt");
    }

    public void readAndPlot(String fileName) {
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public void processLine(String line) {
        String[] tokens = line.split(" ");
        String command = tokens[0];
        int distance = Integer.parseInt(tokens[1]);
        int endX, endY;

        switch(command) {
            case "left":
                endX = Math.max(0, startX - distance);
                gc.strokeLine(startX, startY, endX, startY);
                startX = endX;
                break;

            case "right":
                endX = Math.min(800, startX + distance);
                gc.strokeLine(startX, startY, endX, startY);
                startX = endX;
                break;

            case "up":
                endY = Math.max(0, startY - distance);
                gc.strokeLine(startX, startY, startX, endY);
                startY = endY;
                break;

            case "down":
                endY = Math.min(800, startY + distance);
                gc.strokeLine(startX, startY, startX, endY);
                startY = endY;
                break;

            case "x":
                startX = distance;
                break;

            case "y":
                startY = distance;
                break;

            default:
                System.err.println("Uknown command: " + command);
                break;
        }


    }
}







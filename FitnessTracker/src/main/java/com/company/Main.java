package com.company;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.company.PathToFile.pathToFile;

public class Main extends Application {

    static String path = "./users.xml";

    public static void main(String[] args) throws IOException {
        if (!Files.exists(Path.of(pathToFile).toAbsolutePath())){
            Files.createFile(Path.of(pathToFile).toAbsolutePath());
        }
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/LoginForm.fxml"));

        stage.setTitle("Тренировочка");
        stage.setScene(new Scene(root));
        stage.show();

    }
}

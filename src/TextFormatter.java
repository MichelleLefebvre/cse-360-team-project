import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;

import javax.swing.*;


public class TextFormatter extends Application
{
    private TabPane tabPane;
    private InputPane inputPane;


    public void start(Stage stage)
    {

        StackPane root = new StackPane();


        inputPane = new InputPane();

        root.getChildren().add(inputPane);

        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Text Formatter");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {

        launch(args);


    }
}

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.swing.*;
import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;


public class InputPane extends VBox
{
    private JPanel mainPane;
    private JPanel mainPane2;
    private TextField inputFileTextBox = new TextField();
    private TextArea inputTextBox = new TextArea();
    private Button uploadBtn = new Button("Upload");
    private Button formatTextBtn = new Button("Format File");
    private Label inputLabel;
    private Label errorLabel = new Label("");
    private HBox btnBox = new HBox(10);
    private TextField filePath = new TextField();
    private TextArea outputField = new TextArea();
    private HBox btnBox2 = new HBox(10);
    private Button saveFileBtn = new Button("Save File");
    private Label outputLabel;
    //private OutputPane op = new OutputPane();
    // private TextFormatter tf = new TextFormatter();
    //private List<String> outputFile = new ArrayList<>();
    private File inputFile = null;
    private String allText;

    List<String> lineList = new ArrayList<>();
    //private OutputPane outputPane;

    public InputPane() {
        //this.outputPane = opane;
        this.setSpacing(5);
        this.setPadding(new Insets(25,15,5,15));

        uploadBtn.setFont(Font.font("Verdana", 12));

        btnBox.setAlignment(Pos.CENTER);
        btnBox.getChildren().addAll(inputFileTextBox, uploadBtn);

        inputLabel = new Label("Input:");
        inputLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        inputTextBox.setEditable(false); //disable editting for read-only

        errorLabel = new Label(""); //change this to display hardcoded error message

        formatTextBtn.setPrefSize(660, 30);
        //adds style to buttons implement later
        formatTextBtn.setFont(Font.font("Verdana", 12));
        //formatTextBtn.setBackground(new Background(new BackgroundFill(Color.web("#7592ba"), CornerRadii.EMPTY, Insets.EMPTY)));

        inputFileTextBox.setPrefSize(350,20);

        filePath.setPrefSize(350, 20);
        btnBox2.setAlignment(Pos.CENTER);
        btnBox2.getChildren().addAll(filePath, saveFileBtn);

        outputLabel = new Label("Output:");
        outputLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        errorLabel = new Label(""); //change this to display hardcoded error message

        outputField.setEditable(false);//disable the ability to edit field, read-only

        //saveFileBtn.setPrefSize(200, 20);
        //adds style to buttons implement later
        saveFileBtn.setFont(Font.font("Verdana", 12));

        this.getChildren().addAll(btnBox, inputLabel, inputTextBox, formatTextBtn, outputLabel, outputField, btnBox2, errorLabel);


        uploadBtn.setOnAction(new uploadFileHandler());
        formatTextBtn.setOnAction(new FormatTextHandler());
        saveFileBtn.setOnAction(new saveFileHandler2());

    }

    public List<String> getLineList()
    {
        return lineList;
    }
    private void uploadFile()
    {
        JFileChooser fchooser = new JFileChooser();


        int statusBool = fchooser.showOpenDialog(mainPane);


        if (statusBool == JFileChooser.APPROVE_OPTION) {
            inputFile = fchooser.getSelectedFile();
            inputFileTextBox.setText(inputFile.getPath());
        }

    }

    private void displayInputFileContents()
    {
        StringBuilder stringBuild = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile)))
        {
            String currentLine;
            while((currentLine = br.readLine()) != null)
            {

                stringBuild.append(currentLine).append('\n');
                lineList.add(currentLine); //adds each line fromt ext file inorder to format and manipulate


            }
        }
        catch(IOException e)
        {
            errorLabel.setText("unable to read file");
        }

        inputTextBox.setText(stringBuild.toString());
    }

    private void formatText()
    {


        displayOutputFileContents();

    }

    public void saveFile()
    {
        //get from filePath textfield
        try {
            JFileChooser fchooser = new JFileChooser();


            fchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fchooser.showSaveDialog(mainPane);

            FileWriter txtWriter = new FileWriter(fchooser.getSelectedFile() + ".txt");
            for(int i = 0; i<lineList.size(); i++) {
                txtWriter.write(lineList.get(i));
            }
            txtWriter.flush();
            txtWriter.close();


        }
        catch(IOException e)
        {
            errorLabel.setText("cannot write to file");
        }
    }


    public void displayOutputFileContents() {

        StringBuilder stringBuild = new StringBuilder();

        try {

            for (int i = 0; i < lineList.size(); i++) {

                stringBuild.append(lineList.get(i)).append('\n');
            }
        }
        catch(Exception e)
        {
            errorLabel.setText("unable to read file");
        }

        outputField.setText(stringBuild.toString());
    }

    private class saveFileHandler2 implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent e)
        {
            //if file is empty print error

            //else

            saveFile();
        }
    }

    private class uploadFileHandler implements EventHandler<ActionEvent>
    {

        public void handle(ActionEvent e)
        {
            //if file is empty display error

            //else
            try{
                UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
            }
            catch(UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException er  )
            {

            }
            uploadFile();
            displayInputFileContents();
        }
    }

    private class FormatTextHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent e)
        {
            formatText();
        }
    }
}

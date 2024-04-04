package com.example.demo1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Step6 extends TeacherStorage {
    static String name;
    public static ArrayList<String> list=new ArrayList<>();
    Text verificationMessage = new Text();


    @Override
    public void start(Stage primaryStage) throws IOException{
        // Set the application icon (favicon)
        primaryStage.getIcons().add(image);
        ClassNameForPPT= this.getClass().getName();
        // Create a border pane as the root
        BorderPane root = new BorderPane();

        // Set the header and footer in the border pane
        root.setTop(super.createHeader("Forgot-Password"));
        root.setBottom(super.createFooter());

        // Create a SplitPane for the middle part


        // Set the SplitPane in the center of the border pane
        root.setCenter(createMiddlePartSplit());

        // Create the scene with the root pane
        Scene scene = new Scene(root, 800, 600);

        // Set the scene on the primary stage
        primaryStage.setScene(scene);

        // Set the title of the stage
        primaryStage.setTitle(faviconTitle);

        // Display the stage
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public SplitPane createMiddlePartSplit() throws IOException {
        SplitPane middle = new SplitPane();
        middle.setDividerPositions(0.5);

        // Create the left part with a photo
        photo1Logo.setFitWidth(400);
        photo1Logo.setFitHeight(400);
        StackPane left = new StackPane(photo1Logo);

        // Create the right part with credentials for sign-up
        VBox right = new VBox(10);
        right.setAlignment(Pos.CENTER);
        right.setPadding(new Insets(10));
        right.setStyle("-fx-background-color: #F5F5F5;");

        Text title = new Text("Forgot Password");
        title.setFill(Color.BLACK);
        title.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        TextField nameField = new TextField();
        nameField.setPromptText("email");
        nameField.setMaxWidth(230);
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            name = newValue.trim();
        });

        verificationMessage.setFill(Color.RED);
        verificationMessage.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Button signUpButton = new Button("Next");
        signUpButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        signUpButton.setOnAction(event->{
            if(name==null || name.isEmpty()){
                verificationMessage.setText("Please Fill all the details in the text-field"); // Update the message text
                verificationMessage.setFill(Color.RED);
            }
            else {
                if(name.contains("@") && name.contains(".com")){
                    verificationMessage.setText("Reset-Password Successfully Sent"); // Update the message text
                    verificationMessage.setFill(Color.GREEN);
                }
                else {
                    verificationMessage.setText("The email is valid(format is not correct)"); // Update the message text
                    verificationMessage.setFill(Color.RED);
                }
            }
        });

        VBox fieldsBox = new VBox(10, nameField, signUpButton, verificationMessage);

        fieldsBox.setAlignment(Pos.CENTER);
        right.getChildren().addAll(title, fieldsBox);

        // Add the left and right parts to the SplitPane
        middle.getItems().addAll(left, right);
        return middle;
    }

    @Override
    public StackPane createMiddleSectionStack() throws IOException {
        return null;
    }
}

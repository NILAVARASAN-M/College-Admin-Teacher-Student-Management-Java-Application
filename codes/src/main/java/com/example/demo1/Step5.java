package com.example.demo1;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
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
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Step5 extends TeacherStorage{
    static String name,password,nextClass="";
    public static ArrayList<String> list=new ArrayList<>();
    public static String FurtherName="",FurtherPassword="";
    Text verificationMessage = new Text();
    public static void FileToList() throws IOException {
        File file=new File("user_data.txt");
        Scanner scanner=new Scanner(file);
        while (scanner.hasNextLine()){
            list.add(scanner.nextLine());
        }
    }
    Step5(){
        try {
            Step5.FileToList();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void start(Stage primaryStage) throws IOException{
        // Set the application icon (favicon)
        primaryStage.getIcons().add(image);
        ClassNameForPPT= this.getClass().getName();
        // Create a border pane as the root
        BorderPane root = new BorderPane();

        // Set the header and footer in the border pane
        root.setTop(super.createHeader("Sign-In"));
        root.setBottom(super.createFooter());

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

    public static void main(String[] args) throws IOException{
        FileToList();
        launch(args);
        System.out.println(FurtherName);
        System.out.println(FurtherPassword);
    }

    @Override
    public SplitPane createMiddlePartSplit() throws IOException {
        // Create a SplitPane for the middle part
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

        Text title = new Text("Sign In");
        title.setFill(Color.BLACK);
        title.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        TextField nameField = new TextField();
        nameField.setPromptText("Username/email/collegeID");
        nameField.setMaxWidth(230);
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            name = newValue.trim();
            FurtherName=newValue.trim();
        });

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(230);
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            password = newValue.trim();
            FurtherPassword=newValue.trim();
        });

        verificationMessage.setFill(Color.RED);
        verificationMessage.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Button signUpButton = new Button("Sign In");
        signUpButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        signUpButton.setOnAction(event->{
            String[] temp={name,password};
            boolean a=true;
            for(String str:temp){
                if (str == null || str.isEmpty()) {
                    System.out.println(str);
                    a = false;
                    break;
                }
            }
            boolean b=false;
            if(a){
                int i=0;
                for(String str:list){
                    String[] value=str.split(",");
                    if(value.length>14){
                        if(value[0].equals(name) || value[1].equals(name) || value[3].equals(name) ){
                            if(value[4].equals(password)){
                                b=true;
                            }
                        }
                    }
                }
                if(b){
                    verificationMessage.setText("Successfully Logged-In"); // Update the message text
                    verificationMessage.setFill(Color.GREEN);
                    //after the user-has succesfully logged-in
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(e -> {
                        Stage currentStage = (Stage) signUpButton.getScene().getWindow();
                        currentStage.hide();
                        SignInInfoPage signInInfoPage = new SignInInfoPage();
                        try {
                            signInInfoPage.start(new Stage());
                        }
                        catch (IOException f){
                            f.printStackTrace();
                        }
                    });
                    pause.play();
                }
                else{
                    verificationMessage.setText("Credentials Are Not Matching."); // Update the message text
                    verificationMessage.setFill(Color.RED);
                }
            }
            else {
                verificationMessage.setText("Please Fill all the details in the text-field"); // Update the message text
                verificationMessage.setFill(Color.RED);
            }
        });

        Button ForgetPassword = new Button("Forgot Password");
        ForgetPassword.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        ForgetPassword.setOnAction(event->{
            Stage currentStage = (Stage) ForgetPassword.getScene().getWindow();
            currentStage.hide();
            Step6 step6=new Step6();
            try{
                step6.start(new Stage());
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });

        HBox buttonsBox = new HBox(10, signUpButton, ForgetPassword);
        buttonsBox.setAlignment(Pos.CENTER);

        VBox fieldsBox = new VBox(10, nameField, passwordField, buttonsBox, verificationMessage);


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

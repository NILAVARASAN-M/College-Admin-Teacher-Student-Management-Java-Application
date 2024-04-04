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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public  class Step2  extends TeacherStorage{
    //ForReadOnlyName to the name only
    private String name="",email="",password="";
    static ArrayList<String> list=new ArrayList<>();
    private boolean ForScreenExit=false;
    public static void filetolist() throws IOException {
        File file = new File("user_data.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            list.add(scanner.nextLine());
        }
    }
    Step2(){
        this.ForScreenExit=false;
        try {
            list=new ArrayList<>();
            filetolist();
            for (String str:Step2.list){
                System.out.println(str);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage)  throws IOException{
        ClassNameForPPT= this.getClass().getName();
        primaryStage.getIcons().add(image);

        BorderPane root = new BorderPane();

        root.setTop(super.createHeader("Sign-Up"));
        root.setBottom( super.createFooter());

        // Create a SplitPane for the middle part
        // super is not used here because parent class, is not overriding super, so only.
        root.setCenter(createMiddlePartSplit());

        Scene scene = new Scene(root, 800, 600);
        // only if the details are filled the teacher can exit the page.................
        //************** this for the teacher can't exit that page untill rehistered
        primaryStage.setOnCloseRequest(event -> {
            if (!this.ForScreenExit) {
                event.consume();  // Cancel the close operation if condition is not met
            }
        });


        primaryStage.setScene(scene);

        primaryStage.setTitle(faviconTitle);

        primaryStage.show();
    }

    private void saveUserData() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("user_data.txt",false));
            for(String a:list){
                writer.write(a);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException{
        filetolist();
        for(String a:list){
            System.out.println(a);
        }
        Step4.againListToList();
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

        Text title = new Text("Sign Up");
        title.setFill(Color.BLACK);
        title.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setMaxWidth(200);
        nameField.setText(Step3.ForReadOnlyName);
        nameField.setEditable(false);
        // Set the editable property to false b/c name will be same as above.........................
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.name = Step3.ForReadOnlyName.trim();
            System.out.println(this.name);
        });

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(200);
        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.email = newValue;
        });


        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(200);
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
           this.password = newValue;
        });

        Text verificationMessage = new Text(); // Text element for displaying the verification message
        verificationMessage.setFill(Color.RED); // Set the color of the message
        verificationMessage.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        signUpButton.setOnAction(event -> {
            // Perform the desired action when Sign Up button is clicked
            String[] temp=Step3.list.get(Step3.tracker);
            //
            if(temp[0].equals(Step3.ForReadOnlyName)  && this.email.contains("@") && this.password.length()>5){
                System.out.println("all are perfect");
                String temp1=list.get(Step3.tracker).concat(","+this.email+","+this.password);
                System.out.println(temp1);
                list.set(Step3.tracker,temp1);
                saveUserData();
                System.out.println("hello");
                verificationMessage.setText("Verification successful!"); // Update the message text
                verificationMessage.setFill(Color.GREEN);
                this.ForScreenExit=true;
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> {
                    Stage currentStage = (Stage) signUpButton.getScene().getWindow();
                    currentStage.hide();
                    try{
                        Step4.list=new ArrayList<>();
                        Step4.againListToList();
                        for(String Str:Step4.list){
                            System.out.println(Str);
                        }
                        Step4 step4 = new Step4();
                        step4.start(new Stage());
                    }
                    catch (IOException f){
                        f.printStackTrace();
                    }
                });
                pause.play();
            }
            else {
                verificationMessage.setText("Verification failed. Please check your details."); // Update the message text
                verificationMessage.setFill(Color.RED);
            }
        });


        VBox fieldsBox = new VBox(10, nameField, emailField, passwordField, signUpButton,verificationMessage);
        fieldsBox.setAlignment(Pos.CENTER);
        right.getChildren().addAll(title, fieldsBox);

        // Add the left and right parts to the SplitPane
        middle.getItems().addAll(left, right);

        // Set the SplitPane in the center of the border pane
        return middle;
    }

    @Override
    public StackPane createMiddleSectionStack() throws IOException {
        return null;
    }
}

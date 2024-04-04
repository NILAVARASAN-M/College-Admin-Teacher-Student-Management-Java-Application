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

public class Step3  extends TeacherStorage {

    static ArrayList<String[]> list = new ArrayList<>();
    static int tracker=0;
    //ForReadOnlyName to the name only this is for the step2, and also it is for just passing this name.
    //ForReadOnlyID this is also similar to the name this used in the Step4 classs<--
    public static String ForReadOnlyName="",ForReadOnlyID="";
    // we will store the data we took from the user here. Every time.->static
    public static void TextToList() throws IOException {
        File file = new File("user_data.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String[] UniData = scanner.nextLine().split(",");
            list.add(UniData);
        }
    }
    Step3(){
        try {
            TextToList();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Set the application icon (favicon)
        primaryStage.getIcons().add(image);
        ClassNameForPPT= this.getClass().getName();
        // Create a border pane as the root
        BorderPane root = new BorderPane();

        // Create the middle part with the image and form
        // Set the header, footer, and middle in the border pane
        root.setTop(super.createHeader("Verification"));
        root.setBottom(super.createFooter());
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

    private boolean verifyDetails(String name, String collegeId, String password) {
        if(name.isEmpty() && collegeId.isEmpty() && password.isEmpty()){
            System.out.println("hi");
            return false;
        }
        //********************** have to add nulll            nullllllllllllll
        for(int i=0;i<list.size();i++){
            String[] temp=list.get(i);
            if(temp[0].equals(name) && temp[1].equals(collegeId) && temp[2].equals(password)){
                ForReadOnlyID=temp[1];
                tracker=i;
                return true;
            }
        }
        // Add your verification logic here
        // Compare the details with the stored data in the list or any other verification process
        // For demonstration purposes, let's assume the verification is successful if the name and college ID are not empty
        return false;
    }
    public static void main(String[] args) throws IOException {
        TextToList();
        launch(args);
    }

    @Override
    public SplitPane createMiddlePartSplit() throws IOException {
            photo1Logo.setFitWidth(400);
            photo1Logo.setFitHeight(400);
            StackPane leftPart = new StackPane(photo1Logo);
            StackPane.setAlignment(photo1Logo, Pos.CENTER);
            // Create the right part with the form
            Text verificationHeading = new Text("Verification");
            verificationHeading.setFont(Font.font("Arial", FontWeight.BOLD, 18));

            TextField nameField = new TextField();
            nameField.setPromptText("Name");
            nameField.setMaxWidth(200);

            TextField collegeIdField = new TextField();
            collegeIdField.setPromptText("College ID");
            collegeIdField.setMaxWidth(200);

            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Password");
            passwordField.setMaxWidth(200);

            Button verifyButton = new Button("Verify");
            verifyButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

            Text verificationMessage = new Text(); // Text element for displaying the verification message
            verificationMessage.setFill(Color.RED); // Set the color of the message
            verificationMessage.setFont(Font.font("Arial", FontWeight.BOLD, 14));

            verifyButton.setOnAction(event -> {
                String name = nameField.getText().trim();
                String collegeId = collegeIdField.getText().trim();
                String password = passwordField.getText().trim();


                boolean verificationResult = verifyDetails(name, collegeId, password);
                boolean ForAgainSigningUp=ForAgainSigningUpFunct(name, collegeId, password);

                if (verificationResult) {
                    if (ForAgainSigningUp){
                        //ForReadOnlyName to the name only this is for the step2, and also it is for just passing this name.
                        ForReadOnlyName=name;
                        System.out.println(ForReadOnlyName);
                        verificationMessage.setText("Verification successful!"); // Update the message text
                        verificationMessage.setFill(Color.GREEN); // Set the color to green for successful verification
                    }
                    else {
                        verificationMessage.setText("Verification failed. Already SignedIn"); // Update the message text
                        verificationMessage.setFill(Color.RED);
                    }
                } else {
                    verificationMessage.setText("Verification failed. Please check your details."); // Update the message text
                    verificationMessage.setFill(Color.RED);
                }
                if (verificationResult) {
                    if (ForAgainSigningUp){
                        PauseTransition pause = new PauseTransition(Duration.seconds(2));
                        pause.setOnFinished(e -> {
                            Stage currentStage = (Stage) verifyButton.getScene().getWindow();
                            currentStage.hide();
                            try{
                                Step2.list=new ArrayList<>();
                                Step2.filetolist();
                                for (String str:Step2.list){
                                    System.out.println(str);
                                }
                                Step2 step2 = new Step2();
                                step2.start(new Stage());
                            }
                            catch (IOException f){
                                f.printStackTrace();
                            }
                        });
                        pause.play();
                    }
                }
            });

            VBox rightPart = new VBox(10, verificationHeading, nameField, collegeIdField, passwordField, verifyButton, verificationMessage);
            rightPart.setAlignment(Pos.CENTER);
            rightPart.setPadding(new Insets(10));

            // Create the split pane and add the left and right parts
            SplitPane middle = new SplitPane(leftPart, rightPart);
            middle.setDividerPositions(0.5);

            return middle;
    }

    private boolean ForAgainSigningUpFunct(String name, String collegeId, String password) {
        File file=new File("user_data.txt");
        try {
            Scanner scanner=new Scanner(file);
            while (scanner.hasNextLine()){
                String tem=scanner.nextLine();
                String[] spliarr=tem.split(",");
                if(spliarr[1].equals(collegeId)){
                    if(spliarr.length>4){
                        return false;
                    }
                    else {
                        return true;
                    }
                }
            }
        }
        catch (IOException e){
            System.out.println("file not found");
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public StackPane createMiddleSectionStack() throws IOException {
        return null;
    }
}

// still one more probelem suppose if a teacher have solved signed up aldready and then he dont need to sing-up again so that i one problem
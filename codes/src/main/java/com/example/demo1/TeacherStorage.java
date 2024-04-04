package com.example.demo1;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class TeacherStorage extends Application implements InterfaceClass  {
    public static String ClassNameForPPT="";
    public static Stage currentStage;
    public static String nextClass="";
    TeacherStorage(){
        ClassNameForPPT="";
    }
    @Override
    public HBox createHeader(String header_Title) {
        // Create the college name label
        Text collegeName = new Text(header_Title);
        collegeName.setFill(Color.WHITE);
        collegeName.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        // Create the buttons
        Button homeButton = new Button("Home");
        Button aboutButton = new Button("About");
        Button contactButton = new Button("Contact");
        Button signInButton = new Button("Sign In");
        Button signUpButton = new Button("Sign Up");
        Button nextButton = new Button("-->");

        signInButton.setOnAction(event -> {
            currentStage = (Stage) signInButton.getScene().getWindow();
            currentStage.hide();
            Step5 step5=new Step5();
            try {
                step5.start(new Stage());
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
        signUpButton.setOnAction(event -> {
            currentStage = (Stage) signUpButton.getScene().getWindow();
            currentStage.hide();
            Step3 step3=new Step3();
            try {
                step3.start(new Stage()); //this is the verification part.
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
        contactButton.setOnAction(event->{
            currentStage = (Stage) signUpButton.getScene().getWindow();
            currentStage.hide();
            Step1Contact step1Contact=new Step1Contact();
            try {
                step1Contact.start(new Stage());
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
        homeButton.setOnAction(event->{
            currentStage = (Stage) signUpButton.getScene().getWindow();
            currentStage.hide();
            Step1 step1=new Step1();
            step1.start(new Stage());
        });
        aboutButton.setOnAction(event->{
            currentStage = (Stage) signUpButton.getScene().getWindow();
            currentStage.hide();
            Step1About step1About=new Step1About();
            step1About.start(new Stage());
        });

        // Create the search bar
        TextField searchBar = new TextField();
        searchBar.setPromptText("Enter terms then hit search");
        searchBar.setPrefWidth(160);
        searchBar.setMaxWidth(160);
        searchBar.setAlignment(Pos.CENTER_RIGHT);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            nextClass = newValue.trim();
        });

        nextButton.setOnAction(event->{
            switch (nextClass){
                case "signin"->{
                    currentStage = (Stage) signInButton.getScene().getWindow();
                    currentStage.hide();
                    Step5 step5=new Step5();
                    try {
                        step5.start(new Stage());
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
                case "signup"->{
                    currentStage = (Stage) signUpButton.getScene().getWindow();
                    currentStage.hide();
                    Step3 step3=new Step3();
                    try {
                        step3.start(new Stage());
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
                case "home"->{
                    currentStage = (Stage) signUpButton.getScene().getWindow();
                    currentStage.hide();
                    Step1 step1=new Step1();
                    step1.start(new Stage());
                }
                case "about"->{
                    currentStage = (Stage) signUpButton.getScene().getWindow();
                    currentStage.hide();
                    Step1About step1About=new Step1About();
                    step1About.start(new Stage());
                }
                case "contacts"->{
                    currentStage = (Stage) signUpButton.getScene().getWindow();
                    currentStage.hide();
                    Step1Contact step1Contact=new Step1Contact();
                    try {
                        step1Contact.start(new Stage());
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
                default->{
                    System.out.println("enter proper field");
                }
            }
        });

        // Create an HBox to hold the college name, buttons, and search bar
        HBox header = new HBox(10, collegeName, new Region(), homeButton, aboutButton, contactButton, signInButton, signUpButton, searchBar,nextButton);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: #961300;");
        header.setMinHeight(80);

        // Set the search bar to the right-most corner
        HBox.setHgrow(new Region(), Priority.ALWAYS);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        return header;
    }
    public  StackPane createFooter() {
        Text projectText = new Text("A Project by Group 5");
        projectText.setFill(Color.GRAY);
        projectText.setStyle("-fx-font-size: 14;");
        projectText.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Text headerText = new Text("Website for Teachers");
        headerText.setFill(Color.WHITE);
        headerText.setStyle("-fx-font-size: 24;");
        headerText.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        VBox headerBox = new VBox(headerText, projectText);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setSpacing(5);

        StackPane header = new StackPane();
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: #961300;");

        Button readModeButton = new Button("UML");
        //*****************  previousButton =new Button("<-");
        readModeButton.setOnAction(event -> {
            String pathOFFILEForFunction="A:\\github_repos\\sem_2_JAVA_project\\for github\\codes\\ppts\\";
            if((ClassNameForPPT != null) || !ClassNameForPPT.isEmpty())
            {
                String[] allClassNames={"Step1","Step2","Step3","Step4","Step5","Step6","Step7","Step1About","Step1Contact","TeacherNotification","SignInInfoPage","LeaveReq"};
                String[] splitStr=ClassNameForPPT.split("\\."); // this is actually dot, only regrex-->compiler itself gave
                ClassNameForPPT=splitStr[splitStr.length-1];
                for(String str:allClassNames){
                    if(ClassNameForPPT.equals(str)){
                        System.out.println(str+" page working......");
                        ForOpeningPPt(pathOFFILEForFunction.concat(ClassNameForPPT).concat(".png"));
                        break;
                    }
                }
            }
            else {
                System.out.println("no class-name");
            }
        });
        VBox forButton=new VBox(readModeButton);
        forButton.setAlignment(Pos.CENTER_RIGHT);

        StackPane.setAlignment(headerBox, Pos.CENTER);
        header.getChildren().addAll(headerBox,forButton);
        return header;
    }
    public static void ForOpeningPPt(String imagePath) {
        try {
            // Specify the file path of the image
            File imageFile = new File(imagePath);

            // Check if Desktop is supported
            if (Desktop.isDesktopSupported()) {
                // Get the Desktop instance
                Desktop desktop = Desktop.getDesktop();

                // Check if opening files is supported
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Open the image file using the default application
                    desktop.open(imageFile);
                    System.out.println("Image opened successfully!");
                } else {
                    System.out.println("Opening files is not supported on this platform.");
                }
            } else {
                System.out.println("Desktop is not supported on this platform.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


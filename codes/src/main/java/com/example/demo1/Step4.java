package com.example.demo1;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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

public class Step4 extends TeacherStorage {
    private static String Gender,dob,contact,address,qualification;
    private static String Campus,Subject,Department,Graduate,nextClass="";
    public static ArrayList<String> list=new ArrayList<>();
    public static void againListToList() throws IOException {
        File file=new File("user_data.txt");
        Scanner scanner=new Scanner(file);
        while (scanner.hasNextLine()){
            list.add(scanner.nextLine());
        }
    }
    public static void listtoFIle(){
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
    Step4(){
        try{
            list=new ArrayList<>();
            againListToList();
            for(String Str:Step4.list){
                System.out.println(Str);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void start(Stage primaryStage) {
        // Set the application icon (favicon)
        primaryStage.getIcons().add(image);
        ClassNameForPPT= this.getClass().getName();
        // Create a border pane as the root
        BorderPane root = new BorderPane();

        // Create the middle part with an image and two split panes
        VBox middle = createMiddle();

        // Set the header, middle, and footer in the border pane
        root.setTop(super.createHeader("Personal-Info"));
        root.setCenter(middle);
        root.setBottom(super.createFooter());

        // Create the scene with the root pane
        Scene scene = new Scene(root, 800, 600);

        // Set the scene on the primary stage
        primaryStage.setScene(scene);

        // Set the title of the stage
        primaryStage.setTitle(faviconTitle);

        // Display the stage
        primaryStage.show();
    }

    private VBox createMiddle() {
        // Create the header with college name, buttons, and search bar
        HBox header = createHeader("Sign-In");

        ImageView imageView = new ImageView(new Image("A:\\github_repos\\sem_2_JAVA_project\\for github\\codes\\images\\amrita-main-logo.png"));

        // Set the preserveRatio property of the ImageView to true
        imageView.setPreserveRatio(true);

        // Bind the fitWidth property of the ImageView to the width of the header
        imageView.fitWidthProperty().bind(header.widthProperty());

        // Create a VBox to hold the ImageView

        VBox vbox = new VBox(imageView);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        // Create the SplitPane
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.HORIZONTAL);

        // Create the left part of the SplitPane
        VBox leftPane = new VBox();
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setPadding(new Insets(10));
        leftPane.setSpacing(10);

        // Create the heading for personal information
        Text personalInfoHeading = new Text("Personal Information");
        personalInfoHeading.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        VBox.setMargin(personalInfoHeading, new Insets(10, 0, 0, 0)); // Add margin to create a gap


        // Create the text fields for teacher details

        // Create the text fields for teacher details
        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Full Name *mandatory");
        fullNameField.setMaxWidth(400);
        System.out.println(Step3.ForReadOnlyName);
        fullNameField.setText(Step3.ForReadOnlyName);
        fullNameField.setEditable(false);


        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.setPromptText("Gender *mandatory");
        genderComboBox.getItems().addAll("Male", "Female", "Other");
        genderComboBox.setMaxWidth(400);
        genderComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            Gender = newValue.trim();
        });

        TextField dobField = new TextField();
        dobField.setPromptText("Date of Birth *mandatory");
        dobField.setMaxWidth(400);
        dobField.textProperty().addListener((observable, oldValue, newValue) -> {
            dob = newValue.trim();
        });

        TextField contactField = new TextField();
        contactField.setPromptText("Contact Information *mandatory");
        contactField.setMaxWidth(400);
        contactField.textProperty().addListener((observable, oldValue, newValue) -> {
            contact = newValue.trim();
        });

        TextField addressField = new TextField();
        addressField.setPromptText("Address *mandatory");
        addressField.setMaxWidth(400);
        addressField.textProperty().addListener((observable, oldValue, newValue) -> {
            address = newValue.trim();
        });

        TextField qualificationField = new TextField();
        qualificationField.setPromptText("Educational Qualification *mandatory");
        qualificationField.setMaxWidth(400);
        qualificationField.textProperty().addListener((observable, oldValue, newValue) -> {
            qualification = newValue.trim();
        });


        // Add the heading and text fields to the left pane
        leftPane.getChildren().addAll(
                personalInfoHeading,
                fullNameField,
                genderComboBox,
                dobField,
                contactField,
                addressField,
                qualificationField
        );

        splitPane.getItems().add(leftPane);

        // Create the right part of the SplitPane
        VBox rightPane = new VBox();
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setPadding(new Insets(10));
        rightPane.setSpacing(10);

// Create the heading for college information
        Text collegeInfoHeading = new Text("College Information");
        collegeInfoHeading.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        VBox.setMargin(collegeInfoHeading, new Insets(10, 0, 0, 0)); // Add margin to create a gap

// Create the text fields for college details
        TextField collegeIdField = new TextField();
        collegeIdField.setPromptText("College ID *mandatory");
        collegeIdField.setMaxWidth(400);
        collegeIdField.setText(Step3.ForReadOnlyID);
        collegeIdField.setEditable(false);


        ComboBox<String> campusField = new ComboBox<>();
        campusField.setPromptText("Campus *mandatory");
        campusField.getItems().addAll("Coimbatore", "Amritapuri ", "Bengaluru ","Kochi","Mysuru ","Chennai ");
        campusField.setMaxWidth(400);
        campusField.valueProperty().addListener((observable, oldValue, newValue) -> {
            Campus = newValue.trim();
        });
        TextField subjectField = new TextField();
        subjectField.setPromptText("Subject");
        subjectField.setMaxWidth(400);
        subjectField.textProperty().addListener((observable, oldValue, newValue) -> {
            Subject = newValue.trim();
        });

        ComboBox<String> departmentField = new ComboBox<>();
        departmentField.setPromptText("Campus *mandatory");
        departmentField.getItems().addAll("CSE", "EEE ", "ME ","BA","BT");
        departmentField.setMaxWidth(400);
        departmentField.valueProperty().addListener((observable, oldValue, newValue) -> {
            Department = newValue.trim();
        });

        TextField GraduateField = new TextField();
        GraduateField.setPromptText("Graduate");
        GraduateField.setMaxWidth(400);
        GraduateField.textProperty().addListener((observable, oldValue, newValue) -> {
            Graduate = newValue.trim();
        });

        Text verificationMessage = new Text();
        verificationMessage.setFill(Color.RED);
        verificationMessage.setFont(Font.font("Arial", FontWeight.BOLD, 14));

// Create the submit button
        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white; -fx-font-weight: bold;");
        submitButton.setOnAction(event -> {
            boolean a=true;
            String[] temp={Step3.ForReadOnlyName,Gender,dob,contact,address,qualification,Step3.ForReadOnlyID,Campus,Subject,Department,Graduate};
            for(String str:temp){
                if(str==null || str.isEmpty()){
                    a=false;
                    break;
                }
            }
            if(a){
                String info="";
                for (String str:temp)
                {
                    info=info.concat(",").concat(str);
                }
                info= list.get(Step3.tracker).concat(info);
                list.set(Step3.tracker,info);
                listtoFIle();

                verificationMessage.setText("You are now A Teacher In The Prestigious Amrita"); // Update the message text
                verificationMessage.setFill(Color.GREEN);
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(event1 -> {
                    currentStage = (Stage) submitButton.getScene().getWindow();
                    currentStage.hide();
                    Step5 step5=new Step5();
                    try {
                        step5.start(new Stage());
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                });
                pause.play();
            }
            else {
                verificationMessage.setText("Please Fill all the details in the text-field"); // Update the message text
                verificationMessage.setFill(Color.RED);
            }
        });

// Add the heading, text fields, and submit button to the right pane
        rightPane.getChildren().addAll(
                collegeInfoHeading,
                collegeIdField,
                campusField,
                subjectField,
                departmentField,
                GraduateField,
                submitButton,
                verificationMessage
        );

        splitPane.getItems().add(rightPane);


        // Create the right part of the SplitPane
        // Create the VBox to hold the ImageView and the SplitPane
        VBox middle = new VBox(vbox, splitPane);
        VBox.setVgrow(splitPane, Priority.ALWAYS);

        return middle;
    }

    public static void main(String[] args) throws IOException{
        againListToList();
        launch(args);
    }

    @Override
    public SplitPane createMiddlePartSplit() throws IOException {
        return null;
    }

    @Override
    public StackPane createMiddleSectionStack() throws IOException {
        return null;
    }
}

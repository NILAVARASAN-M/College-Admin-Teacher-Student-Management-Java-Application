package com.example.demo1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class LeaveReq extends TeacherStorage {
    private static String nextClass = "";
    public static String day= "",slot= "",GroundsOfLeave="",SelectedDate="";
    public static String ID_College=""; // this will be the signed user college_id.
    //above all details are needed for sending notification to the admin.
    private static TextField textField;
    private static  TextField mediumTextField;

    public  LeaveReq() throws IOException {
        File file=new File("user_data.txt");
        Scanner scanner=new Scanner(file);
        while(scanner.hasNextLine()){
            String[] comaSplit=scanner.nextLine().split(",");
            boolean a=Step5.FurtherName.equals(comaSplit[0]) || Step5.FurtherName.equals(comaSplit[1]) || Step5.FurtherName.equals(comaSplit[3]);
            if(a && Step5.FurtherPassword.equals(comaSplit[4])){
                ID_College=comaSplit[1]; //because this will be college id-and will be unique.
                break;
            }
        }
    }


    @Override
    public void start(Stage primaryStage) throws IOException{
        primaryStage.getIcons().add(image);
        ClassNameForPPT= this.getClass().getName();
        BorderPane root = new BorderPane();

        root.setTop(super.createHeader("Leave-Request"));
        root.setCenter(createMiddlePartSplit()); // Set the content in the center
        root.setBottom(super.createFooter());

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);

        primaryStage.setTitle(faviconTitle);

        // Display the stage
        primaryStage.show();
    }


//    VBox rightContent = new VBox(20, heading, new VBox(10, emailTextField, new HBox(10, checkButton,CheckButtonMessage)),
//            new HBox(10, timetableButton, emailButton, personalInfoButton),
//            new HBox(10,notificationButton,periodReservationButton),
//            optionsText,
//            messageText);

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    public static void LeaveFileFn(){
        String filename="LeaveFile.txt";
        String data=ID_College.concat(",").concat(day).concat(",").concat(slot).concat(",").concat(SelectedDate).concat(",").concat(GroundsOfLeave);
        try{
            FileWriter fileWriter=new FileWriter(filename,true);
            BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);

            bufferedWriter.write(data);
            bufferedWriter.newLine();
            bufferedWriter.close();
            System.out.println(data);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public SplitPane createMiddlePartSplit() throws IOException {
        Text heading = new Text("Leave Request");
        heading.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        // Select Day heading and ListView
        Text selectDayHeading = new Text("Select Day");
        selectDayHeading.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        Text selectPeriodHeading = new Text("Select Period");
        selectPeriodHeading.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        ListView<String> dayListView = new ListView<>();
        dayListView.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday","Saturday");
        dayListView.setMaxHeight(70);
        dayListView.setMaxWidth(150);
        dayListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Update the text field with the selected day
            textField.setText(newValue);
        });

        // Text field for selected day
        textField = new TextField();
        textField.setMaxWidth(150);
        textField.setPromptText("*day");
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            day = newValue.trim();
        });

        // Slot ListView
        ListView<String> slotListView = new ListView<>();
        slotListView.getItems().addAll("9:00 AM-10:30 AM", "10:40 AM-12:20 PM", "12:20 PM-1:10 PM", "2:30 PM-4:10 PM");
        slotListView.setMaxHeight(70);
        slotListView.setMaxWidth(150);
        slotListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Update the text field with the selected day
            mediumTextField.setText(newValue);
        });

        // Medium-sized text field
        mediumTextField = new TextField();
        mediumTextField.setMaxWidth(150);
        mediumTextField.setPromptText("*period");
        mediumTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            slot = newValue.trim();
        });

        // Request button
        TextField groundsLeave = new TextField();
        groundsLeave.setPromptText("*mandatory Grounds of Leave");
        groundsLeave.setMaxWidth(300);
        groundsLeave.setPrefHeight(70);
        groundsLeave.textProperty().addListener((observable, oldValue, newValue) -> {
            GroundsOfLeave = newValue.trim();
        });


        // Calendar section
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Select Date");


        Text message=new Text("");
        message.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");

        Button requestButton = new Button("Request");
        requestButton.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-font-weight: bold;");
        requestButton.setOnAction(event->{
            LocalDate selectedDate = datePicker.getValue();
            if(selectedDate!=null){
                SelectedDate=selectedDate.toString();
            }
            if(slot==null || day==null || SelectedDate==null || GroundsOfLeave==null ||slot.isEmpty() || day.isEmpty() || SelectedDate.isEmpty() || GroundsOfLeave.isEmpty()){
                message.setText("fill all the fields");
                System.out.println(selectedDate);
                message.setFill(Color.RED);
            }
            else{
                String temp="DATE:".concat(SelectedDate).concat(" DAY:").concat(day).concat(" SLOT:").concat(slot).concat("\n").concat("Reason").concat(GroundsOfLeave);
                message.setText("Request Sent Successfully To Admin\nFor "+temp+"\nWill Let You Know...");
                message.setFill(Color.GREEN);
                LeaveFileFn();
            }
        });

        VBox rightContent = new VBox(20, heading, new HBox(20, selectDayHeading, selectPeriodHeading),
                new HBox(20, dayListView, slotListView),
                new HBox(20, textField, mediumTextField),
                groundsLeave,
                datePicker,
                requestButton,
                message
        );

        rightContent.setAlignment(Pos.CENTER);
        rightContent.setPadding(new Insets(50));
        rightContent.setStyle("-fx-background-color: #E5E5E5;");

        // Left Section (Image)
        VBox leftSection = new VBox();
        leftSection.setAlignment(Pos.CENTER);
        leftSection.setPadding(new Insets(50));

        photo1Logo.setFitWidth(300);
        photo1Logo.setPreserveRatio(true);

        leftSection.getChildren().add(photo1Logo);

        // SplitPane
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(leftSection, rightContent);
        splitPane.setDividerPositions(0.5); // Set the divider position to split the sections equally

        VBox.setVgrow(splitPane, Priority.ALWAYS); // Allow the SplitPane to grow vertically

        HBox contentContainer = new HBox(splitPane);
        contentContainer.setAlignment(Pos.CENTER); // Center the content container

        return new SplitPane(contentContainer);
    }

    @Override
    public StackPane createMiddleSectionStack() throws IOException {
        return null;
    }
}


package com.example.demo1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SignInInfoPage extends TeacherStorage {

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.getIcons().add(image);
        // Create a border pane as the root
        BorderPane root = new BorderPane();
        ClassNameForPPT= this.getClass().getName();

        // Set the header, middle section, and footer in the border pane
        root.setTop(super.createHeader("Options"));
        root.setCenter(createMiddleSectionStack());
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
    @Override
    public StackPane createMiddleSectionStack() throws IOException {
        Button timetableButton = new Button("Time-table Update");
        Button leaveButton = new Button("Applying Leave");
        Button notificationButton=new Button("Notification");
        Button MessageButton=new Button("Email Others");

        timetableButton.setStyle("-fx-background-color: #FF8000; -fx-text-fill: white; -fx-font-size: 16;");
        leaveButton.setStyle("-fx-background-color: #FF8000; -fx-text-fill: white; -fx-font-size: 16;");
        notificationButton.setStyle("-fx-background-color: #FF8000; -fx-text-fill: white; -fx-font-size: 16;");
        MessageButton.setStyle("-fx-background-color: #FF8000; -fx-text-fill: white; -fx-font-size: 16;");
        timetableButton.setOnAction(event->{
            Stage currentStage = (Stage) timetableButton.getScene().getWindow();
            currentStage.hide();
            Step7 Step7=new Step7();
            Step7.start(new Stage());
        });
        leaveButton.setOnAction(event->{
            Stage currentStage = (Stage) leaveButton.getScene().getWindow();
            currentStage.hide();
            try {
                LeaveReq leaveReq=new LeaveReq();
                leaveReq.start(new Stage());
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
        notificationButton.setOnAction(event->{
            try {
                Stage currentStage = (Stage) leaveButton.getScene().getWindow();
                currentStage.hide();
                TeacherNotification teacherNotification=new TeacherNotification();
                teacherNotification.start(new Stage());
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
        StackPane middleSection = new StackPane();
        middleSection.setPadding(new Insets(20));

        // Create an HBox to hold the buttons with spacing
        HBox buttonsContainer = new HBox(20);
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.getChildren().addAll(timetableButton, leaveButton,notificationButton,MessageButton);

        middleSection.getChildren().add(buttonsContainer);

        // Add additional styling
        middleSection.setStyle("-fx-background-color: #F6F6F6; -fx-border-color: #CCCCCC; -fx-border-width: 1px; -fx-border-radius: 10px;");

        return middleSection;
    }
    @Override
    public SplitPane createMiddlePartSplit(){return null;}
    public static void main(String[] args) {
        launch(args);
    }
}


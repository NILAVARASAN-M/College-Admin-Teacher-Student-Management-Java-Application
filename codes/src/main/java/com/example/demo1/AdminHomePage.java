package com.example.demo1;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AdminHomePage extends AdminStotage
{
    private int currentImageIndex = 0;
    public static Text messageText;
    public static String TeacherId="";
    public static String infoOfTeachers="",ForNotificationID="";
    public static boolean a=false,b=false;

    @Override
    public void start(Stage primaryStage) {
        ClassNameForPPT= this.getClass().getName();
        primaryStage.getIcons().add(image);

        BorderPane root = new BorderPane();

        root.setTop(super.createHeader("Home"));
        root.setCenter(createMiddlePartSplit()); // Set the content in the center
        root.setBottom(super.createFooter());

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);

        primaryStage.setTitle(faviconTitle);

        // Display the stage
        primaryStage.show();
    }
    @Override
    public SplitPane createMiddlePartSplit() {
        Text heading = new Text("HOME PAGE");
        heading.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        DatePicker datePicker = new DatePicker();
        datePicker.setMaxWidth(300);

        TextArea shortNotes = new TextArea();
        shortNotes.setPromptText("Select a date and add notes here");
        shortNotes.setMaxWidth(300);
        shortNotes.setPrefHeight(60);

        Button button = new Button("Add");
        button.setStyle("-fx-background-color: blue; -fx-text-fill: white;");

        Text notesMessage = new Text("Add notes on that day..");
        notesMessage.setStyle("-fx-font-size: 14; -fx-fill: #808080;");

        Text currentTimeText = new Text();
        currentTimeText.setStyle("-fx-font-size: 13; -fx-fill: #336699;");

        Text currentDateText = new Text();
        currentDateText.setStyle("-fx-font-size: 13; -fx-fill: #336699;");

        VBox rightContent = new VBox(20, heading, currentTimeText, currentDateText, datePicker, shortNotes, button, notesMessage);
        rightContent.setAlignment(Pos.CENTER);
        rightContent.setPadding(new Insets(50));
        rightContent.setStyle("-fx-background-color: #E5E5E5;");

// Update current time and date
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

        PauseTransition clock = new PauseTransition(Duration.seconds(1));
        clock.setOnFinished(e -> {
            LocalTime currentTime = LocalTime.now();
            LocalDate currentDate = LocalDate.now();
            currentTimeText.setText("Current Time: " + currentTime.format(timeFormatter));
            currentDateText.setText("Current Date: " + currentDate.format(dateFormatter));
            clock.playFromStart();
        });
        clock.play();


        // Left Section (Image)

        //     ****************************** LEFT SECTION  **********************

        VBox leftSection = new VBox();
        leftSection.setAlignment(Pos.CENTER);
        leftSection.setPadding(new Insets(10));
        leftSection.setStyle("-fx-background-color: #F5F5F5;");


        Button nextButton = new Button(">");
        Button prevButton = new Button("<");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(400);
        imageView.setFitHeight(400);

        imageView.setImage(new Image(imageUrls[currentImageIndex]));

        prevButton.setOnAction(event -> {
            currentImageIndex--;
            if (currentImageIndex < 0) {
                currentImageIndex = imageUrls.length - 1;
            }
            imageView.setImage(new Image(imageUrls[currentImageIndex]));
        });

        nextButton.setOnAction(event -> {
            currentImageIndex++;
            if (currentImageIndex >= imageUrls.length) {
                currentImageIndex = 0;
            }
            imageView.setImage(new Image(imageUrls[currentImageIndex]));
        });

        HBox arrowButtons = new HBox(10, prevButton, nextButton);
        arrowButtons.setAlignment(Pos.CENTER);
        leftSection.getChildren().addAll(arrowButtons, imageView);

        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(leftSection, rightContent);
        splitPane.setDividerPositions(0.5);
        return splitPane;
    }

    @Override
    public StackPane createMiddleSectionStack() throws IOException {
        return null;
    }
    public static void main(String[] args) throws IOException {
        launch(args);
    }
}

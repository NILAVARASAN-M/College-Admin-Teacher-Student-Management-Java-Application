package com.example.demo1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Step1About extends TeacherStorage {
    static String name,nextClass="";
    public static ArrayList<String> list=new ArrayList<>();

    Text verificationMessage = new Text();

    @Override
    public void start(Stage primaryStage) {
        // Set the application icon (favicon)
        primaryStage.getIcons().add(image);
        ClassNameForPPT= this.getClass().getName();
        // Create a border pane as the root
        BorderPane root = new BorderPane();


        // Set the header, content, and footer in the border pane
        root.setTop(createHeader("About"));
        root.setCenter(createContent());
        root.setBottom(createFooter());

        // Create the scene with the root pane
        Scene scene = new Scene(root, 800, 600);

        // Set the scene on the primary stage
        primaryStage.setScene(scene);

        // Set the title of the stage
        primaryStage.setTitle(faviconTitle);

        // Display the stage
        primaryStage.show();
    }

    private ScrollPane createContent() {
        Text projectText = new Text("A Project by Group 5");
        projectText.setFill(Color.GRAY);
        projectText.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

        Text titleText = new Text("22AIE111 Object Oriented Programming in Java");
        titleText.setFill(Color.BLACK);
        titleText.setStyle("-fx-font-size: 28; -fx-font-weight: bold; -fx-underline: true;");

        Text descriptionText = new Text("Simplified Timetable Management and Substitution System for Amrita College using Java");
        descriptionText.setFill(Color.BLACK);
        descriptionText.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        Text teamText = new Text("GROUP-5, BATCH-B");
        teamText.setFill(Color.BLACK);
        teamText.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

        Text aboutText = new Text("This project aims to address the challenge of managing timetables for teachers in Amrita \nCollege. It provides a Java-based application that automates the process of finding substitute \nteachers when a regular teacher is absent during a period. The application\n leverages existing timetables to identify available periods and notifies teachers \nabout free periods. It also notifies students about substitute teachers assigned \nto conduct the class in case of teacher absences. The application aims to streamline\n the process, minimize disruptions, and improve communication.");
        aboutText.setFill(Color.BLACK);
        aboutText.setStyle("-fx-font-size: 18;");

        TextFlow aboutTextFlow = new TextFlow();
        aboutTextFlow.setLineSpacing(10.0);
        aboutTextFlow.getChildren().addAll(
                new Text("MODULE 1: CLASS SCHEDULE GENERATOR\n"),
                new Text("  - Automates finding substitute teachers by leveraging existing timetables\n"),
                new Text("  - Identifies available periods and notifies teachers about free periods\n\n"),
                new Text("MODULE 2: TEST RESERVATION, GENERAL MEETING, PUBLIC EVENTS, HOLIDAY\n"),
                new Text("  - Manages reservations for tests, general meetings, public events, and holidays\n"),
                new Text("  - Ensures proper scheduling and avoids conflicts with regular classes\n\n"),
                new Text("MODULE 3: FREE PERIOD NOTIFICATION AND RESERVATION\n"),
                new Text("  - Allows teachers to indicate availability for substitute teaching during free periods\n"),
                new Text("  - Teachers receive notifications and can reserve periods for their classes\n\n"),
                new Text("MODULE 4: UPDATED CLASS TIMING\n"),
                new Text("  - Manages changes and updates to the class schedule\n"),
                new Text("  - Allows modifications to class timings and room allocations\n\n"),
                new Text("TEAM MEMBER’S CONTRIBUTION IN THE PROJECT:\n\n"),
                new Text("S.NO\tNAME\tCONTRIBUTION\n"),
                new Text("1\tM.NILAVARASAM\tMODULE 1.\n"),
                new Text("2\tMANTHOSH\tMODULE .\n"),
                new Text("3\tKL AMRITHA NANDINI\tMODULE 3.\n"),
                new Text("4\tCH ARJUN\tMODULE 4.\n")
        );
        aboutTextFlow.setStyle("-fx-font-size: 18;");

        VBox content = new VBox(20, projectText, titleText, descriptionText, teamText, aboutText, aboutTextFlow);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: #F5F5F5;");

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setStyle("-fx-background-color: transparent;");

        return scrollPane;
    }

    public static void main(String[] args) throws IOException {
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

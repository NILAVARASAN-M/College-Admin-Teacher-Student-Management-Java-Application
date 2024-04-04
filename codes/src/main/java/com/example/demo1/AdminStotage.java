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

public abstract class AdminStotage extends Application implements InterfaceClass {
    public static Stage currentStage;
    public static String ClassNameForPPT="";
    public static Button readMode;
    AdminStotage(){
        ClassNameForPPT="";
    }

    public  StackPane createFooter() {
        Text projectText = new Text("A Project by Group 5");
        projectText.setFill(Color.GRAY);
        projectText.setStyle("-fx-font-size: 14;");
        projectText.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Text headerText = new Text("Website for Admin");
        headerText.setFill(Color.WHITE);
        headerText.setStyle("-fx-font-size: 24;");
        headerText.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        VBox headerBox = new VBox(headerText, projectText);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setSpacing(5);

        readMode=createStyledButton("Read Mode");
        ForPPTReadMode();
        VBox forButton=new VBox(readMode);
        forButton.setAlignment(Pos.CENTER_RIGHT);

        StackPane header = new StackPane();
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: #004E98;");

        StackPane.setAlignment(headerBox, Pos.CENTER);
        header.getChildren().addAll(headerBox,forButton);

        return header;
    }
    public static void ForPPTReadMode(){
        readMode.setOnAction(event -> {
            String pathOFFILEForFunction="A:\\github_repos\\sem_2_JAVA_project\\for github\\codes\\ppts\\";
            if((ClassNameForPPT != null) || !ClassNameForPPT.isEmpty())
            {
                System.out.println(ClassNameForPPT);
                String[] allClassNames={"AdminAllTeachersInfo","AdminHomePage","AdminNotification","AdminRecruitTeachers","TeacherAdmin"};
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
    }


    public  HBox createHeader(String heading) {
        // Create the college name label
        Text collegeName = new Text(heading);
        collegeName.setFill(Color.WHITE);
        collegeName.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        // Create buttons
        Button teacherInfo = createStyledButton("Teacher-Info");
        Button homeButton = createStyledButton("Home");
        Button AllTeachersButton = createStyledButton("All Teachers info");
        Button newTeach = createStyledButton("NewRecruit");
        Button periodReservationButton = createStyledButton("Period Reservation");
        Button nextButton = createStyledButton("-->");
        AllTeachersButton.setOnAction(event->{
            currentStage=(Stage) AllTeachersButton.getScene().getWindow();
            currentStage.hide();
            AdminAllTeachersInfo adminAllTeachersInfo=new AdminAllTeachersInfo();
            try{
                adminAllTeachersInfo.start(new Stage());

            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
        teacherInfo.setOnAction(event->{
            currentStage=(Stage) teacherInfo.getScene().getWindow();
            currentStage.hide();
            TeacherAdmin teacherAdm=new TeacherAdmin();
            teacherAdm.start(new Stage());
        });
        homeButton.setOnAction(event->{
            currentStage=(Stage) homeButton.getScene().getWindow();
            currentStage.hide();
            AdminHomePage adminHomePage=new AdminHomePage();
            adminHomePage.start(new Stage());
        });
        newTeach.setOnAction(event->{
            currentStage=(Stage) newTeach.getScene().getWindow();
            currentStage.hide();
            AdminRecruitTeachers adminRecruitTeachers=new AdminRecruitTeachers();
            try {
                adminRecruitTeachers.start(new Stage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        // Create the search bar
        TextField searchBar = createStyledTextField();

        // Button action
        nextButton.setOnAction(event -> {
            // your action here
        });

        // Create an HBox to hold the college name, buttons, and search bar
        HBox header = new HBox(10, collegeName, new Region(), homeButton, teacherInfo, AllTeachersButton, newTeach, periodReservationButton, searchBar, nextButton);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: #004E98;");
        header.setMinHeight(80);

        // Set the search bar to the right-most corner
        HBox.setHgrow(new Region(), Priority.ALWAYS);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        return header;
    }

    private static Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        return button;
    }

    private static TextField createStyledTextField() {
        TextField textField = new TextField();
        textField.setPromptText("Enter terms then hit search");
        textField.setPrefWidth(200);
        textField.setMaxWidth(200);
        textField.setAlignment(Pos.CENTER_RIGHT);
        textField.setStyle("-fx-font-size: 14px; -fx-border-color: #007BFF; -fx-border-radius: 5px;");
        return textField;
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

package com.example.demo1;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Step7 extends TeacherStorage {
    public static String FileName=""; //this filename is nothing but the current user who signed-in that user college-id, is used also in the LeaveReq page
    private static Text additionalText;
    private static Button confirmButton;

    private static ArrayList<String> textFieldValues = new ArrayList<>();
    // public static ArrayList<ArrayList<String>> FinalList=new ArrayList<>();
    public static void ForFileName() throws IOException {
        File file=new File("user_data.txt");
        Scanner scanner=new Scanner(file);
        while(scanner.hasNextLine()){
            String[] comaSplit=scanner.nextLine().split(",");
            boolean a=Step5.FurtherName.equals(comaSplit[0]) || Step5.FurtherName.equals(comaSplit[1]) || Step5.FurtherName.equals(comaSplit[3]);
            if(a && Step5.FurtherPassword.equals(comaSplit[4])){
                FileName=comaSplit[1]; //because this will be college id-and will be unique.
                break;
            }
        }
    }
    public static String[][] TransposedMatrix=new String[6][4];

    @Override
    public void start(Stage primaryStage) {
        primaryStage.getIcons().add(image);
        ClassNameForPPT= this.getClass().getName();
        BorderPane root = new BorderPane();

        VBox timetable = createTimetable();

        root.setTop(super.createHeader("Timetable-Wizard"));
        root.setCenter(timetable);
        root.setBottom(super.createFooter());

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);

        primaryStage.setTitle(faviconTitle);

        // Display the stage
        primaryStage.show();
    }
    private VBox createTimetable() {
        VBox timetable = new VBox();
        timetable.setSpacing(10);
        timetable.setPadding(new Insets(10));
        timetable.setAlignment(Pos.CENTER);

        Text timetableTitle = new Text("Time Table");
        timetableTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        HBox weekdays = new HBox();
        weekdays.setSpacing(10);
        weekdays.setAlignment(Pos.CENTER);

        List<String> daysOfWeek = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
        for (String day : daysOfWeek) {
            Text dayText = new Text(day);
            dayText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            weekdays.getChildren().add(dayText);
        }

        GridPane slotsGridPane = new GridPane();
        slotsGridPane.setHgap(10);
        slotsGridPane.setVgap(10);
        slotsGridPane.setAlignment(Pos.CENTER);

        List<String> timeSlots = Arrays.asList("9:00 am to 10:30 am", "10:40 am to 12:20 pm", "12:20 pm to 1:10 pm", "2:30 pm to 4:10 pm");
        List<String> classes = Arrays.asList("Class 1", "Class 2", "Class 3", "Class 4", "Class 5", "Class 6","Break");

        for (int i = 0; i < timeSlots.size(); i++) {
            String timeSlot = timeSlots.get(i);

            Text timeSlotText = new Text(timeSlot);
            timeSlotText.setFont(Font.font("Arial", FontWeight.BOLD, 14));

            for (int j = 0; j < daysOfWeek.size(); j++) {
                String day = daysOfWeek.get(j);

                ListView<String> classListView = new ListView<>();
                classListView.getItems().addAll(classes);
                classListView.setPrefHeight(100);

                TextField textField = new TextField();
                textField.setPrefWidth(120);
                textField.setMaxWidth(120);
                textField.setEditable(false);
                textField.setAlignment(Pos.CENTER);

                // Add selection listener to the ListView
                classListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        textField.setText(newValue);
                    }
                });

                VBox classBox = new VBox(5, classListView, textField);
                classBox.setAlignment(Pos.CENTER);
                slotsGridPane.add(classBox, j, i);
            }

            slotsGridPane.add(timeSlotText, daysOfWeek.size(), i);
        }

        confirmButton = new Button("Confirm");
        confirmButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        confirmButton.setPrefWidth(80);

        additionalText = new Text("");
        additionalText.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        confirmButton.setOnAction(event -> {
                    textFieldValues.clear(); // Clear the ArrayList before adding new values

                    for (Node node : slotsGridPane.getChildren()) {
                        if (node instanceof VBox) {
                            VBox classBox = (VBox) node;
                            TextField textField = (TextField) classBox.getChildren().get(1);
                            textFieldValues.add(textField.getText());
                        }
                    }
                    try {
                        if(For2dList()){
                            timetableToFile();
                            additionalText.setText("Ur TimeTable is successfully updated...");
                            additionalText.setFill(Color.GREEN);
                            PauseTransition pause = new PauseTransition(Duration.seconds(2));
                            pause.setOnFinished(e -> {
                                additionalText.setText("Redirecting to the teachers-Info Page.......");
                            });
                            pause.play();
                            pause.setOnFinished(e->{
                                Stage currentStage = (Stage) confirmButton.getScene().getWindow();
                                currentStage.hide();
                                Step5 step5=new Step5();
                                try {
                                    step5.start(new Stage());
                                }
                                catch (IOException f){
                                    f.printStackTrace();
                                }
                            });
                            pause.play();
                        }
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
        );

        timetable.getChildren().addAll(timetableTitle, weekdays, slotsGridPane, confirmButton,additionalText);
        return timetable;
    }

    public static void timetableToFile() throws IOException{
        // FileWriter fileWriter=new FileWriter(Step5.name.concat(".txt"));
        ForFileName();
        FileWriter fileWriter=new FileWriter(FileName.concat(".txt"));
        String[] DaysInWeek={"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        BufferedWriter writer = new BufferedWriter(fileWriter);
        int i=0;
        for(String[] a:TransposedMatrix){
            String temp=DaysInWeek[i];
            for(String str:a){
                temp=temp.concat(",").concat(str);
            }
            writer.write(temp);
            writer.newLine();
            i++;
        }
        writer.close();
    }
    public static boolean For2dList(){
        int tracker=0;
        String[][] FinalArr=new String[4][6];
        for(int i=0;i<4;i++){
            for(int j=0;j<6;j++){
                if(textFieldValues.get(tracker)==null || textFieldValues.get(tracker).equals("")){
                    additionalText.setText("please fill all the slot's available");
                    additionalText.setFill(Color.RED);
                    return false;
                }
                else {
                    FinalArr[i][j]=textFieldValues.get(tracker);
                    tracker++;
                    TransposedMatrix[j][i]=FinalArr[i][j];
                }
            }
        }
        return true;
        //now taking the transpose of the because the details are filled like each class in a week.....
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

package com.example.demo1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AdminNotification extends AdminStotage
{
    private static String day = "", slot = "", date = "", reason = "";
    private static Text leave;
    public static boolean forIf = false, LeaveApprovalBoolean = false;
    VBox rightContent;

    @Override
    public void start(Stage primaryStage) {
        ClassNameForPPT= this.getClass().getName();
        day = ""; slot = "";date = "";
        reason = "";
        forIf = false;
        LeaveApprovalBoolean = false;
        try {
            print();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        primaryStage.getIcons().add(image);

        BorderPane root = new BorderPane();


        root.setTop(super.createHeader("Notification"));
        root.setCenter(createMiddlePartSplit()); // Set the content in the center
        root.setBottom(super.createFooter());

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);

        primaryStage.setTitle(faviconTitle);
        primaryStage.show();
    }

    @Override
    public SplitPane createMiddlePartSplit() {
        Text heading = new Text("ID:" + TeacherAdmin.ForNotificationID + " Notification's");
        heading.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        Text message = new Text("");
        message.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");

        try {
            DeclaringLeaveVariables();
            String temp0 = "______LEAVE REQUEST:_____\n";
            String temp2 = "DAY: ".concat(day).concat("\n").concat("SLOT: ").concat(slot).concat("\n").concat("DATE: ").concat(date).concat("\n").concat("Reason:\n").concat(reason);
            String temp1 = "ID: ".concat(TeacherAdmin.ForNotificationID).concat("\n");
            leave = new Text(temp0.concat(temp1.concat(temp2)));
            leave.setStyle("-fx-font-size: 14;");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (forIf) {
            Button approve = new Button("Approve");
            approve.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
            approve.setOnAction(event -> {
                message.setText("ID: " + TeacherAdmin.ForNotificationID + " pass has been\n successfully APPROVED");
                message.setFill(Color.GREEN);
                LeaveApprovalBoolean = true;
                String str=TeacherAdmin.ForNotificationID.concat(",").concat(day).concat(",").concat(slot).concat(",").concat(date);
                FileForTeachers(str);
                LeaveApprovalRejection();
                deleteLeaveMessage();
            });
            Button cancel = new Button("Reject");
            cancel.setStyle("-fx-background-color: black; -fx-text-fill: white;");
            cancel.setOnAction(event -> {
                message.setText("ID: " + TeacherAdmin.ForNotificationID + " pass has been\n REJECTED");
                message.setFill(Color.RED);
                LeaveApprovalBoolean = false;
                LeaveApprovalRejection();
                deleteLeaveMessage();
            });
            rightContent = new VBox(20, heading, new VBox(10, leave),
                    new HBox(10, approve, cancel), message);
        } else {
            Text tem = new Text("               NO-notification");
            tem.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
            tem.setFill(Color.DEEPPINK);
            rightContent = new VBox(20, heading, new VBox(10, tem));
        }

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

        return splitPane;
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    public static void DeclaringLeaveVariables() throws IOException {
        File file = new File("LeaveFile.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String[] str = scanner.nextLine().split(",");
            if (str[0].equals(TeacherAdmin.ForNotificationID)) {
                day = str[1];
                slot = str[2];
                date = str[3];
                reason = str[4];
                forIf = true;
                break;
            }
        }
    }

    public static void LeaveApprovalRejection() {
        try {
            FileWriter fileWriter = new FileWriter("LeaveRequest.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String temp = "rejected";
            if (LeaveApprovalBoolean) {
                temp = "approved";
            }
            String newtemp=day.concat(",").concat(slot).concat(",").concat(date);
            temp = TeacherAdmin.ForNotificationID.concat(",").concat(temp).concat(",").concat(newtemp);
            bufferedWriter.write(temp);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //this function is for when the admin accepts the pass, that pass-history should be removed . that pass requested history, so only the function

    public static void deleteLeaveMessage() {
        try {
            File inputFile = new File("LeaveFile.txt");
            File outputFile = new File("temp.txt");

            Scanner scanner = new Scanner(inputFile);
            FileWriter fileWriter = new FileWriter(outputFile);
            boolean d=false;
            while (scanner.hasNextLine()) {
                String val = scanner.nextLine();
                String[] str = val.split(",");
                if (!TeacherAdmin.ForNotificationID.equals(str[0]) || d) {
                    fileWriter.write(val + System.lineSeparator()); // Same as "\n"
                }
                else {
                    d=true;
                    //this boolean is for when if there are multiple pass request from same user, in that case this is required.
                }
            }
            scanner.close();
            fileWriter.close();

            appendFileToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void appendFileToFile() {
        try {
            File tempFile = new File("temp.txt");
            File leaveFile = new File("LeaveFile.txt");

            FileWriter fileWriter = new FileWriter(leaveFile, false); // true for append mode

            Scanner scanner = new Scanner(tempFile);
            while (scanner.hasNextLine()) {
                fileWriter.write(scanner.nextLine());
                fileWriter.write(System.lineSeparator()); // Add line separator if necessary
            }
            scanner.close();
            fileWriter.close();

            tempFile.delete(); // Delete the temp file if needed
            System.out.println("Content appended successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void FileForTeachers(String temp){
        try {
            FileWriter fileWriter = new FileWriter("ForTeachersLeave.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(temp);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void print() throws IOException{
        File file=new File("LeaveFile.txt");
        Scanner scanner=new Scanner(file);
        while (scanner.hasNextLine()){
            System.out.println(scanner.nextLine());
        }
        scanner.close();
    }

    @Override
    public StackPane createMiddleSectionStack() throws IOException {
        return null;
    }
}
//for notificationID is used here

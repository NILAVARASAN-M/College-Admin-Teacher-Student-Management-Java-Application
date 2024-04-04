package com.example.demo1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class TeacherAdmin extends AdminStotage {
    public static Text messageText;
    public static String TeacherId = "";
    public static String infoOfTeachers = "", ForNotificationID = "";
    public static boolean a = false, b = false, thisBooleanForWrongID = false;

    @Override
    public void start(Stage primaryStage) {
        ClassNameForPPT = this.getClass().getName();
        primaryStage.getIcons().add(image);
        BorderPane root = new BorderPane();

        root.setTop(super.createHeader("Teacher Personal Info"));
        root.setCenter(createMiddlePartSplit()); // Set the content in the center
        root.setBottom(super.createFooter());

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);

        primaryStage.setTitle(faviconTitle);
        primaryStage.show();
    }

    public SplitPane createMiddlePartSplit() {
        Text heading = new Text("Teachers Information");
        heading.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        TextField emailTextField = new TextField();
        emailTextField.setPromptText("*only College ID");
        emailTextField.setMaxWidth(400);
        emailTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            TeacherId = newValue.trim(); //trim is also used, b/c of space charactes
        });
        messageText = new Text("Welcome!!!!!");
        messageText.setStyle("-fx-font-size: 15;");
        messageText.setVisible(false);

        Text CheckButtonMessage = new Text("");
        CheckButtonMessage.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");


        Button timetableButton = new Button("Timetable Check");
        timetableButton.setStyle("-fx-background-color: #228B22; -fx-text-fill: white;");
        timetableButton.setVisible(false);
        timetableButton.setOnAction(event -> {
            if (b) {
                //to see if the teacher has timetable
                if (VerificationFinButTimeTableNO(TeacherId)) {
                    try {
                        String[] lastTemp = infoOfTeachers.split(",");
                        String timetable = "slot1    ||slot2    ||slot3    ||slot4    ||\n";
                        File file = new File(lastTemp[1].concat(".txt"));
                        System.out.println(lastTemp[1].concat(".txt"));
                        Scanner scanner = new Scanner(file);
                        while (scanner.hasNextLine()) {
                            String newValue = timeStruct(scanner.nextLine());
                            timetable = timetable.concat(newValue).concat("\n");
                        }
                        messageText.setText(timetable);
                        messageText.setFill(Color.DEEPPINK);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    messageText.setText("Sorry still ID:" + TeacherId + "\nhas not updated his\ntimetable");
                    messageText.setFill(Color.RED);
                }
            }
        });

        Button emailButton = new Button("Email");
        emailButton.setStyle("-fx-background-color: #228B22; -fx-text-fill: white;");
        emailButton.setVisible(false);
        emailButton.setOnAction(event -> {
            if (b) {
                String[] tem = infoOfTeachers.split(",");
                messageText.setText("redirecting to Requested Email->" + tem[3]);
                messageText.setFill(Color.TOMATO);
            }
        });
        Button personalInfoButton = new Button("Personal Info");
        personalInfoButton.setStyle("-fx-background-color: #228B22; -fx-text-fill: white;");
        personalInfoButton.setVisible(false);
        personalInfoButton.setOnAction(event -> {
            messageText.setText(TeacherDetails(infoOfTeachers));
            messageText.setStyle("-fx-font-size: 13;");
            messageText.setFill(Color.PURPLE);
        });

        Button notificationButton = new Button("Notification");
        notificationButton.setStyle("-fx-background-color: #228B22; -fx-text-fill: white;");
        notificationButton.setVisible(false);
        notificationButton.setOnAction(event -> {
            Stage currentStage = (Stage) notificationButton.getScene().getWindow();
            currentStage.hide();
            AdminNotification adminNotification = new AdminNotification();
            adminNotification.start(new Stage());
        });

        Text optionsText = new Text("Meeting, Exam, Others");
        optionsText.setFill(Color.GRAY);
        optionsText.setStyle("-fx-font-size: 12;");
        optionsText.setVisible(false);


        Button checkButton = new Button("Check");
        checkButton.setStyle("-fx-background-color: #228B22; -fx-text-fill: white;");
        checkButton.setOnAction(event -> {
            if (TeacherId == null || TeacherId.isEmpty()) {
                System.out.println("hello");
                CheckButtonMessage.setText("enter then search");
                CheckButtonMessage.setFill(Color.RED);
            } else {
                if (ToCheckIfFileIsNullThatID(TeacherId)) {
                    /*
                    this function is for when
                    suppose this will enter this loop only when that character has array size more than 15,
                    but while running this loop suppose if  a middle details of a teacher is not fulled
                    then will raise a error array index outofbound
                    to avoid this exception again, so only this code, will return the String
                     */
                    String val = finalFunctBcOfMiddleLineError(TeacherId);
                    String[] str = val.split(",");
                    System.out.println(val);
                    if (str[1].equals(TeacherId)) {
                        CheckButtonMessage.setText("Now you can fetch " + str[0] + " Details.");
                        CheckButtonMessage.setFill(Color.BLACK);
                        infoOfTeachers = val;
                        a = false;
                        System.out.println(infoOfTeachers);
                        b = true;
                        ForNotificationID = str[1];
                        timetableButton.setVisible(true);
                        notificationButton.setVisible(true);
                        personalInfoButton.setVisible(true);
                        emailButton.setVisible(true);
                        optionsText.setVisible(true);
                        messageText.setVisible(true);
                    }

                }
                    else {
                    timetableButton.setVisible(false);
                    notificationButton.setVisible(false);
                    personalInfoButton.setVisible(false);
                    emailButton.setVisible(false);
                    optionsText.setVisible(false);
                    messageText.setVisible(false);
                    if (thisBooleanForWrongID) {
                        CheckButtonMessage.setText("Entered College-Id /  Email is not Correct...");
                        CheckButtonMessage.setFill(Color.RED);
                    } else {
                        CheckButtonMessage.setText("U cannot fetch ID:" + TeacherId + "\ndetails b/c has not finished\nverification process yet...");
                        CheckButtonMessage.setFill(Color.RED);
                    }
                }
            }
        });


        VBox rightContent = new VBox(20, heading, new VBox(10, emailTextField, new HBox(10, checkButton, CheckButtonMessage)),
                new HBox(10, timetableButton, emailButton, personalInfoButton),
                new HBox(10, notificationButton),
                optionsText,
                messageText);
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

    public static String timeStruct(String value) {
        String[] str = value.split(",");
        String temp = "";
        for (int i = 1; i < str.length; i++) {
            temp = temp.concat(str[i]).concat(" ||");
        }
        return temp;
    }

    public static String TeacherDetails(String info) {
        String[] dict = {"Full Name:", "Gender:", "D.O.B:", "Contact:", "Address:", "Qualification:", "College ID:", "Campus:", "Subject:", "Department:", "Graduate:"};
        String[] arr = info.split(",");
        String temp1 = "", temp2 = "", temp3 = "", temp4 = "";
        for (int i = 5; i < 15; i++) {
            //suppose if again error comes it can be due to 15 --> replace it with arr.length
            if (i < 8) {
                temp1 = temp1.concat(dict[i - 5]).concat(arr[i]).concat(" || ");
            } else if (i < 11) {
                temp2 = temp2.concat(dict[i - 5]).concat(arr[i]).concat(" || ");
            } else if (i < 14) {
                temp3 = temp3.concat(dict[i - 5]).concat(arr[i]).concat(" || ");
            } else {
                temp4 = temp4.concat(dict[i - 5]).concat(arr[i]).concat(" || ");
            }
        }
        return temp1.concat("\n").concat(temp2).concat("\n").concat(temp3).concat("\n").concat(temp4);
    }

    @Override
    public StackPane createMiddleSectionStack() throws IOException {
        return null;
    }

    //this function is to check whether, suppose, if a user didnot fill the details,
    // so before it will raise a error stating that array is ArrayIndexOutOfBoundsException b/c of
    // we use array split function, so suppose if i didnot gone through the sign-up page,
    // then that splitted array will be of length three only, b/c all others will be of length 15, and this will be of three, only b/c didnot filled
    public static boolean ToCheckIfFileIsNullThatID(String teacherIdrmailID) {
        File file = new File("user_data.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()) {
            String temp = scanner.nextLine();
            String[] splitArr = temp.split(",");
            if (teacherIdrmailID.equals(splitArr[1])) {
                if (splitArr.length < 4) {
                    thisBooleanForWrongID = false;
                    return false;
                }
                return true;
            }
        }
        thisBooleanForWrongID = true;
        return false;
    }

    /*
any teacher first they will have go through the verification, proccess then only will go the sign-in page,
so basically if a teacher has not gone through the verification, then the teacher will
not have gone to signin page, so
if the details are not filled, then will not go only first of alll,
but if the teachers have filled, the sign-up process, but have not filled the timetable, the that is why the function upcoming now will be
 */
    public static boolean VerificationFinButTimeTableNO(String filename) {
        System.out.println(filename);
        File file = new File(filename.concat(".txt"));
        try {
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found sorryy!!!!!");
            return false;
        }
        return true;
    }

    public static String finalFunctBcOfMiddleLineError(String teacherId) {
        try {
            File file = new File("user_data.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String val = scanner.nextLine();
                String[] str = val.split(",");
                if (str[1].equals(TeacherId)) {
                    return val;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("due to a teacher who has not filled details before the present teacher");
        } catch (IOException e) {
            System.out.println("file not found");
            e.printStackTrace();
        }
        return "";
    }
}
// one issue not debuuged, that is nothinh but suppose if a teacher has not filled a certain detail,
// that is veification -->sign up --> personal-info, suppose in this if the teacher have not finished, will result, in a exception
// so i will keep  that place like the teacher can't escape that process.

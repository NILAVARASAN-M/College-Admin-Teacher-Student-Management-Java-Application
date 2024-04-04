package com.example.demo1;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TeacherNotification extends TeacherStorage {

    public static ArrayList<VBox> sectionList; //this is for the 8-vbox'x
    public static ArrayList<Button> AcceptButtonList; //this is for the accept button's list.
    public static ArrayList<Button> RejectButtonList; //this is for the reject button's list.
    public static ArrayList<Text> textList; //this is for showing the message, after accepting or rejecting
    public static ArrayList<Button> ConfirmButtonList;
    public static ArrayList<String> message;
    public static String SignTeacherId;
    static int num;
    static String str;
    public static ArrayList<String> myFreeperiod;
    //************** this below string is for storing the collegeID
    public static String currentButtonID="";
    public static ArrayList<String> allIdList=new ArrayList<>();
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.getIcons().add(image);
        ClassNameForPPT= this.getClass().getName();
        SignTeacherId = "";
        num = 0;
        str = "";
        sectionList = new ArrayList<>();
        message = new ArrayList<>();
        AcceptButtonList =new ArrayList<>();
        textList=new ArrayList<>();
        RejectButtonList=new ArrayList<>();
        ConfirmButtonList=new ArrayList<>();
        ClassNameForPPT= this.getClass().getName();
        BorderPane root = new BorderPane();

        root.setTop(super.createHeader("Notification"));

        root.setCenter(createMiddlePartSplit());

        root.setBottom(super.createFooter());

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle(faviconTitle);
        primaryStage.show();
    }

    public SplitPane createMiddlePartSplit() throws IOException {
        //FreePeriodMessageGenerator();

        SplitPane topSection = createSplitSection("Top Section", "#E6D6C3", "#C3D6E6", "#E6E6C3", "#C3C3E6");
        SplitPane bottomSection = createSplitSection("Bottom Section", "#D6E6C3", "#E6C3D6", "#C3E6E6", "#E6C3C3");
        VBox.setVgrow(topSection, Priority.ALWAYS);
        VBox.setVgrow(bottomSection, Priority.ALWAYS);

        ForFileName();
        int j = 0;
        while (OwnPassChecker()) {
            allIdList.add("");
            VBox section = sectionList.get(j);
            Text text = new Text(str);
            text.setStyle("-fx-font-size: 14;");
            Button ok = new Button("Ok");
            ok.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
            Text smallMessage = new Text("Click Ok-Button to notify other teachers..");
            smallMessage.setStyle("-fx-font-size: 12; -fx-font-style: italic; -fx-font-weight: 500; -fx-fill: grey;");
            section.getChildren().addAll(text, ok, smallMessage);
            j++;
        }
        breakPeriodFinder();
        for(String value:myFreeperiod){
            VBox section = sectionList.get(j);
            Text ButtonMessage = new Text("");
            ButtonMessage.setStyle("-fx-font-size: 12; -fx-font-family: 'Arial'; -fx-font-weight: bold; ");
            textList.add(ButtonMessage);

            String[] message=value.split(",");
            String str1="****FREE PERIOD AVAILABLE****\n\n".concat("DAY:     ").concat(message[1]).concat("\n");
            String str2 ="SLOT:   ".concat(message[2]).concat("\nDATE:   ").concat(message[3]).concat("\n");
            String temp=str1.concat(str2);
            allIdList.add(message[0]);

            Text text = new Text(temp);
            text.setStyle("-fx-font-size: 14; -fx-font-family: 'Arial'; -fx-fill: #333333;");

            Button accept = new Button("ACCEPT");
            accept.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 8px 16px; -fx-border-radius: 5px; -fx-border-color: black; -fx-border-width: 2px; -fx-cursor: hand;");
            AcceptButtonList.add(accept);

            Button reject = new Button("REJECT");
            reject.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 8px 16px; -fx-border-radius: 5px; -fx-border-color: white; -fx-border-width: 2px; -fx-cursor: hand;");
            RejectButtonList.add(reject);

            Button confirm=new Button("confirm");
            confirm.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
            confirm.setVisible(false);
            ConfirmButtonList.add(confirm);

            HBox buttonBox = new HBox(10, accept, reject);
            buttonBox.setAlignment(Pos.CENTER);
            section.getChildren().addAll(text,buttonBox,ButtonMessage,confirm);
            section.setAlignment(Pos.CENTER);
            j++;
        }

        //now all left is modifying this part so that this period will be reserved for this teacher, and also removing this file from the that txfile after

        //now after adding the confirm button this period should get assigned to this teacher or this should get rejcted.
        //if the teacher accept's the pass then that line should be deleted from the file.

        //another arraylist for the text and another for the string's in the file.

        for(int i=0;i<AcceptButtonList.size();i++) {
            final int index = i;
            AcceptButtonList.get(index).setOnAction(event -> {
                System.out.println("ACCEPT button clicked!");
                textList.get(index).setText("period is reserved for you");
                textList.get(index).setFill(Color.GREEN);
                ConfirmButtonList.get(index).setVisible(true);

                ConfirmButtonList.get(index).setOnAction(e->{
                    System.out.println("CONFIRM button clicked!");
                    textList.get(index).setText("period is reserved for you\n will let know the students....");
                    textList.get(index).setFill(Color.GREEN);
                });
            });
            RejectButtonList.get(index).setOnAction(event -> {
                System.out.println("REJECT button clicked!");
                textList.get(index).setText("free period is cancelled");
                textList.get(index).setFill(Color.RED);
                ConfirmButtonList.get(index).setVisible(true);
                ConfirmButtonList.get(index).setOnAction(e->{
                    System.out.println("CONFIRM button clicked!");
                    textList.get(index).setText("this free period is rejected as\n per you request...");
                    textList.get(index).setFill(Color.RED);
                });
            });
        }


//        buttonList.get(0).setOnAction(event -> {
//            System.out.println("ACCEPT button clicked!");
//            System.out.println("1st button is clicked");
//        });
//
//        buttonList.get(1).setOnAction(event -> {
//            System.out.println("ACCEPT button clicked!");
//            System.out.println("2nd button is clicked");
//        });

//        accept.setOnAction(event -> {
//            System.out.println("ACCEPT button clicked!");
//            ButtonMessage.setText("period is reserved for you");
//            ButtonMessage.setFill(Color.GREEN);
//        });
//        reject.setOnAction(event -> {
//            System.out.println("REJECT button clicked!");
//            ButtonMessage.setText("period is cancelled for you");
//            ButtonMessage.setFill(Color.RED);
//        });




//        VBox section1 = sectionList.get(0);
//        Button button1 = new Button("Button 1");
//        section1.getChildren().add(button1);
//
//// Accessing the second section (index 1) and adding two buttons
//        VBox section2 = sectionList.get(1);
//        Button button2 = new Button("Button 2");
//        Button button3 = new Button("Button 3");
//        section2.getChildren().addAll(button2, button3);
//
//// Accessing the third section (index 2) and adding eight buttons
//        VBox section3 = sectionList.get(2);
//        for (int i = 1; i <= 8; i++) {
//            Button button = new Button("Button " + i);
//            section3.getChildren().add(button);
//        }
        // settingMessages();
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(topSection, bottomSection);
        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.setDividerPositions(0.5);

        return splitPane;
    }


    private SplitPane createSplitSection(String title, String color1, String color2, String color3, String color4) {
        SplitPane splitPane = new SplitPane();
        VBox topLeftSection = createSection("Message-".concat(Integer.toString(1 + num)), color1, Color.BLACK, FontWeight.BOLD, FontPosture.REGULAR);
        VBox topRightSection = createSection("Message-".concat(Integer.toString(2 + num)), color2, Color.BLACK, FontWeight.BOLD, FontPosture.REGULAR);
        VBox bottomLeftSection = createSection("Message-".concat(Integer.toString(3 + num)), color3, Color.BLACK, FontWeight.BOLD, FontPosture.REGULAR);
        VBox bottomRightSection = createSection("Message-".concat(Integer.toString(4 + num)), color4, Color.BLACK, FontWeight.BOLD, FontPosture.REGULAR);
        num = num + 4;
        sectionList.add(topLeftSection);
        sectionList.add(topRightSection);
        sectionList.add(bottomLeftSection);
        sectionList.add(bottomRightSection);

        splitPane.getItems().addAll(topLeftSection, topRightSection, bottomLeftSection, bottomRightSection);
        splitPane.setDividerPositions(0.25, 0.5, 0.75);

        return splitPane;
    }

    private VBox createSection(String title, String color, Color fontColor, FontWeight fontWeight, FontPosture posture) {
        VBox section = new VBox();
        section.setStyle("-fx-background-color: " + color + ";");

        Text heading = new Text(title);
        heading.setFont(Font.font("Arial", fontWeight, FontPosture.ITALIC, 18));
        heading.setFill(fontColor);

        section.getChildren().addAll(heading);
        section.setAlignment(Pos.CENTER);
        section.setSpacing(10);
        section.setPadding(new Insets(10));

        return section;
    }

    public static void main(String[] args) {
        launch(args);
        System.out.println(sectionList.size());
    }

    public static void breakPeriodFinder() throws IOException {
        File file = new File("ForTeachersLeave.txt");
        Scanner scanner = new Scanner(file);
        ArrayList<String> listOfOtherTeacherleave = new ArrayList<>();
        // this list will contain all the other teachers free period, list of that., except the present teachers time-table who have logged-in.
        myFreeperiod = new ArrayList<>();
        // this list contains the present teacher who signed-in that teachers, free periods.
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            String[] val = str.split(",");
            if (!val[0].equals(SignTeacherId)) {
                listOfOtherTeacherleave.add(str);
            }
        }
        for (String str : listOfOtherTeacherleave) {
            String[] val = str.split(",");
            //val1 is day, val2 is slot.
            if (myselfTeacherBreak(val[1],val[2])) {
                myFreeperiod.add(str);
            }
        }
    }
    // this function is for the present teacher who have signed in that teachers, to check whether break period is available.
    public static boolean myselfTeacherBreak(String day, String slot) throws IOException {
        File file = new File(SignTeacherId.concat(".txt"));
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            String[] val = str.split(",");
            if (val[0].equals(day)) {
                int num=0;
                System.out.println(slot);
                num = switch (slot.trim()) {
                    case "9:00 AM-10:30 AM" -> 1;
                    case "10:40 AM-12:20 PM" -> 2;
                    case "12:20 PM-1:10 PM" -> 3;
                    case "2:30 PM-4:10 PM" -> 4;
                    default -> 0;
                };
                if(val[num].equals("Break")){
                    System.out.println(val[num]);
                    return true;
                }
            }
        }
        return false;
    }

    //this function is for the self use suppose if the pass apllied by the user is cancel or approved anything, then that will be displayed.
    public static boolean OwnPassChecker() throws IOException{
        File file=new File("LeaveRequest.txt");
        int num=0;
        Scanner scanner=new Scanner(file);
        boolean a=false;
        String[] value={};
        while (scanner.hasNextLine()){
            String[] SepVal=scanner.nextLine().split(",");
            if(SignTeacherId.equals(SepVal[0])){
                value=SepVal;
                break;
            }
            num++;
        }
        str="";
        if(value.length!=0){
            String str1="****YOUR PASS APPLIED ON****\n\n".concat("DATE:   ").concat(value[4]);
            String str2="\nDAY:    ".concat(value[2]).concat("\nSLOT:   ").concat(value[3]).concat("\nHas been ");
            String str3=value[1].toUpperCase().concat(" by the admin.");
            str=str1.concat(str2).concat(str3);
            a=true;
            //now if the message is found then that message shoud be deleted so only this block of code.
            adminMessageDelete(num);
        }
        scanner.close();

        return a;
    }

    public static void adminMessageDelete(int num) throws IOException{
        ArrayList<String> list=new ArrayList<>();
        File file=new File("LeaveRequest.txt");
        Scanner scanner=new Scanner(file);
        int i=0;
        while (scanner.hasNextLine()){
            String val=scanner.nextLine();
            if(i!=num){
                list.add(val);
            }
            i++;
        }
        FileWriter fileWriter=new FileWriter("LeaveRequest.txt");
        for(String str:list){
            fileWriter.write(str.concat("\n"));
        }
        fileWriter.close();
        scanner.close();
    }

    public static void ForFileName() throws IOException{
        File file=new File("user_data.txt");
        Scanner scanner=new Scanner(file);
        while(scanner.hasNextLine()){
            String[] comaSplit=scanner.nextLine().split(",");
            boolean a=Step5.FurtherName.equals(comaSplit[0]) || Step5.FurtherName.equals(comaSplit[1]) || Step5.FurtherName.equals(comaSplit[3]);
            if(a && Step5.FurtherPassword.equals(comaSplit[4])){
                SignTeacherId=comaSplit[1]; //because this will be college id-and will be unique.
                break;
            }
        }
    }
    @Override
    public StackPane createMiddleSectionStack(){return null;}
}



// *********************** kindly remember that ForTeachersLeave.txt file is where we are going to use for assigning teachers to the break periods, it has the
//pass which were accepetd by the warden, only the accepted pass's


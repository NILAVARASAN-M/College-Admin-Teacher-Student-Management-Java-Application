package com.example.demo1;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminAllTeachersInfo extends AdminStotage{

    private TableView<String[]> tableView;
    public static int rowCount=0;
    public static int TotalRowCount=0;

    @Override
    public void start(Stage primaryStage) throws IOException{
        ClassNameForPPT= this.getClass().getName();
        primaryStage.getIcons().add(image);
        rowCount=0;
        try {
            TotalRowCount=NoOfLinesInFile();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        primaryStage.setTitle(faviconTitle);

        BorderPane root = new BorderPane();

        root.setTop(super.createHeader("All Teachers Info"));
        root.setCenter(createContent());
        root.setBottom(super.createFooter());

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ScrollPane createContent() throws IOException{
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Create a TableView
        tableView = new TableView<>();

        String[] info_dat={"Name","College ID","Password1","Mail ID","Password2","Name","Gender","D.O.B","Phone No","Address","Qualification","ID","Campus","Subject","Department","Graduate","Extra","Extra","Extra","Extra"};
        // Create columns with custom headings
        for (int i = 1; i <= 20; i++) {
            TableColumn<String[], String> column = new TableColumn<>(info_dat[i-1]);
            column.setText(info_dat[i-1]);
            final int columnIndex = i - 1;
            column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[columnIndex]));
            column.setCellFactory(TextFieldTableCell.forTableColumn());
            tableView.getColumns().add(column);
        }

        // Add data to the TableView
        for(int i=1;i<=TotalRowCount;i++){
            ArrayList<String> temp=rowOfTable();
            String[] arr=new String[temp.size()];
            for(int j=0;j<temp.size();j++){
                arr[j]=temp.get(j);
            }
            tableView.getItems().addAll(arr);
            rowCount++;
            System.out.println(rowCount);
        }


//        for (int i = 1; i <= 20; i++) {
//            String[] row = new String[20];
//            for (int j = 0; j < 20; j++) {
//                row[j] = "Row " + i + ", Column " + (j + 1);
//            }
//            tableView.getItems().add(row);
//        }

        // Enable cell editing
        tableView.setEditable(true);

        // Customize the TableView appearance
        tableView.setStyle("-fx-background-color: skyblue; -fx-padding: 10;");

        // Add the TableView to the ScrollPane
        scrollPane.setContent(tableView);

        return scrollPane;
    }

    public static void main(String[] args) throws IOException{
        launch(args);
    }
    public static ArrayList<String> rowOfTable()throws IOException {
        ArrayList<String> alter=new ArrayList<>();
        String str="";
        File file=new File("user_data.txt");
        Scanner scanner=new Scanner(file);
        int i=0;
        while (i<=rowCount ){
            str=scanner.nextLine();
            i++;
        }
        String[] temp=str.split(",");
        for(int j=0;j<20;j++){
            if(j<temp.length){
                alter.add(temp[j]);
            }
            else {
                alter.add("-");
            }
        }
        return alter;
    }
    public static int NoOfLinesInFile() throws IOException{
        File file=new File("user_data.txt");
        Scanner scanner=new Scanner(file);
        int count=0;
        while (scanner.hasNextLine()){
            count++;
            scanner.nextLine();
        }
        return count;
    }

    //i will noy use this sicnce due to i am using scroolpane
    @Override
    public SplitPane createMiddlePartSplit() throws IOException {
        return null;
    }
    // similary to above //i will noy use this sicnce due to i am using scroolpane
    @Override
    public StackPane createMiddleSectionStack() throws IOException {
        return null;
    }
}
/*
this part of the code is for seeing alll the teachers info in a common page,
so the admin can see through, all the information of teachers, also his/her password,
so the probelem with is that there is no privacy, but anyway the teachers is not going to
send message, so no problem with that............

 this website has zero probelms ........................................

 *******************************************************END**********************************************

 */
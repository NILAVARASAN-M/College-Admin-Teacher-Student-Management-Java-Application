package com.example.demo1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Step1Contact extends TeacherStorage  {
    static String name,nextClass="";
    public static ArrayList<String> list=new ArrayList<>();
    Text verificationMessage = new Text();


    @Override
    public void start(Stage primaryStage) throws IOException{
        primaryStage.getIcons().add(image);
        ClassNameForPPT= this.getClass().getName();
        BorderPane root = new BorderPane();

        root.setTop(super.createHeader("Contact"));
        root.setCenter(createMiddleSectionStack());
        root.setBottom(super.createFooter());

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);

        primaryStage.setTitle(faviconTitle);

        primaryStage.show();
    }

    private HBox createContactCard(String name, String email, String phone, String imagePath) {
        // Create the contact card layout
        HBox contactCard = new HBox(20);
        contactCard.setAlignment(Pos.CENTER);
        contactCard.setStyle("-fx-background-color: white; -fx-border-radius: 10; -fx-padding: 10;");
        contactCard.setEffect(new DropShadow(10, Color.GRAY));

        // Load the contact image
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        // Create the contact details
        VBox details = new VBox(10);
        details.setAlignment(Pos.CENTER_LEFT);

        Text nameLabel = new Text("Name: " + name);
        Text emailLabel = new Text("Email: " + email);
        Text phoneLabel = new Text("Phone: " + phone);

        details.getChildren().addAll(nameLabel, emailLabel, phoneLabel);

        // Add the image and details to the contact card
        contactCard.getChildren().addAll(imageView, details);

        return contactCard;
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
        Text title = new Text("Contact Us");
        title.setFill(Color.BLACK);
        title.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        // Create the contact cards
        VBox contactCards = new VBox(20);
        contactCards.setAlignment(Pos.CENTER);
        contactCards.setPadding(new Insets(20));
        contactCards.setStyle("-fx-background-color: #F5F5F5; -fx-border-radius: 10;");
        String[] ImgUrls={
                "A:\\github_repos\\sem_2_JAVA_project\\for github\\codes\\images\\messi-1.jpg",
                "A:\\github_repos\\sem_2_JAVA_project\\for github\\codes\\images\\ronaldo-1.jpeg",
                "A:\\github_repos\\sem_2_JAVA_project\\for github\\codes\\images\\vijay-1.png",
        };

        // Create individual contact cards
        HBox contact1 = createContactCard("Messi", "Messi@example.com", "123-456-7890", ImgUrls[0]);
        HBox contact2 = createContactCard("Ronaldo", "Ronaldo@example.com", "987-654-3210", ImgUrls[1]);
        HBox contact3 = createContactCard("Thalapathy Vijay", "Vijay@example.com", "555-555-5555", ImgUrls[2]);

        // Add the contact cards to the VBox
        contactCards.getChildren().addAll(contact1, contact2, contact3);

        // Create a button for submitting inquiries
        Button submitButton = new Button("Submit Inquiry");
        submitButton.setStyle("-fx-background-color: #961300; -fx-text-fill: white;");
        submitButton.setOnAction(event -> {
            System.out.println("Inquiry submitted!");
        });

        // Create a VBox to hold the title, contact cards, and submit button
        VBox contactsSection = new VBox(20, title, contactCards, submitButton);
        contactsSection.setAlignment(Pos.CENTER);
        contactsSection.setPadding(new Insets(20));

        return new StackPane(contactsSection);
    }
}

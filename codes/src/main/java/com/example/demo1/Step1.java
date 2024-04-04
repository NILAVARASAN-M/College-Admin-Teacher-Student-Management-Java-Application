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

import java.io.IOException;

public class Step1 extends TeacherStorage{

    private int currentImageIndex = 0;
    @Override
    public void start(Stage primaryStage) {
        ClassNameForPPT= this.getClass().getName();
        // the above one can be
        // Set the application icon (favicon)
        primaryStage.getIcons().add(image);
        // Create a border pane as the root
        BorderPane root = new BorderPane();
        // Create the header with college name, buttons, and search bar
        root.setTop(super.createHeader("Home"));
        root.setCenter(createMiddlePartSplit());
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
    public SplitPane createMiddlePartSplit(){
        SplitPane content = new SplitPane();

        // Create the left half of the content with an image and arrow buttons
        VBox leftContent = new VBox();
        leftContent.setAlignment(Pos.CENTER);
        leftContent.setPadding(new Insets(10));
        leftContent.setStyle("-fx-background-color: #F5F5F5;");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(400);
        imageView.setFitHeight(400);

        // Create the arrow buttons
        Button prevButton = new Button("<");
        Button nextButton = new Button(">");

        // Set the initial image

        imageView.setImage(new Image(imageUrls[this.currentImageIndex]));

        // Add event handlers for the arrow buttons
        prevButton.setOnAction(event -> {
            this.currentImageIndex--;
            if (this.currentImageIndex < 0) {
                this.currentImageIndex = imageUrls.length - 1;
            }
            imageView.setImage(new Image(imageUrls[this.currentImageIndex]));
        });

        nextButton.setOnAction(event -> {
            this.currentImageIndex++;
            if (this.currentImageIndex >= imageUrls.length) {
                this.currentImageIndex = 0;
            }
            imageView.setImage(new Image(imageUrls[this.currentImageIndex]));
        });

        // Add the arrow buttons and image to the left content
        HBox arrowButtons = new HBox(10, prevButton, nextButton);
        arrowButtons.setAlignment(Pos.CENTER);
        leftContent.getChildren().addAll(arrowButtons, imageView);

        // Create the right half of the content with a random quote
        VBox rightContent = new VBox();
        rightContent.setAlignment(Pos.CENTER);
        rightContent.setPadding(new Insets(10));
        rightContent.setStyle("-fx-background-color: #F5F5F5;");

        Text quoteText = new Text("My Religion is LOVE.");
        quoteText.setFill(Color.BLACK);
        quoteText.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        Text authorText = new Text("-Amma (mata amritanandamayi devi)");
        authorText.setFill(Color.BLACK);
        authorText.setStyle("-fx-font-size: 18;");

        rightContent.getChildren().addAll(quoteText, authorText);

        // Set the left and right halves of the content in the split pane
        content.getItems().addAll(leftContent, rightContent);
        content.setDividerPositions(0.5);
        return content;
    }
    // this is not used here and also just due to interface
    @Override
    public StackPane createMiddleSectionStack() throws IOException {
        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}





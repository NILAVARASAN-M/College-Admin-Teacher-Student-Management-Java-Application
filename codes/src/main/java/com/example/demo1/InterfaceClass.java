package com.example.demo1;

import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;

interface InterfaceClass {
    Image image=new Image("A:\\github_repos\\sem_2_JAVA_project\\for github\\codes\\images\\amrita-favicon-image.jpg");
    ImageView photo1Logo = new ImageView(new Image("A:\\github_repos\\sem_2_JAVA_project\\for github\\codes\\images\\amrita-main-logo2.png"));
    String[] imageUrls = {
            "A:\\github_repos\\sem_2_JAVA_project\\for github\\codes\\images\\college-photo-1.jpg",
            "A:\\github_repos\\sem_2_JAVA_project\\for github\\codes\\images\\college-photo-2.jpg",
            "A:\\github_repos\\sem_2_JAVA_project\\for github\\codes\\images\\college-photo-3.jpeg",
            "A:\\github_repos\\sem_2_JAVA_project\\for github\\codes\\images\\college-photo-4.jpeg",
            "A:\\github_repos\\sem_2_JAVA_project\\for github\\codes\\images\\amrita-main-logo2.png",
    };
    String faviconTitle="Amrita University Teacher's-Management";
    HBox createHeader(String header_Title) throws IOException;
    StackPane createFooter();
    SplitPane createMiddlePartSplit() throws IOException;
    StackPane createMiddleSectionStack() throws IOException;
}





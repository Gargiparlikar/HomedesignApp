 package com.homedesignhub.controllers;
   
 import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

public class AboutUsPage extends StackPane {

    public AboutUsPage() {
        // Set background with gradient overlay
        setStyle("-fx-background-image: url('https://img.freepik.com/free-photo/still-life-arrangement-friendship-day_23-2148970498.jpg?ga=GA1.1.1898718417.1719894709&semt=ais_user'); "
               + "-fx-background-size: cover; -fx-background-repeat: no-repeat;");
       // Rectangle gradientOverlay = new Rectangle(1000, 700);
        //gradientOverlay.setFill(new LinearGradient(0, 0, 1, 1, true, null,
//new Stop(0, Color.rgb(0, 0, 0, 0.4)), new Stop(1, Color.rgb(0, 0, 0, 0.7))));
       // getChildren().add(gradientOverlay);

        // Add fade transition to gradient overlay
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3));
        fadeTransition.setFromValue(0.4);
        fadeTransition.setToValue(0.7);
        fadeTransition.setCycleCount(FadeTransition.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();

        // Transparent rectangle background
        Rectangle rect = new Rectangle(900, 600);
        rect.setFill(Color.rgb(255, 255, 255, 0.8)); // Semi-transparent white
        rect.setArcWidth(30); // Rounded corners
        rect.setArcHeight(30);
        rect.setStroke(Color.WHITE); // White border
        rect.setStrokeWidth(3);
        rect.setEffect(new javafx.scene.effect.DropShadow(10, Color.BLACK)); // Shadow effect
        StackPane.setAlignment(rect, Pos.CENTER_LEFT); // Align the rectangle to the left
        StackPane.setMargin(rect, new javafx.geometry.Insets(0, 0, 0, 50)); // Add left margin
        getChildren().add(rect);

        // Add About Us title
        Label titleLabel = new Label("About Home Design Hub");
        titleLabel.setPrefHeight(100);
        titleLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 50));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-effect: dropshadow(one-pass-box, black, 8, 0.3, 4, 4);"); // Text shadow
        StackPane.setAlignment(titleLabel, Pos.TOP_LEFT); // Align title to the top left
        StackPane.setMargin(titleLabel, new javafx.geometry.Insets(30, 0, 0, 80)); // Add left margin
        getChildren().add(titleLabel);

        // Create bullet points with icons
        Text bullet1 = new Text("\u2022 Welcome to the Home Design Hub, where creativity meets functionality in the world of interior design.\n");
        Text bullet2 = new Text("\u2022 Our team, consisting of Shivkanya Khedkar, Gargi Parlikar, and Bhakti Shirsat, guided by Sachin Sir, is passionate about leveraging Java, JavaFX, and Firebase to bring your home renovation visions to life.\n");
        Text bullet3 = new Text("\u2022 Under the expert guidance of Shashi Sir, we aim to simplify and enhance your interior design journey through intuitive technology and personalized solutions.\n");
        Text bullet4 = new Text("\u2022 Concepts Used : Inheritance,Encapsulation,Polymorphism,Exception,FireStore,JavaFx concepts ");
 

        bullet1.setFont(Font.font("Arial", 25));
        bullet2.setFont(Font.font("Arial", 25));
        bullet3.setFont(Font.font("Arial", 25));
        bullet4.setFont(Font.font("Arial", 25));

        // Style bullets
        bullet1.setFill(Color.DARKBLUE);
        bullet2.setFill(Color.DARKBLUE);
        bullet3.setFill(Color.DARKBLUE);
        bullet4.setFill(Color.DARKBLUE);

        TextFlow textFlow = new TextFlow(bullet1, bullet2, bullet3,bullet4);
        textFlow.setMaxWidth(900);
        textFlow.setLineSpacing(25);
        textFlow.setPadding(new javafx.geometry.Insets(50, 30, 30, 30)); // Add padding to the text flow
        StackPane.setAlignment(textFlow, Pos.TOP_LEFT); // Align text flow to the top left
        StackPane.setMargin(textFlow, new javafx.geometry.Insets(150, 0, 0, 80)); // Add left margin
        getChildren().add(textFlow);

        // Create a back button with custom styling and hover effect
        Button backButton = new Button("Back");
        backButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        backButton.setStyle("-fx-text-fill: white; -fx-background-color: #3b5998; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: white; -fx-border-width: 2;");
        backButton.setPrefSize(120, 50);
        backButton.setEffect(new javafx.scene.effect.DropShadow(5, Color.BLACK)); // Button shadow

        // Add hover effect
        backButton.setOnMouseEntered(e -> {
            backButton.setScaleX(1.1);
            backButton.setScaleY(1.1);
            backButton.setStyle("-fx-text-fill: white; -fx-background-color: #5b79d8; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: white; -fx-border-width: 2;");
        });
        backButton.setOnMouseExited(e -> {
            backButton.setScaleX(1.0);
            backButton.setScaleY(1.0);
            backButton.setStyle("-fx-text-fill: white; -fx-background-color: #3b5998; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: white; -fx-border-width: 2;");
        });

        backButton.setOnAction(e -> {
            HomeRenovationApp.navigateToRoomCategoryPage();
        });
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT); // Align button to the bottom left
        StackPane.setMargin(backButton, new javafx.geometry.Insets(0, 0, 50, 80)); // Add left margin
        getChildren().add(backButton);
    }
}

package com.homedesignhub.controllers;

import java.util.List;
import java.util.Map;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.homedesignhub.Sevices.firebaseconfig;;
public class RoomCategorySelection extends VBox {
    private static templatePage TP; 
    private firebaseconfig fb;
    private Stage primaryStage;
    private Scene aboutUsScene;

    public RoomCategorySelection(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Set the primary stage dimensions
        primaryStage.setWidth(1800);
        primaryStage.setHeight(1000);

        // Set background image
        Image backgroundImage = new Image("https://i.pinimg.com/564x/02/d9/66/02d96633505aa858d80f9e5f03553881.jpg");
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true));
        setBackground(new Background(background));

        // Create and style the label
        Label label = new Label(" Room Category  ");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        label.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10px;-fx-background-radius: 20;");

        // Create a GridPane for room images and their buttons
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(40);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(40));

        // Add room images with clickable buttons
        addRoomButtons(gridPane);

        // Create a button with a bag icon for the cart
        Button viewCartButton = createCartButton();

        // Create About Us button
        Button aboutUsButton = new Button("About Us");
        aboutUsButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        aboutUsButton.setStyle("-fx-text-fill: black; -fx-background-color: white; -fx-background-radius: 10;-fx-font-weight: bold;");
        aboutUsButton.setPrefSize(200, 50);
        aboutUsButton.setOnAction(event -> navigateToAboutUsPage());
        aboutUsButton.setOnMouseClicked(event -> playButtonAnimation(aboutUsButton));

        // Button backButton = new Button("Back");
        // backButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        // backButton.setStyle("-fx-text-fill: black; -fx-background-color: white; -fx-background-radius: 10;-fx-font-weight: bold;");
        // backButton.setPrefSize(200, 40);
        // backButton.setOnAction(e -> {
        //     HomeRenovationApp.navigateToLoginPage();
        //     this.getChildren().clear();
        // });
    

        // Add components to the AnchorPane
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(label, gridPane, viewCartButton, aboutUsButton);

        // Set AnchorPane constraints for the components
        AnchorPane.setTopAnchor(label, 40.0);
        AnchorPane.setLeftAnchor(label, 700.0);
        AnchorPane.setTopAnchor(gridPane, 100.0);
        AnchorPane.setLeftAnchor(gridPane, 110.0);
        AnchorPane.setTopAnchor(viewCartButton, 30.0);
        AnchorPane.setRightAnchor(viewCartButton, 20.0);
        AnchorPane.setTopAnchor(aboutUsButton, 50.0);
        AnchorPane.setRightAnchor(aboutUsButton, 120.0);  
        // AnchorPane.setTopAnchor(backButton, 50.0);
        // AnchorPane.setLeftAnchor(backButton, 120.0);  

        // Add the AnchorPane to the VBox
        this.getChildren().add(anchorPane);
    }

    private void playButtonAnimation(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(2);
        scaleTransition.play();
    }

    private Button createAnimatedButton(String text, ImageView imageView) {
        Button button = new Button(text, imageView);
        button.setContentDisplay(javafx.scene.control.ContentDisplay.TOP);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        button.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, new CornerRadii(20), Insets.EMPTY)));

        // Mouse enter event for scaling and background color change
        button.setOnMouseEntered(event -> {
            button.setStyle("-fx-background-color: pink;");
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.play();
        });

        // Mouse exit event for scaling and background color change
        button.setOnMouseExited(event -> {
            button.setStyle("-fx-background-color: whitesmoke;");
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.play();
        });

        // Mouse click event (currently does nothing additional)
        button.setOnMouseClicked(event -> {
            // You can add any specific actions for the click event here
        });

        return button;
    }

    private void navigateToAboutUsPage() {
        AboutUsPage aboutUsPage = new AboutUsPage();
        aboutUsScene = new Scene(aboutUsPage, 800, 600);
        primaryStage.setScene(aboutUsScene);
    }

    private void addRoomButtons(GridPane gridPane) {
        // Create room buttons with images
        gridPane.add(createRoomButton("Living Room", "/images/livingRoom.jpg", "Living Room", "FurnitureLiving"), 0, 0);
        gridPane.add(createRoomButton("Kitchen", "/images/kitchen.jpg", "Kitchen", "FurnitureKitchen"), 1, 0);
        gridPane.add(createRoomButton("Bed Room", "/images/bedroom2.jpg", "BedRoom", "FurnitureBedroom"), 0, 1);
        gridPane.add(createRoomButton("Bath Room", "/images/bath.jpg", "BathRoom", "FurnitureBathroom"), 2, 0);
        gridPane.add(createRoomButton("Kids Room", "/images/kids.jpg", "ChildRoom", "FurnitureKidsroom"), 1, 1);
        gridPane.add(createRoomButton("Balcony", "/images/balcony.jpg", "Balcony", "FurnitureBalcony"), 2, 1);
    }

    private Button createRoomButton(String buttonText, String imagePath, String firebaseCollection, String category) {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(410);
        imageView.setFitHeight(350);
        Button button = createAnimatedButton(buttonText, imageView);
        button.setOnAction(event -> fetchTemplates(firebaseCollection, category));
        return button;
    }

    private void fetchTemplates(String firebaseCollection, String category) {
        TP = new templatePage();
        try {
            fb = new firebaseconfig();
            List<Map<String, Object>> templates = fb.getProducts(firebaseCollection);
            for (Map<String, Object> template : templates) {
                String imageUrl = (String) template.get("ImageURL");
                String price = (String) template.get("price");
                String rating = (String) template.get("ProductR");
                String furniture = (String) template.get("Furniture");
                String dimension = (String) template.get("RoomD");
                String style = (String) template.get("Style");

                templatePage.createButton(imageUrl, price, rating, furniture, dimension, style);
            }
            HomeRenovationApp.setSelectedCategory(category);
            HomeRenovationApp.navigateToTemplatePage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    private Button createCartButton() {
        Image bagImage = new Image(getClass().getResourceAsStream("/images/cart.png"));
        ImageView bagImageView = new ImageView(bagImage);
        bagImageView.setFitWidth(70);
        bagImageView.setFitHeight(70);
        Button viewCartButton = new Button("", bagImageView);
        viewCartButton.setBackground(null);  // Remove button background
        viewCartButton.setOnAction(e -> HomeRenovationApp.navigateToCartPage());
        return viewCartButton;
    }

    public static templatePage getView() {
        return TP;
    }
}

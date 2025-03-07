package com.homedesignhub.controllers;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class templatePage extends VBox {
    private static TilePane tilePane = new TilePane();
    private static HomeRenovationApp controller;

    public templatePage() {
        initialize();
    }

    public templatePage(HomeRenovationApp controller) {
        templatePage.controller = controller;
        initialize();
    }

    private void initialize() {
        tilePane.setPadding(new Insets(20, 30, 20, 140));
        tilePane.setHgap(50);
        tilePane.setVgap(30);
        tilePane.setStyle("-fx-background-image: url('https://i.pinimg.com/736x/8c/64/e5/8c64e5c53ba8ed29e54eda8f38b9301e.jpg');");
        

        ScrollPane scrollPane = new ScrollPane(tilePane);
        scrollPane.setFitToWidth(true);

        // Create a back button
        Button backButton = new Button("Back");
        backButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        backButton.setStyle("-fx-text-fill: black; -fx-background-color: white; -fx-background-radius: 10;-fx-font-weight: bold;");
        backButton.setPrefSize(200, 40);
        backButton.setOnAction(e -> {
            HomeRenovationApp.navigateToRoomCategoryPage();
            tilePane.getChildren().clear();
        });

        // Create a button with a bag icon instead of text
        Image bagImage = new Image(getClass().getResourceAsStream("/images/cart.png"));
        ImageView bagImageView = new ImageView(bagImage);
        bagImageView.setFitWidth(70);
        bagImageView.setFitHeight(70);

        Button viewCartButton = new Button("", bagImageView);
        viewCartButton.setBackground(null);  // Remove button background
        viewCartButton.setOnAction(e -> HomeRenovationApp.navigateToCartPage());
        viewCartButton.setPrefSize(200, 40);

        // BorderPane layout to arrange components
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(backButton);
        borderPane.setRight(viewCartButton);
        BorderPane.setMargin(backButton, new Insets(10));
        BorderPane.setMargin(viewCartButton, new Insets(10));

        // Separator between buttons and tilePane
        Separator separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);
        separator.setStyle("-fx-background-color: black;");

        // StackPane for positioning the borderPane above the scrollPane
        StackPane buttonPane = new StackPane();
        buttonPane.getChildren().add(borderPane);

        // Create a VBox layout to arrange components vertically
        VBox layout = new VBox();
        layout.getChildren().addAll(buttonPane, separator, scrollPane);
        layout.setSpacing(10); // Set spacing between components
        layout.setPadding(new Insets(10)); // Set padding around the layout

        // Add the layout to your VBox (assuming templatePage extends VBox)
        this.getChildren().add(layout);

        // Apply fade transition to the entire layout
        applyFadeTransition(layout);
    }

    private void applyFadeTransition(VBox vbox) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000), vbox);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    public static void createButton(String imageurl, String price, String rating, String furniture, String dimension, String style) {
        Image image = new Image(imageurl);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(400);
        imageView.setFitHeight(400);
        imageView.setEffect(new DropShadow(20, Color.BLACK));

        Button productButton = new Button(price);
        productButton.setGraphic(imageView);
        productButton.setContentDisplay(ContentDisplay.TOP);
        productButton.setStyle("-fx-font-size: 23px; -fx-text-fill: BLACK");
        productButton.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, new CornerRadii(20), Insets.EMPTY)));
        productButton.setPrefSize(300, 350);

        Button selectButton = new Button("Select");
        selectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.showFullPageTemplate(imageurl, price, rating, furniture, dimension, style);
            }
        });

        VBox buttonContainer = new VBox(10, selectButton);
        buttonContainer.setAlignment(Pos.CENTER);

        VBox productContainer = new VBox(10, productButton, buttonContainer);
        productContainer.setAlignment(Pos.CENTER);

        tilePane.getChildren().addAll(productContainer);

        // Apply fade transition to each product container
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000), productContainer);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    public static TilePane getTilePane() {
        return tilePane;
    }

    public static void setTilePane(TilePane tilePane) {
        templatePage.tilePane = tilePane;
    }

    public void clearTiles() {
        tilePane.getChildren().clear();
    }
}

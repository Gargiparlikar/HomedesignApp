package com.homedesignhub.controllers;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.ScaleTransition;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import com.homedesignhub.Sevices.firebaseconfig;

public class CartPage extends VBox {
    private List<CartItem> cartItems;
    private Label totalPriceLabel;
    private firebaseconfig fb;

    public CartPage(List<CartItem> list) {
        // Initialize FirebaseService
        fb = new firebaseconfig(); // Ensure Firebase configuration is correctly initialized

        // Set VBox properties
        setPadding(new Insets(20));
        setSpacing(20);
        setAlignment(Pos.TOP_CENTER);

        // Initialize totalPriceLabel
        totalPriceLabel = new Label("Total Price: $0.00");
        getChildren().add(totalPriceLabel);

        // Add a back button to navigate back to the room category page
        Button backButton = new Button("Home");
        backButton.setOnAction(e -> HomeRenovationApp.navigateToRoomCategoryPage());
        getChildren().add(backButton);

        // Load cart items and populate the UI
        loadCartItems();
    }

    private void loadCartItems() {
        // Fetch cart items synchronously
        cartItems = HomeRenovationApp.getCartItems();
        populateCartItems(); // Update UI after fetching items
        updateTotalPrice(); // Update total price after fetching items
    }

    private void populateCartItems() {
        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(20));
        tilePane.setHgap(40);
        tilePane.setVgap(40);
        tilePane.setAlignment(Pos.CENTER);
        tilePane.setStyle("-fx-background-image: url('https://i.pinimg.com/564x/67/ba/08/67ba084e049b5873913c2ba820faf604.jpg');");

        for (CartItem item : cartItems) {
            Image image = new Image(item.getImageUrl());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200); // Adjust size as needed
            imageView.setFitHeight(200); // Adjust size as needed

            VBox itemBox = new VBox(10);
            itemBox.setAlignment(Pos.CENTER);
            itemBox.getChildren().addAll(imageView, new Label("Price: " + item.getPrice()), new Label("Type: " + item.getItemType()));

            // Optionally, add a button to remove item from cart
            Image removeImage = new Image(getClass().getResourceAsStream("/images/remove.png"));
            ImageView removeImageView = new ImageView(removeImage);
            removeImageView.setFitWidth(30);
            removeImageView.setFitHeight(30);
            Button removeButton = new Button("", removeImageView);
            removeButton.setOnAction(e -> {
                HomeRenovationApp.removeFromCart(item);
                tilePane.getChildren().remove(itemBox); // Remove from TilePane
                updateTotalPrice();
            });
            itemBox.getChildren().add(removeButton);

            // Add item to TilePane with animation
            tilePane.getChildren().add(itemBox);
            animateItem(itemBox);
        }

        // Add TilePane to ScrollPane
        ScrollPane scrollPane = new ScrollPane(tilePane);
        scrollPane.setPadding(new Insets(20));
        scrollPane.setFitToWidth(true); // Allow horizontal scrolling if needed
        scrollPane.setFitToHeight(true); // Allow vertical scrolling if needed
        getChildren().add(scrollPane);
    }

    private void animateItem(VBox itemBox) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), itemBox);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), itemBox);
        translateTransition.setFromY(50);
        translateTransition.setToY(0);
        
        SequentialTransition sequentialTransition = new SequentialTransition(fadeIn, translateTransition);
        sequentialTransition.play();
    }

    private void updateTotalPrice() {
        double totalPrice = calculateTotalPrice();
        String formattedPrice = formatCurrency(totalPrice);
        totalPriceLabel.setText("Total Price: " + formattedPrice);
    }

    private double calculateTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems) {
            try {
                total += parsePrice(item.getPrice());
            } catch (ParseException e) {
                System.err.println("Invalid price format: " + item.getPrice());
            }
        }
        return total;
    }

    private double parsePrice(String price) throws ParseException {
        String cleanedPrice = price.replaceAll("[^\\d.]", "");
        return NumberFormat.getNumberInstance(Locale.ENGLISH).parse(cleanedPrice).doubleValue();
    }

    private String formatCurrency(double amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        return formatter.format(amount);
    }
}

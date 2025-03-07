package com.homedesignhub.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.homedesignhub.Sevices.firebaseconfig;
import com.google.cloud.firestore.WriteResult;

public class HomeRenovationApp extends Application {

    private static Scene roomCategorySelectionScene, templatePageScene, loginpagescene, signupScene;

    private static RoomCategorySelection roomCategorySelection;
    private static templatePage templatePage;
    private static Stage primaryStage;
    private static List<CartItem> cartItems = new ArrayList<>();
    private firebaseconfig fb;
    private static DocumentReference dReference;

    private Scene aboutUsScene;
    private static String selectedCategory;
    private static LoginController loginpage;
    private static SignupController signuppage;

    @Override
    public void start(Stage primaryStage) {
        HomeRenovationApp.primaryStage = primaryStage;
        primaryStage.setTitle("Home Design Hub");

        roomCategorySelection = new RoomCategorySelection(primaryStage);
        templatePage = new templatePage(this);
        loginpage = new LoginController(primaryStage, this);
        signuppage = new SignupController(this);
        loginpagescene = loginpage.getLoginScene();
        signupScene = signuppage.createSignupScene(primaryStage);
        roomCategorySelectionScene = new Scene(roomCategorySelection, 1800, 1000);
        templatePageScene = new Scene(templatePage.getTilePane(), 1800, 1000);

        ScrollPane scrollPane = new ScrollPane(templatePage);
        scrollPane.setFitToWidth(true);
        templatePageScene = new Scene(scrollPane, 1800, 1000);

        primaryStage.setScene(loginpagescene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void navigateToLoginPage() {
        primaryStage.setScene(loginpagescene);
    }

    public static void navigateToSignUpPage() {
        primaryStage.setScene(signupScene);
    }

    public static void navigateToRoomCategoryPage() {
        primaryStage.setScene(roomCategorySelectionScene);
    }

    public static void navigateToTemplatePage() {
        primaryStage.setScene(templatePageScene);
    }

    private void navigateToBookingConfirmationPage() {
        BookingConfirmationPage bookingConfirmationPage = new BookingConfirmationPage();
        Scene bookingConfirmationScene = new Scene(bookingConfirmationPage, 1800, 1000);
        HomeRenovationApp.getPrimaryStage().setScene(bookingConfirmationScene);
    }
   

    public static void navigateToCartPage() {
        CartPage cartPage = new CartPage(getCartItems());
        Scene cartPageScene = new Scene(cartPage, 1800, 1000);
        primaryStage.setScene(cartPageScene);
    }


    public static Stage getPrimaryStage() {
        return primaryStage;
    }
   

            


    public void showFullPageTemplate(String imageUrl, String price,String rating,String furniture,String dimension, String style) {
        Image image = new Image(imageUrl);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(1700);
        imageView.setFitHeight(750);
        imageView.setPreserveRatio(true);
        imageView.setEffect(new DropShadow(20, Color.BLACK));
        
        Label l1=new Label("Style : "+style);
        l1.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        l1.setStyle("-fx-background-color: #skyblue; -fx-padding: 10px;");
        Label  l2=new Label("Dimensions : "+dimension);
        l2.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        l2.setStyle("-fx-background-color: #skyblue; -fx-padding: 10px;");
        Label l3=new Label("Furniture : "+ furniture);
        l3.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        l3.setStyle("-fx-background-color: #skyblue; -fx-padding: 10px;");
        Label l4=new Label("Ratings : "+rating);
        l4.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        l4.setStyle("-fx-background-color: #skyblue; -fx-padding: 10px;");
        Label l5=new Label("Price : "+ price);
        l5.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        l5.setStyle("-fx-background-color:  #skyblue; -fx-padding: 10px;");

        VBox infoBox = new VBox(5, l1, l2, l3, l4, l5);
        infoBox.setLayoutX( 800);
        infoBox.setLayoutY(350);
        infoBox.setMargin(infoBox, new Insets( 400,20,400,900));
        infoBox.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 10; -fx-padding: 10;");
        
        
        HBox hBox =new HBox(100, infoBox);
        hBox.setLayoutX(800);
        hBox.setLayoutY(1000);
        hBox.setMargin(infoBox, new Insets( 400,20,400,1100));
        


        StackPane fullPageLayout = new StackPane(imageView,hBox);
        
        Scene fullPageScene = new Scene(fullPageLayout, 1500, 700);
        fullPageLayout.setStyle("-fx-background-color: White;-fx-text-fill: white; -fx-background-radius: 20;");
        // Back button to return to the template page
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-text-fill: Black; -fx-background-color: white; -fx-background-radius: 10;");
        backButton.setOnAction(e -> primaryStage.setScene(templatePageScene));
        fullPageLayout.getChildren().add(backButton);
        backButton.setPrefSize(90, 50); // Updated to match the "View Cart" button size
        StackPane.setMargin(backButton, new Insets(20, 20, 20, 50));
        StackPane.setAlignment(backButton, Pos.TOP_LEFT);

        // Create a button with a bag icon instead of text
        Image bagImage = new Image(getClass().getResourceAsStream("/images/cart.png"));
        ImageView bagImageView = new ImageView(bagImage);
        bagImageView.setFitWidth(70);
        bagImageView.setFitHeight(70);
        Button viewCartButton = new Button("", bagImageView);
        viewCartButton.setStyle("-fx-text-fill: Black;  -fx-background-radius: 10;");
        viewCartButton.setPrefSize(90, 70); // Size matched to the "Back" button
        StackPane.setMargin(viewCartButton, new Insets(20, 50, 20, 20));
        viewCartButton.setOnAction(e -> HomeRenovationApp.navigateToCartPage());
        fullPageLayout.getChildren().add(viewCartButton);

        StackPane.setAlignment(viewCartButton, Pos.TOP_RIGHT);

        // Add to Cart button
        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setStyle("-fx-text-fill:Black;-fx-background-color:white;-fx-background-radius: 10;");
        addToCartButton.setPrefSize(200, 50);
        addToCartButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        StackPane.setAlignment(addToCartButton, Pos.BOTTOM_CENTER);
        StackPane.setMargin(addToCartButton, new Insets(20, 250, 20, 0));
        addToCartButton.setOnAction(event -> {
            addToCart(new CartItem(imageUrl, price, "Template"));
            showAddToCartNotification(); // Show notification on add to cart
        });
        fullPageLayout.getChildren().addAll(addToCartButton);
        fullPageLayout.setAlignment(imageView,Pos.CENTER_LEFT);
        fullPageLayout.setStyle("-fx-background-image: url('https://i.pinimg.com/564x/67/ba/08/67ba084e049b5873913c2ba820faf604.jpg')");
        
        
        StackPane.setMargin(imageView,new Insets( 20,20,20,100));
        StackPane.setAlignment(addToCartButton, Pos.BOTTOM_CENTER);
        StackPane.setMargin(addToCartButton, new Insets(20, 20, 20, 230));


        FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000), fullPageLayout);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(2.0);
        fadeTransition.play();

        primaryStage.setScene(fullPageScene);
    }
    
    private static void showAddToCartNotification() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Item Added to Cart");
        alert.setHeaderText(null);
        alert.setContentText("Item successfully added to cart!");
        alert.showAndWait();
    }

   

    public static List<CartItem> getCartItems() {
        return cartItems;
    }

    public static void removeFromCart(CartItem item) {
        cartItems.remove(item);
    }

    public static void setSelectedCategory(String category) {
        selectedCategory = category;
    }
    public static void addToCart(CartItem item) {
        if (dReference != null) {
            CollectionReference cartRef = dReference.collection("cart");
            Map<String, Object> cartItemData = new HashMap<>();
            cartItemData.put("ImageURL", item.getImageUrl());
            cartItemData.put("price", item.getPrice());
            cartItemData.put("type", item.getItemType());

            ApiFuture<DocumentReference> future = cartRef.add(cartItemData);
            future.addListener(() -> {
                try {
                    DocumentReference docRef = future.get();
                    System.out.println("Document added with ID: " + docRef.getId());
                    showAddToCartNotification();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, Runnable::run);
        } else {
            System.err.println("Firestore DocumentReference is not initialized.");
        }
    }


}
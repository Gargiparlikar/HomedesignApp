package com.homedesignhub.controllers;

// import com.homedesignhub.Sevices.firebaseconfig;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.control.Button;
// import javafx.scene.control.ContentDisplay;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.effect.DropShadow;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.layout.Background;
// import javafx.scene.layout.BackgroundFill;
// import javafx.scene.layout.CornerRadii;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.StackPane;
// import javafx.scene.layout.TilePane;
// import javafx.scene.layout.VBox;
// import javafx.scene.paint.Color;

// public class FurniturePage extends VBox {
//     private static TilePane tilePane = new TilePane();
//     private firebaseconfig fb;
//     private static HomeRenovationApp controller;

//     public FurniturePage() {
//         initialize();
//     }

//     private void initialize() {
//         tilePane.setPadding(new Insets(15, 15, 15, 15));
//         tilePane.setHgap(15);
//         tilePane.setVgap(15);
//         this.getChildren().add(tilePane);  // Add tilePane to the VBox

//         ScrollPane scrollPane = new ScrollPane(tilePane);
//         scrollPane.setFitToWidth(true);

//         // Create a back button
//         Button backButton = new Button("Back");
//         backButton.setOnAction(e -> {

//             HomeRenovationApp.navigateToRoomCategoryPage();
//             tilePane.getChildren().clear();
//         });
//         backButton.setPrefSize(150, 50);
//         backButton.setAlignment(Pos.TOP_LEFT);

//         Button viewCartButton = new Button("View Cart");
//       viewCartButton.setOnAction(e -> HomeRenovationApp.navigateToCartPage());
//       viewCartButton.setPrefSize(150, 50);
//       viewCartButton.setAlignment(Pos.TOP_RIGHT); // Align to the top-right corner

//        // HBox layout to arrange components horizontally
//       HBox buttonBox = new HBox();
//     buttonBox.getChildren().addAll(backButton, viewCartButton);
//     buttonBox.setSpacing(10); // Set spacing between buttons
//      buttonBox.setAlignment(Pos.TOP_RIGHT); // Align components to the top-right

// // StackPane for positioning the View Cart button
// StackPane buttonPane = new StackPane();
// buttonPane.getChildren().add(buttonBox);

// // Create a VBox layout to arrange components vertically
// VBox layout = new VBox();
// layout.getChildren().addAll(buttonPane, scrollPane);
// layout.setSpacing(10); // Set spacing between components
// layout.setPadding(new Insets(15)); // Set padding around the layout

// // Add the layout to your TilePane (assuming CartPage extends TilePane)
// this.getChildren().add(layout);
//     }


//     public void createButton(String imageUrl, String price) {
//         Image image = new Image(imageUrl);
//         ImageView imageView = new ImageView(image);
//         imageView.setFitWidth(300);
//         imageView.setFitHeight(300);
//         imageView.setEffect(new DropShadow(20, Color.BLACK));

//         Button productButton = new Button(price);
//         productButton.setGraphic(imageView);
//         productButton.setContentDisplay(ContentDisplay.TOP);
//         productButton.setStyle("-fx-font-size: 23px; -fx-font-color: BLACK");
//         productButton
//                 .setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, new CornerRadii(20), Insets.EMPTY)));
//         productButton.setPrefSize(300, 350);

//         // Add to Cart button
//         Button addToCartButton = new Button("Add to Cart");
//         addToCartButton.setOnAction(event -> {
//             HomeRenovationApp.addToCart(new CartItem(imageUrl, price, "Furniture"));
//             System.out.println("Added to cart: " + imageUrl);
//         });

//         VBox productContainer = new VBox(10, productButton, addToCartButton);
//         productContainer.setAlignment(Pos.CENTER);

//         tilePane.getChildren().addAll(productContainer);
//     }

//     public static TilePane getTilePane() {
//         return tilePane;
//     }

//     public static void setTilePane(TilePane tilePane) {
//         FurniturePage.tilePane = tilePane;
//     }

//     public void clearTiles() {
//         tilePane.getChildren().clear();
//     }
// }

package com.homedesignhub.controllers;

import com.google.cloud.firestore.DocumentSnapshot;
import com.homedesignhub.Sevices.firebaseconfig;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DesignerDashboardController extends Application {

    private ListView<String> userListView;
    private VBox userDetailsVBox;

    private firebaseconfig firebase;

    public DesignerDashboardController() {
        firebase = new firebaseconfig();
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = createDesignerDashboardScene();
        primaryStage.setTitle("Designer Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Scene createDesignerDashboardScene() {
        AnchorPane root = new AnchorPane();

        userListView = new ListView<>();
        userDetailsVBox = new VBox();
        userDetailsVBox.setSpacing(10.0);

        AnchorPane.setTopAnchor(userListView, 10.0);
        AnchorPane.setLeftAnchor(userListView, 10.0);
        AnchorPane.setBottomAnchor(userListView, 10.0);
        AnchorPane.setRightAnchor(userDetailsVBox, 10.0);
        AnchorPane.setTopAnchor(userDetailsVBox, 10.0);
        AnchorPane.setLeftAnchor(userDetailsVBox, 200.0);
        AnchorPane.setBottomAnchor(userDetailsVBox, 10.0);

        root.getChildren().addAll(userListView, userDetailsVBox);

        try {
            // Fetch all users
            List<Map<String, Object>> users = firebase.getAllUsers();

            if (users == null) {
                throw new RuntimeException("Failed to fetch users from Firebase");
            }

            // Populate the ListView with usernames
            ObservableList<String> items = FXCollections.observableArrayList();
            for (Map<String, Object> user : users) {
                String username = (String) user.get("username");
                if (username != null) {
                    items.add(username);
                }
            }

            // Update ListView with items
            if (userListView != null) {
                userListView.setItems(items);
            } else {
                throw new RuntimeException("userListView is not initialized");
            }

            // Set up a listener for selected user
            userListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    try {
                        if (newValue != null) {
                            displayUserDetails(newValue);
                        }
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Scene(root, 600, 400);
    }

    private void displayUserDetails(String username) throws ExecutionException, InterruptedException {
        userDetailsVBox.getChildren().clear();

        DocumentSnapshot userDoc = firebase.getData("users", username);
        if (userDoc.exists()) {
            userDetailsVBox.getChildren().add(createTextLabel("Username: " + username));
            userDetailsVBox.getChildren().add(createTextLabel("Name: " + userDoc.getString("name")));
            userDetailsVBox.getChildren().add(createTextLabel("Address: " + userDoc.getString("address")));
            userDetailsVBox.getChildren().add(createTextLabel("Phone Number: " + userDoc.getString("phone")));

            List<Map<String, Object>> cartItems = firebase.getUserCartItems(username);
            userDetailsVBox.getChildren().add(createTextLabel("Cart Items:"));
            for (Map<String, Object> item : cartItems) {
                String imageUrl = (String) item.get("ImageURL");
                if (imageUrl != null) {
                    ImageView imageView = new ImageView(new Image(imageUrl));
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    userDetailsVBox.getChildren().add(imageView);
                }
            }
        } else {
            showAlert("User not found", "User details not available.");
        }
    }

    private javafx.scene.control.Label createTextLabel(String text) {
        javafx.scene.control.Label label = new javafx.scene.control.Label(text);
        return label;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    }


package com.homedesignhub.controllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.homedesignhub.Sevices.firebaseconfig;

public class SignupController {

    private LoginController loginpage;
    private firebaseconfig firestoreConfig;

    public SignupController(LoginController loginpage) {
        this.loginpage = loginpage;
        this.firestoreConfig = new firebaseconfig(); // Initialize Firebase configuration
    }

    public SignupController(HomeRenovationApp homeRenovationApp) {
        //TODO Auto-generated constructor stub
    }

    public Scene createSignupScene(Stage primaryStage) {
        // Setup background image and layout
        Image backgroundImage = new Image("/images/logo.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1920);
        backgroundImageView.setFitHeight(1000);
        Pane backgroundPane = new Pane(backgroundImageView);

        // Signup form fields
        Label fNameLabel = new Label("First Name");
        fNameLabel.setStyle("-fx-text-fill: white;");
        fNameLabel.setFont(new Font(20));
        TextField fNameTextField = new TextField();
        fNameTextField.setPromptText("Enter First Name");
        fNameTextField.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: turquoise; -fx-border-width: 0 0 1 0;");

        Label lNameLabel = new Label("Last Name");
        lNameLabel.setStyle("-fx-text-fill: white;");
        lNameLabel.setFont(new Font(20));
        TextField lNameTextField = new TextField();
        lNameTextField.setPromptText("Enter Last Name");
        lNameTextField.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: turquoise; -fx-border-width: 0 0 1 0;");

        Label mNumberLabel = new Label("Mobile No.");
        mNumberLabel.setStyle("-fx-text-fill: white;");
        mNumberLabel.setFont(new Font(20));
        TextField mNamTextField = new TextField();
        mNamTextField.setPromptText("Enter Mobile No.");
        mNamTextField.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: turquoise; -fx-border-width: 0 0 1 0;");

        Label addressLabel = new Label("Address");
        addressLabel.setStyle("-fx-text-fill: white;");
        addressLabel.setFont(new Font(20));
        TextField addTextField = new TextField();
        addTextField.setPromptText("Enter Address");
        addTextField.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: turquoise; -fx-border-width: 0 0 1 0;");

        Label userLabel = new Label("Username");
        userLabel.setStyle("-fx-text-fill: white;");
        userLabel.setFont(new Font(20));
        TextField userTextField = new TextField();
        userTextField.setPromptText("Enter Username");
        userTextField.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: turquoise; -fx-border-width: 0 0 1 0;");

        Label passLabel = new Label("Password");
        passLabel.setStyle("-fx-text-fill: white;");
        passLabel.setFont(new Font(20));
        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter Password");
        passField.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: turquoise; -fx-border-width: 0 0 1 0;");

        Button signupButton = new Button("Signup");
        signupButton.setFont(new Font(20));
        signupButton.setPrefSize(200, 40);
        signupButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: transparent;");
        signupButton.setOnAction(event -> handleSignup(
                fNameTextField.getText(),
                lNameTextField.getText(),
                mNamTextField.getText(),
                addTextField.getText(),
                userTextField.getText(),
                passField.getText(), false));

        Button designerSignupButton = new Button("Signup as Designer");
        designerSignupButton.setFont(new Font(20));
        designerSignupButton.setPrefSize(200, 40);
        designerSignupButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: transparent;");
        designerSignupButton.setOnAction(event -> handleSignup(
                fNameTextField.getText(),
                lNameTextField.getText(),
                mNamTextField.getText(),
                addTextField.getText(),
                userTextField.getText(),
                passField.getText(), true));

        VBox signupFields = new VBox(20, fNameLabel, fNameTextField, lNameLabel, lNameTextField,
                mNumberLabel, mNamTextField, addressLabel, addTextField,
                userLabel, userTextField, passLabel, passField, signupButton, designerSignupButton);
        signupFields.setAlignment(Pos.CENTER);
        signupFields.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.5), new CornerRadii(10), Insets.EMPTY)));
        signupFields.setPadding(new Insets(20));
        signupFields.setMaxWidth(600);

        // Navigation buttons
        Button loginPageNavigator = new Button("Login");
        loginPageNavigator.setPrefSize(200, 50);
        loginPageNavigator.setFont(new Font(30));
        loginPageNavigator.setStyle(" -fx-text-fill: Black; -fx-border-color:white;");
        loginPageNavigator.setOnAction(event -> loginpage.showLoginScene());

        Button signupPageNavigator = new Button("Sign Up");
        signupPageNavigator.setTextFill(Color.TEAL);
        signupPageNavigator.setPrefSize(200, 50);
        signupPageNavigator.setFont(new Font(30));
        signupPageNavigator.setStyle(" -fx-text-fill: black; -fx-border-color: white;");
        signupPageNavigator.setOnAction(event -> loginpage.showSignupScene());

        HBox navigationButtonBox = new HBox(160, loginPageNavigator, signupPageNavigator);
        navigationButtonBox.setAlignment(Pos.CENTER);
        navigationButtonBox.setPrefSize(1700, 100);

        // Main layout
        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPrefSize(1700, 900);
        mainLayout.getChildren().addAll(signupFields, navigationButtonBox);

        // Root group
        Group root = new Group(backgroundPane, mainLayout);
        return new Scene(root, 1920, 1000);
    }

    private void handleSignup(String fName, String lName, String mNo, String address, String username, String password, boolean isDesigner) {
        // Validate signup data (add validation as needed)

        // Prepare data for Firestore
        Map<String, Object> userData = new HashMap<>();
        userData.put("firstName", fName);
        userData.put("lastName", lName);
        userData.put("mobileNumber", mNo);
        userData.put("address", address);
        userData.put("username", username);
        userData.put("password", password);

        String collection = isDesigner ? "designers" : "users";

        try {
            // Save user data to Firestore
            firestoreConfig.addData(collection, username, userData);

            // Show alert for successful signup
            firestoreConfig.showAlert("Signup Successful", "User registered successfully!");

            // Navigate back to login page
            loginpage.showLoginScene();
        } catch (ExecutionException | InterruptedException e) {
            // Handle exceptions (e.g., Firestore write failure)
            firestoreConfig.showAlert("Signup Error", "Failed to register user: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

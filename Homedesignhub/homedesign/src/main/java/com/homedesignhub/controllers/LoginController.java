 package com.homedesignhub.controllers;
 
 import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.homedesignhub.Sevices.firebaseconfig;

public class LoginController {

    private Stage primaryStage;
    private Scene loginScene;
    private Scene roomCategorySelectionScene;
    private SignupController signupController;
    private HomeRenovationApp homeRenovationApp;
    private firebaseconfig firebase;

    private ImageView backgroundImageView;
    private int currentImageIndex = 0;
    private Image[] backgroundImages = {
            new Image("https://media.designcafe.com/wp-content/uploads/2024/07/07222216/transitional-interior-design.jpg"),
            new Image("https://media.designcafe.com/wp-content/uploads/2024/07/07214722/hanging-modern-art-glass-painting-designs.jpg"),
            new Image("https://media.designcafe.com/wp-content/uploads/2024/05/22165542/rattan-furniture-care.jpg"),
            new Image("https://media.designcafe.com/wp-content/uploads/2024/05/20211358/rustic-kitchen-wicker-baskets-1.jpg"),
    };

    public LoginController(Stage primaryStage, HomeRenovationApp homeRenovationApp) {
        this.primaryStage = primaryStage;
        this.homeRenovationApp = homeRenovationApp;
        firebase = new firebaseconfig();
        initScenes();
    }

    private void initScenes() {
        initLoginScene();
        initRoomCategorySelectionScene();
    }

    public void initLoginScene() {
        Label messageLabel = new Label("Welcome to Home Design Hub");
        messageLabel.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 40));
        messageLabel.setTextFill(Color.BLACK);
        messageLabel.setAlignment(Pos.CENTER);

        Rectangle messageBackground = new Rectangle();
        messageBackground.setFill(Color.WHITE);
        messageBackground.setStroke(Color.BLACK);
        messageBackground.setArcWidth(30);
        messageBackground.setArcHeight(30);
        messageBackground.setWidth(700); // Ensure the rectangle width is consistent
        messageBackground.setHeight(100); // Ensure the rectangle height is consistent

        StackPane messageBox = new StackPane();
        messageBox.getChildren().addAll(messageBackground, messageLabel);
        messageBox.setAlignment(Pos.CENTER);
        messageBox.setPadding(new Insets(40));
        messageBox.setStyle("-fx-background-color: rgba(255,255,255,0.5); -fx-background-radius: 30; -fx-border-radius: 30;");

        messageBox.setMaxWidth(700);

        VBox vBoxMessage = new VBox(messageBox);
        vBoxMessage.setAlignment(Pos.CENTER);

        // Create a pane for the background images
        Pane backgroundPane = new Pane();
        backgroundPane.setPrefSize(1920, 1000);

        // Initialize two ImageView objects
        ImageView backgroundImageView1 = new ImageView();
        ImageView backgroundImageView2 = new ImageView();

        backgroundImageView1.setFitWidth(1920);
        backgroundImageView1.setFitHeight(1000);
        backgroundImageView1.setPreserveRatio(false);

        backgroundImageView2.setFitWidth(1920);
        backgroundImageView2.setFitHeight(1000);
        backgroundImageView2.setPreserveRatio(false);

        backgroundPane.getChildren().addAll(backgroundImageView1, backgroundImageView2);

        backgroundImageView1.setImage(backgroundImages[0]); // Set initial image

        // Create timeline for crossfade slideshow
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, 
                event -> {
                    backgroundImageView1.setImage(backgroundImages[currentImageIndex]);
                    backgroundImageView1.setOpacity(1.0);
                    backgroundImageView2.setOpacity(0.0);
                }
            ),
            new KeyFrame(Duration.seconds(3), 
                event -> {
                    currentImageIndex = (currentImageIndex + 1) % backgroundImages.length;
                    backgroundImageView2.setImage(backgroundImages[currentImageIndex]);
                    FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), backgroundImageView1);
                    fadeOut.setFromValue(1.0);
                    fadeOut.setToValue(0.0);
                    fadeOut.play();

                    FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), backgroundImageView2);
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1.0);
                    fadeIn.play();
                }
            )
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Image userIcon = new Image(getClass().getResourceAsStream("/images/username.jpg"));
        ImageView userIconView = new ImageView(userIcon);
        userIconView.setFitWidth(30);
        userIconView.setFitHeight(30);
        Label userLabel = new Label("Username", userIconView);
        userLabel.setStyle("-fx-text-fill:White;");
        userLabel.setFont(new Font(30));
        TextField userTextField = new TextField();
        userTextField.setPromptText("Enter Username");
        userTextField.setPrefSize(300, 70);
        userTextField.setStyle("-fx-background-color:White;-fx-text-fill:black;");

        Image passIcon = new Image(getClass().getResourceAsStream("/images/password.png"));
        ImageView passIconView = new ImageView(passIcon);
        passIconView.setFitWidth(30);
        passIconView.setFitHeight(30);
        Label passLabel = new Label("Password", passIconView);
        passLabel.setStyle("-fx-text-fill:White;");
        passLabel.setFont(new Font(30));
        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter Password");
        passField.setStyle("-fx-background-color:White;-fx-text-fill:black;");

        Button loginButton = new Button(" User Login");
        loginButton.setFont(new Font(20));
        loginButton.setPrefSize(200, 40);
        loginButton.setStyle("-fx-text-fill:Black;-fx-background-color:white;-fx-background-radius: 10;");
        loginButton.setAlignment(Pos.TOP_CENTER);
        loginButton.setOnAction(event -> {
            try {
                handleLogin(userTextField.getText(), passField.getText(), "user");
                userTextField.clear();
                passField.clear();
            } catch (ExecutionException | InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });

        // Add Interior Designer Login Button
        Button designerLoginButton = new Button("Designer Login");
        designerLoginButton.setFont(new Font(20));
        designerLoginButton.setPrefSize(200, 40);
        designerLoginButton.setStyle("-fx-text-fill:Black;-fx-background-color:white;-fx-background-radius: 10;");
        designerLoginButton.setAlignment(Pos.TOP_CENTER);
        designerLoginButton.setOnAction(event -> {
            try {
                handleLogin(userTextField.getText(), passField.getText(), "designer");
                userTextField.clear();
                passField.clear();
            } catch (ExecutionException | InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });

        VBox fieldBox1 = new VBox(10, userLabel, userTextField);
        fieldBox1.setMaxSize(500, 30);

        VBox fieldBox2 = new VBox(10, passLabel, passField);
        fieldBox2.setMaxSize(500, 40);

        HBox buttonBox = new HBox(20, loginButton, designerLoginButton);
        buttonBox.setMaxSize(500, 30);
        buttonBox.setAlignment(Pos.CENTER);

        VBox inputBox = new VBox(20, fieldBox1, fieldBox2, buttonBox);
        inputBox.setAlignment(Pos.CENTER);

        Rectangle rectangle = new Rectangle(700, 400);
        rectangle.setFill(Color.rgb(0, 0, 0, 0.5));
        rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);

        StackPane stackPane = new StackPane(rectangle, inputBox);
        stackPane.setAlignment(Pos.CENTER);

        Button loginPageNavigator = new Button(" Login");
        loginPageNavigator.setTextFill(Color.BLACK);
        loginPageNavigator.setPrefSize(200, 30);
        loginPageNavigator.setFont(new Font(30));
        loginPageNavigator.setOnAction(event -> showLoginScene());

        Button signupPageNavigator = new Button("Sign Up");
        signupPageNavigator.setPrefSize(200, 50);
        signupPageNavigator.setFont(new Font(30));
        signupPageNavigator.setOnAction(event -> showSignupScene());

        HBox navigationButtonBox = new HBox(160, loginPageNavigator, signupPageNavigator);
        navigationButtonBox.setAlignment(Pos.CENTER);
        navigationButtonBox.setPrefSize(1700, 100);

        VBox vBoxMain = new VBox(40, vBoxMessage, stackPane, navigationButtonBox);
        vBoxMain.setPrefSize(1700, 900);
        vBoxMain.setAlignment(Pos.TOP_CENTER);
        vBoxMain.setLayoutY(140);

        Group gr = new Group(backgroundPane, vBoxMain);

        loginScene = new Scene(gr, 1920, 1000);
        loginScene.setFill(Color.BLACK);

        // Set initial position of message label for sliding animation
        messageLabel.setTranslateX(-700); // Adjust as needed

        // Add sliding animation to the welcome message
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), messageLabel);
        transition.setFromX(-700); // Adjust as needed
        transition.setToX(0);
        transition.play();

        applyFadeTransition(vBoxMain);
    }

    private void initRoomCategorySelectionScene() {
        RoomCategorySelection roomCategorySelection = new RoomCategorySelection(primaryStage);
        roomCategorySelectionScene = new Scene(roomCategorySelection, 1920, 1000);
    }

    public Scene getLoginScene() {
        if (loginScene == null) {
            initLoginScene();
        }
        return loginScene;
    }

    public void showLoginScene() {
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login Page");
        primaryStage.show();
    }

    private void handleLogin(String username, String password, String userType) throws ExecutionException, InterruptedException, IOException {
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Invalid Login", "Please enter username and password.");
            return;
        }
    
        boolean authenticated;
        if (userType.equals("designer")) {
            authenticated = firebase.authenticateDesigner(username, password);
            if (authenticated) {
                DesignerDashboardController designerDashboard = new DesignerDashboardController();
                Scene designerDashboardScene = designerDashboard.createDesignerDashboardScene();
                primaryStage.setScene(designerDashboardScene);
            } else {
                showAlert("Invalid Login", "Invalid username or password.");
            }
        } else {
            authenticated = firebase.authenticateUser(username, password);
            if (authenticated) {
                initRoomCategorySelectionScene();
                primaryStage.setScene(roomCategorySelectionScene);
            } else {
                showAlert("Invalid Login", "Invalid username or password.");
            }
        }
    }
    

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showSignupScene() {
        signupController = new SignupController(this);
        Scene signupScene = signupController.createSignupScene(primaryStage);

        primaryStage.setScene(signupScene);
        primaryStage.setTitle("Signup Page");
        primaryStage.show();
    }

    private void handleLogout() {
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login Page");
    }

    private void applyFadeTransition(VBox vbox) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), vbox);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }
}

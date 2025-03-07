package com.homedesignhub.controllers;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BookingConfirmationPage extends StackPane {
    public BookingConfirmationPage() {
        Label confirmationLabel = new Label("Designer is booked successfully");
        confirmationLabel.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        setAlignment(confirmationLabel, Pos.CENTER);

        getChildren().add(confirmationLabel);
    }
}


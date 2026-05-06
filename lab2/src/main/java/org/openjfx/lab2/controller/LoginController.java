package org.openjfx.lab2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.openjfx.lab2.App;
import org.openjfx.lab2.User;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller for login.fxml. Validates user input against the provided
 * list of users; on success switches the Stage's Scene to the welcome
 * screen, on failure shows an inline error.
 *
 * @authors Waseem Sheety, Adam Karain
 */
public class LoginController {

    @FXML private AnchorPane loginScreen;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Label errorLabel;

    private ArrayList<User> users;
    private Stage stage;

    /** Injected by App.start() so the controller knows the valid users. */
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    /** Injected by App.start() so the controller can swap Scenes. */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Triggered when the login button is clicked.
     * Searches the user list for a matching username/password pair.
     * On match: loads welcome.fxml and replaces the current Scene.
     * On mismatch: displays the error message inline and clears the password.
     */
    @FXML
    void onLoginButtonClick(ActionEvent event) {
        String enteredUser = username.getText();
        String enteredPass = password.getText();

        for (User u : users) {
            if (u.getName().equals(enteredUser) && u.getPassword().equals(enteredPass)) {
                showWelcomeScreen();
                return;
            }
        }

        errorLabel.setText("user or password do not match");
        password.clear();
    }

    /** Loads welcome.fxml and replaces the current Scene on the same Stage. */
    private void showWelcomeScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("welcome.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            errorLabel.setText("Error loading welcome screen");
        }
    }
}
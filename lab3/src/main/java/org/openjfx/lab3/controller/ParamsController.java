package org.openjfx.lab3.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.openjfx.lab3.App;
import org.openjfx.lab3.User;
import org.openjfx.lab3.UserStateRepository;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller for params.fxml. Reads n (max attempts) and t (block seconds)
 * from the user, then loads login.fxml with those values and the user list.
 *
 * @authors Waseem Sheety, Adam Karain
 */
public class ParamsController {

    @FXML private TextField nField;
    @FXML private TextField tField;
    @FXML private Label     errorLabel;

    private ArrayList<User> users;
    private Stage           stage;

    public void setUsers(ArrayList<User> users) { this.users = users; }
    public void setStage(Stage stage)           { this.stage = stage; }

    /**
     * Validates n and t as positive integers and switches to the login Scene.
     */
    @FXML
    void onContinueClick(ActionEvent event) {
        int n, t;
        try {
            n = Integer.parseInt(nField.getText().trim());
            t = Integer.parseInt(tField.getText().trim());
        } catch (NumberFormatException e) {
            errorLabel.setText("Please enter whole numbers in both fields");
            return;
        }

        if (n <= 0 || t <= 0) {
            errorLabel.setText("Both n and t must be positive integers");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("login.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            loginController.setStage(stage);
            loginController.setUserStateRepository(new UserStateRepository(users));
            loginController.setLimits(n, t);

            stage.setScene(new Scene(root, 320, 240));
            stage.setTitle("Users Login");
        } catch (IOException e) {
            errorLabel.setText("Error loading login screen");
        }
    }
}

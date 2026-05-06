package org.openjfx.lab2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openjfx.lab2.controller.LoginController;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Application entry point. Loads the valid users, displays the login screen,
 * and injects the user list + stage into the LoginController.
 *
 * @authors Waseem Sheety, Adam Karain
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Load valid users from users.txt (classpath resource)
        ArrayList<User> users = UserRepository.loadUsers();

        // Load the login FXML and build the scene
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        // Inject dependencies into the LoginController instance created by FXML
        LoginController controller = fxmlLoader.getController();
        controller.setUsers(users);
        controller.setStage(stage);

        stage.setTitle("Users Login");
        stage.setScene(scene);
        stage.show();
    }
}
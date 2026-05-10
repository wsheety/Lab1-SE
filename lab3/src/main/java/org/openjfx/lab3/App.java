package org.openjfx.lab3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openjfx.lab3.controller.ParamsController;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Application entry point. Loads the valid users, displays the params screen,
 * and injects the user list + stage into the ParamsController.
 *
 * @authors Waseem Sheety, Adam Karain
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Load valid users from users.txt (classpath resource)
        ArrayList<User> users = UserRepository.loadUsers();

        // Load the params FXML and build the scene
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("params.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        // Inject dependencies into the ParamsController instance created by FXML
        ParamsController controller = fxmlLoader.getController();
        controller.setUsers(users);
        controller.setStage(stage);

        stage.setTitle("Login Settings");
        stage.setScene(scene);
        stage.show();
    }
}

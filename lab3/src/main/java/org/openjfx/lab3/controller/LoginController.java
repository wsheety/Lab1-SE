package org.openjfx.lab3.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.openjfx.lab3.App;
import org.openjfx.lab3.UserState;
import org.openjfx.lab3.UserStateRepository;
import org.openjfx.lab3.threads.UpdateAttemptsThread;
import org.openjfx.lab3.threads.VerifyNotBlockedThread;

import java.io.IOException;

/**
 * Controller for login.fxml. On each login click spawns the appropriate
 * worker thread, and after n failed attempts disables the inputs and runs
 * a t-second countdown before re-enabling them.
 *
 * @authors Waseem Sheety, Adam Karain
 */
public class LoginController {

    @FXML private AnchorPane    loginScreen;
    @FXML private TextField     username;
    @FXML private PasswordField password;
    @FXML private Label         errorLabel;
    @FXML private Button        loginButton;

    private Stage               stage;
    private UserStateRepository repo;
    private int                 maxAttempts;     // n
    private int                 blockSeconds;    // t
    private Timeline            countdownTimeline;

    public void setStage(Stage stage)                          { this.stage = stage; }
    public void setUserStateRepository(UserStateRepository r)  { this.repo  = r;     }
    public void setLimits(int n, int t) {
        this.maxAttempts  = n;
        this.blockSeconds = t;
    }

    /**
     * Routes each login click to the correct worker thread without blocking
     * the FX thread.
     */
    @FXML
    void onLoginButtonClick(ActionEvent event) {
        String enteredUser = username.getText();
        String enteredPass = password.getText();

        UserState match = repo.findMatching(enteredUser, enteredPass);
        if (match != null) {
            errorLabel.setText("Verifying...");
            new VerifyNotBlockedThread(match, blockSeconds, this::onVerifyDone).start();
            return;
        }

        UserState knownEmail = repo.findByEmail(enteredUser);
        if (knownEmail != null) {
            new UpdateAttemptsThread(knownEmail, maxAttempts, this::onAttemptsUpdated).start();
        } else {
            errorLabel.setText("user or password do not match");
            password.clear();
        }
    }

    /** Callback for VerifyNotBlockedThread, runs on the FX thread. */
    private void onVerifyDone(boolean allowed) {
        if (allowed) {
            showWelcomeScreen();
        } else {
            UserState s = repo.findByEmail(username.getText());
            long left   = s == null ? blockSeconds : s.remainingBlockSeconds(blockSeconds);
            errorLabel.setText("Account is blocked. Try again in " + left + "s");
            startBlockCountdown((int) left);
        }
    }

    /** Callback for UpdateAttemptsThread, runs on the FX thread. */
    private void onAttemptsUpdated(UpdateAttemptsThread.Result r) {
        if (r.justBlocked) {
            errorLabel.setText("Too many failed attempts. Blocked for " + blockSeconds + "s");
            startBlockCountdown(blockSeconds);
        } else {
            int left = maxAttempts - r.attempts;
            errorLabel.setText("user or password do not match (" + left + " attempt(s) left)");
        }
        password.clear();
    }

    /**
     * Disables the inputs and ticks a per-second countdown until zero,
     * then re-enables the inputs for another n attempts.
     */
    private void startBlockCountdown(int seconds) {
        if (countdownTimeline != null) countdownTimeline.stop();

        setInputsDisabled(true);
        final int[] remaining = { seconds };

        countdownTimeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            remaining[0]--;
            if (remaining[0] <= 0) {
                countdownTimeline.stop();
                setInputsDisabled(false);
                errorLabel.setText("You may try again");
                username.clear();
                password.clear();
            } else {
                errorLabel.setText("Blocked. Try again in " + remaining[0] + "s");
            }
        }));
        countdownTimeline.setCycleCount(seconds);
        countdownTimeline.play();
    }

    private void setInputsDisabled(boolean disabled) {
        username.setDisable(disabled);
        password.setDisable(disabled);
        loginButton.setDisable(disabled);
    }

    /** Loads welcome.fxml and replaces the current Scene on the same Stage. */
    private void showWelcomeScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("welcome.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Welcome");
        } catch (IOException e) {
            errorLabel.setText("Error loading welcome screen");
        }
    }
}

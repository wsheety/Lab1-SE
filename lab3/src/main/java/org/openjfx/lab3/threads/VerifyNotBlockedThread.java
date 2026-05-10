package org.openjfx.lab3.threads;

import javafx.application.Platform;
import org.openjfx.lab3.UserState;

import java.util.function.Consumer;

/**
 * Given a user with valid credentials, checks they aren't blocked and reports
 * the result back on the JavaFX thread.
 *
 * @authors Waseem Sheety, Adam Karain
 */
public class VerifyNotBlockedThread extends Thread {

    private final UserState         state;
    private final int               blockSeconds;
    private final Consumer<Boolean> uiCallback;

    public VerifyNotBlockedThread(UserState state,
                                  int blockSeconds,
                                  Consumer<Boolean> uiCallback) {
        super("verify-not-blocked-" + state.getUser().getName());
        this.state        = state;
        this.blockSeconds = blockSeconds;
        this.uiCallback   = uiCallback;
    }

    @Override
    public void run() {
        boolean allowed;
        synchronized (state) {
            allowed = !state.isBlocked(blockSeconds);
            if (allowed) state.resetAttempts();
        }
        final boolean result = allowed;
        Platform.runLater(() -> uiCallback.accept(result));
    }
}

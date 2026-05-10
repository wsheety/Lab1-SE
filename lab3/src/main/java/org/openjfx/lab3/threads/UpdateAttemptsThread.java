package org.openjfx.lab3.threads;

import javafx.application.Platform;
import org.openjfx.lab3.UserState;

import java.util.function.Consumer;

/**
 * Increments the failed-attempt counter for one user, and if the counter
 * reaches n blocks the user. Result is delivered back on the JavaFX thread.
 *
 * @authors Waseem Sheety, Adam Karain
 */
public class UpdateAttemptsThread extends Thread {

    /** Snapshot of what the thread did, passed to the UI callback. */
    public static final class Result {
        public final int     attempts;
        public final boolean justBlocked;
        public Result(int attempts, boolean justBlocked) {
            this.attempts    = attempts;
            this.justBlocked = justBlocked;
        }
    }

    private final UserState        state;
    private final int              maxAttempts;
    private final Consumer<Result> uiCallback;

    public UpdateAttemptsThread(UserState state,
                                int maxAttempts,
                                Consumer<Result> uiCallback) {
        super("update-attempts-" + state.getUser().getName());
        this.state       = state;
        this.maxAttempts = maxAttempts;
        this.uiCallback  = uiCallback;
    }

    @Override
    public void run() {
        boolean justBlocked;
        int     attempts;
        synchronized (state) {
            attempts    = state.incrementFailedAttempts();
            justBlocked = false;
            if (attempts >= maxAttempts) {
                state.block();
                justBlocked = true;
            }
        }

        Result result = new Result(attempts, justBlocked);
        Platform.runLater(() -> uiCallback.accept(result));
    }
}

package org.openjfx.lab3;

/**
 * Tracks failed login attempts and block time for a single valid user.
 * Methods are synchronized so the worker threads can't race on the same record.
 *
 * @authors Waseem Sheety, Adam Karain
 */
public class UserState {

    private final User user;
    private int  failedAttempts;
    private long blockedAtMillis; // 0 = not blocked

    public UserState(User user) {
        this.user            = user;
        this.failedAttempts  = 0;
        this.blockedAtMillis = 0L;
    }

    public User getUser() { return user; }

    /** Increments the failed-attempt counter and returns the new value. */
    public synchronized int incrementFailedAttempts() {
        return ++failedAttempts;
    }

    /** Clears both the counter and any active block. */
    public synchronized void resetAttempts() {
        failedAttempts  = 0;
        blockedAtMillis = 0L;
    }

    /** Records the current time as the start of a block. */
    public synchronized void block() {
        blockedAtMillis = System.currentTimeMillis();
    }

    /**
     * True if the user is currently blocked. Once the t-second window is over,
     * clears the block and resets the counter so the user has a fresh allowance.
     */
    public synchronized boolean isBlocked(int blockSeconds) {
        if (blockedAtMillis == 0L) return false;

        long elapsed = System.currentTimeMillis() - blockedAtMillis;
        if (elapsed >= blockSeconds * 1000L) {
            failedAttempts  = 0;
            blockedAtMillis = 0L;
            return false;
        }
        return true;
    }

    /** Seconds left in the current block window, or 0 if not blocked. */
    public synchronized long remainingBlockSeconds(int blockSeconds) {
        if (blockedAtMillis == 0L) return 0L;
        long elapsedMs   = System.currentTimeMillis() - blockedAtMillis;
        long remainingMs = blockSeconds * 1000L - elapsedMs;
        return Math.max(0L, (remainingMs + 999L) / 1000L);
    }

    public synchronized int getFailedAttempts() { return failedAttempts; }
}

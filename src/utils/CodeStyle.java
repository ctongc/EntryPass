package utils;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

import java.sql.Connection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CodeStyle {
    @VisibleForTesting
    static final String USER_FILED = "user";

    @Nullable
    private Connection connection;
    // considering using Optional

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        boolean acquiredLock;
        try {
            acquiredLock = lock.tryLock(1L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // Log.info("Interrupted while doing x");
            Thread.currentThread().interrupt(); // When interrupted, reset thread interrupted state
        }
    }
}

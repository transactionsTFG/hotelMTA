package integration.environment;

import java.util.Arrays;

public abstract class PersistenceConfig {
    private static PersistenceConfig PERSISTENCE;

    protected PersistenceConfig() {
    }

    public static synchronized PersistenceConfig getInstance() {
        if (PERSISTENCE == null) {
            boolean isJUnitRunning = Arrays.stream(Thread.currentThread().getStackTrace())
                    .map(StackTraceElement::getClassName)
                    .anyMatch(className -> className.startsWith("org.junit."));
            PERSISTENCE = isJUnitRunning ? new JUnitEnv() : new GlassFishEnv();
        }

        return PERSISTENCE;

    }

    public abstract boolean isJUnitRunning();

    public abstract String getPersistenceName();
}
package hooks;

public class StepErrorTracker {
    private static final ThreadLocal<String> lastError = new ThreadLocal<>();

    public static void setLastError(String error) {
        lastError.set(error);
    }

    public static String getLastError() {
        return lastError.get();
    }

    public static void clear() {
        lastError.remove();
    }
}
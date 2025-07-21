package hooks;

public class StepTracker {
    private static final ThreadLocal<String> lastStepText = new ThreadLocal<>();

    public static void setLastStepText(String text) {
        lastStepText.set(text);
    }

    public static String getLastStepText() {
        return lastStepText.get();
    }
}
// src/test/java/hooks/ScreenshotCleaner.java
package hooks;

import java.io.File;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class ScreenshotCleaner {
    public static void deleteOldScreenshots(String directoryPath, int daysThreshold) {
        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) return;

        File[] files = dir.listFiles();
        if (files == null) return;

        Instant cutoff = Instant.now().minus(daysThreshold, ChronoUnit.DAYS);

        for (File file : files) {
            if (file.isFile()) {
                Instant fileTime = Instant.ofEpochMilli(file.lastModified());
                if (fileTime.isBefore(cutoff)) {
                    file.delete();
                }
            }
        }
    }
}
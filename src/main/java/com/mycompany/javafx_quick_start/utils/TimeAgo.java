package com.mycompany.javafx_quick_start.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeAgo {
    public static String getTimeAgo(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(dateTime, now);

        long seconds = duration.getSeconds();
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days > 0) {
            return String.format("%d days ago", days);
        } else if (hours > 0) {
            return String.format("%d hours ago", hours);
        } else if (minutes > 0) {
            return String.format("%d minutes ago", minutes);
        } else {
            return String.format("%d seconds ago", seconds);
        }
    }
}

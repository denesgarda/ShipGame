package com.denesgarda.ShipGame.util;

import java.util.Locale;

public class OS {
    public enum OSType {
        Windows,
        MacOS,
        Linux,
        OTHER
    }

    protected static OSType detectedOS;

    public static OSType getOperatingSystemType() {
        if (detectedOS == null) {
            String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((OS.contains("mac")) || (OS.contains("darwin"))) {
                detectedOS = OSType.MacOS;
            } else if (OS.contains("win")) {
                detectedOS = OSType.Windows;
            } else if (OS.contains("nux")) {
                detectedOS = OSType.Linux;
            } else {
                detectedOS = OSType.OTHER;
            }
        }
        return detectedOS;
    }
}

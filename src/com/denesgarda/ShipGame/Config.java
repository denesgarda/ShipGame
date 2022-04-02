package com.denesgarda.ShipGame;

import com.denesgarda.JarData.data.Serialized;
import com.denesgarda.JarData.data.statics.Serialization;
import com.denesgarda.ShipGame.util.OS;
import com.denesgarda.ShipGame.util.Popup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

public class Config {
    public final String path;
    public final String dirPath;
    public final String statsPath;

    public Config() {
        String homeDir = System.getProperty("user.home");
        OS.OSType os = OS.getOperatingSystemType();
        if (os == OS.OSType.Windows) {
            dirPath = homeDir + File.separator + "AppData" + File.separator + "Roaming" + File.separator + "ShipGame";
            path = dirPath + File.separator + "config.properties";
        } else if (os == OS.OSType.MacOS) {
            dirPath = homeDir + File.separator + "Library" + File.separator + "Application Support" + File.separator + "ShipGame";
            path = dirPath + File.separator + "config.properties";
        } else {
            dirPath = homeDir + File.separator + ".ShipGame";
            path = dirPath + File.separator + "config.properties";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            try {
                boolean successful = dir.mkdir();
                if (!successful) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Popup.error("Config Error", "Failed to generate config directory.", true);
            }
        }
        File file = new File(path);
        if (!file.exists()) {
            try {
                boolean successful = file.createNewFile();
                if (!successful) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Popup.error("Config Error", "Failed to generate config file.", true);
            }
        }
        statsPath = getPropertyNotNull("stats-path", dirPath + File.separator + "ShipGame.stats");
        readStats();
    }

    public String getPropertyNotNull(String key, String defaultValue) {
        try {
            Properties config = new Properties();
            config.load(new FileReader(path));
            String value = config.getProperty(key);
            if (value == null || value.isBlank()) {
                config.put(key, defaultValue);
                FileWriter out = new FileWriter(path);
                config.store(out, null);
                out.flush();
                out.close();
                return defaultValue;
            }
            return value;
        } catch (Exception e) {
            Popup.error("Config Error", "Failed to read/write to config file.", true);
            return null;
        }
    }

    public void setProperty(String key, String value) {
        try {
            Properties config = new Properties();
            config.load(new FileReader(path));
            config.put(key, value);
            FileWriter out = new FileWriter(path);
            config.store(out, null);
            out.flush();
            out.close();
        } catch (Exception e) {
            Popup.error("Config Error", "Failed to read/write to config file.", true);
        }
    }

    public void readStats() {
        try {
            File file = new File(statsPath);
            if (!file.exists()) {
                Main.stats = new Stats();
            } else {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String data = reader.readLine();
                Serialized serialized = new Serialized(data);
                Stats stats;
                try {
                    stats = (Stats) serialized.deSerialize();
                } catch (Exception e) {
                    stats = new Stats();
                }
                Main.stats = stats;
            }
        } catch (Exception e) {
            Popup.error("Config Error", "Failed to read/write to stats file.", true);
        }
    }

    public void writeStats(boolean win, Game.GameMode gameType) {
        if (win) {
            switch (gameType) {
                case EASY -> Main.stats.win_easy++;
                case MEDIUM -> Main.stats.win_medium++;
                case HARD -> Main.stats.win_hard++;
                case IMPOSSIBLE -> Main.stats.win_impossible++;
                case CUSTOM -> Main.stats.win_custom++;
            }
        } else {
            switch (gameType) {
                case EASY -> Main.stats.loss_easy++;
                case MEDIUM -> Main.stats.loss_medium++;
                case HARD -> Main.stats.loss_hard++;
                case IMPOSSIBLE -> Main.stats.loss_impossible++;
                case CUSTOM -> Main.stats.loss_custom++;
            }
        }
        try {
            Serialized serialized = Serialization.serialize(Main.stats);
            String data = serialized.getData();
            File file = new File(statsPath);
            if (!file.exists()) {
                boolean successful = file.createNewFile();
                if (!successful) {
                    throw new Exception();
                }
            }
            FileWriter out = new FileWriter(statsPath);
            out.write(data);
            out.flush();
            out.close();
        } catch (Exception e) {
            Popup.error("Config Error", "Failed to read/write to stats file.", true);
        }
    }
}

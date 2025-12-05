package org.k1zik.turnitdown;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigJson {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static ConfigData data = new ConfigData();

    static {
        load();
    }

    public static synchronized void load() {
        try {
            File configDir = new File("config");
            if (!configDir.exists()) {
                configDir.mkdirs();
            }

            File cfgFile = new File(configDir, "turnitdown.json");

            if (cfgFile.exists()) {
                try (FileReader reader = new FileReader(cfgFile)) {
                    data = gson.fromJson(reader, ConfigData.class);
                    if (data == null) {
                        data = new ConfigData();
                    }
                }
            } else {
                save();
            }
        } catch (IOException e) {
            System.err.println("[TurnItDown] Failed to load JSON config: " + e.getMessage());
            data = new ConfigData();
        }
    }

    public static synchronized void save() {
        try {
            File configDir = new File("config");
            if (!configDir.exists()) {
                configDir.mkdirs();
            }

            File cfgFile = new File(configDir, "turnitdown.json");
            try (FileWriter writer = new FileWriter(cfgFile)) {
                gson.toJson(data, writer);
            }
        } catch (IOException e) {
            System.err.println("[TurnItDown] Failed to save JSON config: " + e.getMessage());
        }
    }

    public static double getInitialVolume() {
        return data.initialVolume;
    }

    public static void setInitialVolume(double v) {
        data.initialVolume = v;
        save();
    }

    public static int getHudPositionY() {
        return data.hudPositionY;
    }

    public static void setHudPositionY(int y) {
        data.hudPositionY = y;
        save();
    }

    public static class ConfigData {
        public double initialVolume = 1.0;
        public int hudPositionY = 110;
    }
}

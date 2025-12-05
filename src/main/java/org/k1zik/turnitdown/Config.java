package org.k1zik.turnitdown;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Config {
    private static final double DEFAULT_INITIAL_VOLUME = 1.0;
    private static double initialVolume = DEFAULT_INITIAL_VOLUME;

    static {
        load();
    }

    public static synchronized void load() {
        try {
            File configDir = new File("config");
            if (!configDir.exists()) configDir.mkdirs();

            File cfg = new File(configDir, "turnitdown.properties");
            Properties props = new Properties();

            if (cfg.exists()) {
                try (InputStream in = new FileInputStream(cfg)) {
                    props.load(in);
                    String s = props.getProperty("initialVolume");
                    if (s != null) {
                        initialVolume = Double.parseDouble(s.trim());
                    }
                }
            } else {
                props.setProperty("initialVolume", Double.toString(DEFAULT_INITIAL_VOLUME));
                try (OutputStream out = new FileOutputStream(cfg)) {
                    props.store(out, "TurnItDown configuration");
                }
            }
        } catch (Exception e) {
            System.err.println("[TurnItDown] Failed to load config: " + e.getMessage());
            e.printStackTrace();
            initialVolume = DEFAULT_INITIAL_VOLUME;
        }
    }

    public static synchronized void save() {
        try {
            File configDir = new File("config");
            if (!configDir.exists()) configDir.mkdirs();
            File cfg = new File(configDir, "turnitdown.properties");
            Properties props = new Properties();
            props.setProperty("initialVolume", Double.toString(initialVolume));
            try (OutputStream out = new FileOutputStream(cfg)) {
                props.store(out, "TurnItDown configuration");
            }
        } catch (Exception e) {
            System.err.println("[TurnItDown] Failed to save config: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static double getInitialVolume() {
        return initialVolume;
    }

    public static void setInitialVolume(double v) {
        initialVolume = v;
        save();
    }
}

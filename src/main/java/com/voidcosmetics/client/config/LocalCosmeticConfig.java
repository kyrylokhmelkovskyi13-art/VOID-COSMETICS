package com.voidcosmetics.client.config;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/** Stores the selected cosmetic on this computer only. */
public final class LocalCosmeticConfig {
    private static final Path FILE = FabricLoader.getInstance()
            .getConfigDir().resolve("voidcosmetics.properties");

    private CosmeticType selected = CosmeticType.NONE;

    public CosmeticType getSelected() {
        return selected;
    }

    public void setSelected(CosmeticType selected) {
        this.selected = selected == null ? CosmeticType.NONE : selected;
        save();
    }

    public void load() {
        if (!Files.isRegularFile(FILE)) {
            save();
            return;
        }

        Properties properties = new Properties();
        try (var reader = Files.newBufferedReader(FILE, StandardCharsets.UTF_8)) {
            properties.load(reader);
            selected = CosmeticType.valueOf(
                    properties.getProperty("selected", "NONE").trim().toUpperCase()
            );
        } catch (IOException | IllegalArgumentException exception) {
            selected = CosmeticType.NONE;
        }
    }

    private void save() {
        Properties properties = new Properties();
        properties.setProperty("selected", selected.name());
        try {
            Files.createDirectories(FILE.getParent());
            try (var writer = Files.newBufferedWriter(FILE, StandardCharsets.UTF_8)) {
                properties.store(writer, "VOID COSMETICS - local settings");
            }
        } catch (IOException exception) {
            // Keep the game running if the local config cannot be written.
        }
    }
}

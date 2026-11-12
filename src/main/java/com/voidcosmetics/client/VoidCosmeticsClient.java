package com.voidcosmetics.client;

import com.voidcosmetics.client.config.LocalCosmeticConfig;
import com.voidcosmetics.client.screen.CosmeticMenuScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class VoidCosmeticsClient implements ClientModInitializer {
    public static final String MOD_ID = "voidcosmetics";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final LocalCosmeticConfig CONFIG = new LocalCosmeticConfig();

    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath(MOD_ID, "main")
    );
    private static KeyMapping openMenuKey;

    @Override
    public void onInitializeClient() {
        CONFIG.load();
        openMenuKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.voidcosmetics.open_menu",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.consumeClick()) {
                if (client.player != null) {
                    client.gui.setScreen(new CosmeticMenuScreen());
                }
            }
        });

        LOGGER.info("VOID COSMETICS initialized. Local-only selection: {}", CONFIG.getSelected());
    }
}

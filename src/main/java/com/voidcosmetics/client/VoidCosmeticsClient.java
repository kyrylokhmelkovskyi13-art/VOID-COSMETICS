package com.voidcosmetics.client;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.voidcosmetics.client.config.LocalCosmeticConfig;

/** Client-only entrypoint. No server code or network synchronization is used. */
public final class VoidCosmeticsClient implements ClientModInitializer {
    public static final String MOD_ID = "voidcosmetics";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final LocalCosmeticConfig CONFIG = new LocalCosmeticConfig();

    @Override
    public void onInitializeClient() {
        CONFIG.load();
        LOGGER.info("VOID COSMETICS client initialized. Local-only mode is enabled. Selected: {}", CONFIG.getSelected());
    }
}

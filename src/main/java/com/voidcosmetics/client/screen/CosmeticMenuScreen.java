package com.voidcosmetics.client.screen;

import com.voidcosmetics.client.VoidCosmeticsClient;
import com.voidcosmetics.client.config.CosmeticType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class CosmeticMenuScreen extends Screen {
    private static final CosmeticType[] ITEMS = {
            CosmeticType.DEMONIC_WINGS, CosmeticType.ANGEL_WINGS,
            CosmeticType.OBSIDIAN_HORNS, CosmeticType.SUNLIT_HALO,
            CosmeticType.VOID_CROWN, CosmeticType.RAVEN_MASK,
            CosmeticType.DRAGON_TAIL, CosmeticType.SPECTRAL_SWORDS
    };

    private static final String[] NAMES = {
            "Demonic Wings", "Angel Wings", "Obsidian Horns", "Sunlit Halo",
            "Void Crown", "Raven Mask", "Dragon Tail", "Spectral Swords"
    };

    private static final String[] DETAILS = {
            "Dark feathered back cosmetic", "Bright feathered back cosmetic",
            "Heavy forged head cosmetic", "Floating light ring",
            "Royal void headpiece", "Shadow face covering",
            "Scaled dragon tail", "Twin blades floating behind you"
    };

    private static final int[] COLORS = {
            0xFF9D3B48, 0xFFFFD875, 0xFF8A8A9B, 0xFFFFE9A3,
            0xFF9B70E8, 0xFF68718F, 0xFF4EAA82, 0xFF9C83D8
    };

    private int panelX;
    private int panelY;
    private int panelWidth;

    public CosmeticMenuScreen() {
        super(Component.literal("VOID COSMETICS"));
    }

    @Override
    protected void init() {
        panelWidth = Math.min(430, this.width - 24);
        panelX = (this.width - panelWidth) / 2;
        panelY = Math.max(12, (this.height - 330) / 2);

        int gap = 8;
        int cardWidth = (panelWidth - 44 - gap) / 2;
        int cardHeight = 54;
        int startX = panelX + 14;
        int startY = panelY + 76;

        for (int i = 0; i < ITEMS.length; i++) {
            final CosmeticType type = ITEMS[i];
            int col = i % 2;
            int row = i / 2;
            int x = startX + col * (cardWidth + gap);
            int y = startY + row * (cardHeight + 7);

            Button card = Button.builder(Component.empty(), button -> {
                VoidCosmeticsClient.CONFIG.setSelected(type);
            }).bounds(x, y, cardWidth, cardHeight).build();
            this.addRenderableWidget(card);
        }

        this.addRenderableWidget(Button.builder(Component.literal("UNEQUIP ALL"), button -> {
            VoidCosmeticsClient.CONFIG.setSelected(CosmeticType.NONE);
        }).bounds(panelX + 14, panelY + 306, 124, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("DONE"), button -> onClose())
                .bounds(panelX + panelWidth - 94, panelY + 306, 80, 20).build());
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractRenderState(graphics, mouseX, mouseY, delta);

        graphics.fill(0, 0, this.width, this.height, 0xA9000000);
        graphics.fill(panelX, panelY, panelX + panelWidth, panelY + 334, 0xFF171923);
        graphics.outline(panelX, panelY, panelWidth, 334, 0xFF55516D);
        graphics.fill(panelX + 1, panelY + 1, panelX + panelWidth - 1, panelY + 3, 0xFF9A72D8);

        graphics.text(this.font, "VOID COSMETICS", panelX + 14, panelY + 12, 0xFFFFFFFF, true);
        graphics.text(this.font, "YOUR LOOK. YOUR WORLD.", panelX + 15, panelY + 29, 0xFFB8A9D6, false);
        graphics.text(this.font, "LOCAL ONLY", panelX + panelWidth - 76, panelY + 15, 0xFF83D6A4, true);
        graphics.fill(panelX + 14, panelY + 53, panelX + panelWidth - 14, panelY + 54, 0xFF454052);

        int cardWidth = (panelWidth - 44 - 8) / 2;
        int cardHeight = 54;
        int startX = panelX + 14;
        int startY = panelY + 76;

        for (int i = 0; i < ITEMS.length; i++) {
            int col = i % 2;
            int row = i / 2;
            int x = startX + col * (cardWidth + 8);
            int y = startY + row * (cardHeight + 7);
            boolean selected = VoidCosmeticsClient.CONFIG.getSelected() == ITEMS[i];

            graphics.fill(x + 3, y + 3, x + 23, y + 23, COLORS[i]);
            graphics.fill(x + 6, y + 6, x + 20, y + 20, 0xFF20202A);
            graphics.fill(x + 9, y + 9, x + 17, y + 17, COLORS[i]);
            graphics.text(this.font, NAMES[i], x + 30, y + 7, selected ? 0xFFFFE29A : 0xFFFFFFFF, true);
            graphics.text(this.font, DETAILS[i], x + 30, y + 21, 0xFFB5B2C0, false);
            if (selected) {
                graphics.outline(x + 1, y + 1, cardWidth - 2, cardHeight - 2, 0xFFFFD36E);
                graphics.text(this.font, "EQUIPPED", x + 30, y + 36, 0xFFFFD36E, true);
            } else {
                graphics.text(this.font, "CLICK TO EQUIP", x + 30, y + 36, 0xFF8F8B9B, false);
            }
        }

        graphics.text(this.font, "Selection is saved on this computer only.", panelX + 14, panelY + 285, 0xFF92909E, false);
    }

    @Override
    public void onClose() {
        this.minecraft.gui.setScreen(null);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

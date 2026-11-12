package com.voidcosmetics.client.screen;

import com.voidcosmetics.client.VoidCosmeticsClient;
import com.voidcosmetics.client.config.CosmeticType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public final class CosmeticMenuScreen extends Screen {
    private static final CosmeticType[] ITEMS = {
            CosmeticType.DEMONIC_WINGS, CosmeticType.ANGEL_WINGS,
            CosmeticType.OBSIDIAN_HORNS, CosmeticType.SUNLIT_HALO,
            CosmeticType.VOID_CROWN, CosmeticType.RAVEN_MASK,
            CosmeticType.DRAGON_TAIL, CosmeticType.SPECTRAL_SWORDS
    };
    private static final String[] NAME_KEYS = {
            "cosmetic.voidcosmetics.demonic_wings", "cosmetic.voidcosmetics.angel_wings",
            "cosmetic.voidcosmetics.obsidian_horns", "cosmetic.voidcosmetics.sunlit_halo",
            "cosmetic.voidcosmetics.void_crown", "cosmetic.voidcosmetics.raven_mask",
            "cosmetic.voidcosmetics.dragon_tail", "cosmetic.voidcosmetics.spectral_swords"
    };
    private static final String[] DETAIL_KEYS = {
            "cosmetic.voidcosmetics.demonic_wings.detail", "cosmetic.voidcosmetics.angel_wings.detail",
            "cosmetic.voidcosmetics.obsidian_horns.detail", "cosmetic.voidcosmetics.sunlit_halo.detail",
            "cosmetic.voidcosmetics.void_crown.detail", "cosmetic.voidcosmetics.raven_mask.detail",
            "cosmetic.voidcosmetics.dragon_tail.detail", "cosmetic.voidcosmetics.spectral_swords.detail"
    };
    private static final int[] COLORS = {
            0xFF9D3B48, 0xFFFFD875, 0xFF8A8A9B, 0xFFFFE9A3,
            0xFF9B70E8, 0xFF68718F, 0xFF4EAA82, 0xFF9C83D8
    };

    private int panelX, panelY, panelWidth, cardWidth, startX, startY;

    public CosmeticMenuScreen() {
        super(Component.translatable("screen.voidcosmetics.title"));
    }

    private Component tr(String key) { return Component.translatable(key); }

    @Override
    protected void init() {
        panelWidth = Math.min(430, this.width - 24);
        panelX = (this.width - panelWidth) / 2;
        panelY = Math.max(8, (this.height - 372) / 2);
        cardWidth = (panelWidth - 44 - 8) / 2;
        startX = panelX + 14;
        startY = panelY + 70;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor g, int mouseX, int mouseY, float delta) {
        g.fill(0, 0, this.width, this.height, 0xA9000000);
        g.fill(panelX, panelY, panelX + panelWidth, panelY + 372, 0xFF171923);
        g.outline(panelX, panelY, panelWidth, 372, 0xFF55516D);
        g.fill(panelX + 1, panelY + 1, panelX + panelWidth - 1, panelY + 4, 0xFF9A72D8);
        g.text(this.font, tr("screen.voidcosmetics.title"), panelX + 14, panelY + 12, 0xFFFFFFFF, true);
        g.text(this.font, tr("screen.voidcosmetics.tagline"), panelX + 15, panelY + 29, 0xFFB8A9D6, false);
        Component localOnly = tr("screen.voidcosmetics.local_only");
        g.text(this.font, localOnly, panelX + panelWidth - this.font.width(localOnly) - 14, panelY + 15, 0xFF83D6A4, true);
        g.fill(panelX + 14, panelY + 52, panelX + panelWidth - 14, panelY + 53, 0xFF454052);

        for (int i = 0; i < ITEMS.length; i++) {
            int col = i % 2, row = i / 2;
            int x = startX + col * (cardWidth + 8);
            int y = startY + row * 58;
            boolean selected = VoidCosmeticsClient.CONFIG.getSelected() == ITEMS[i];
            boolean hovered = mouseX >= x && mouseX <= x + cardWidth && mouseY >= y && mouseY <= y + 51;
            int bg = selected ? 0xFF393044 : hovered ? 0xFF30313F : 0xFF242633;
            g.fill(x, y, x + cardWidth, y + 51, bg);
            g.outline(x, y, cardWidth, 51, selected ? 0xFFFFD36E : 0xFF484858);

            // Tiny pixel-art style emblem, deliberately built from crisp squares.
            g.fill(x + 9, y + 10, x + 29, y + 30, 0xFF11131B);
            g.fill(x + 13, y + 7, x + 25, y + 33, COLORS[i]);
            g.fill(x + 7, y + 13, x + 31, y + 25, COLORS[i]);
            g.fill(x + 15, y + 12, x + 23, y + 28, 0xFF242633);
            g.text(this.font, tr(NAME_KEYS[i]), x + 37, y + 8, selected ? 0xFFFFE29A : 0xFFFFFFFF, true);
            g.text(this.font, tr(DETAIL_KEYS[i]), x + 37, y + 22, 0xFFB5B2C0, false);
            g.text(this.font, tr(selected ? "screen.voidcosmetics.equipped" : "screen.voidcosmetics.click_to_equip"),
                    x + 37, y + 36, selected ? 0xFFFFD36E : 0xFF92909E, true);
        }

        g.text(this.font, tr("screen.voidcosmetics.local_save"), panelX + 14, panelY + 310, 0xFF92909E, false);
        int buttonY = panelY + 338;
        drawButton(g, panelX + 14, buttonY, 124, 20, tr("screen.voidcosmetics.unequip_all"), mouseX, mouseY);
        drawButton(g, panelX + panelWidth - 94, buttonY, 80, 20, tr("screen.voidcosmetics.done"), mouseX, mouseY);
        Component esc = tr("screen.voidcosmetics.press_esc");
        g.text(this.font, esc, panelX + (panelWidth - this.font.width(esc)) / 2, buttonY + 6, 0xFF777482, false);
    }

    private void drawButton(GuiGraphicsExtractor g, int x, int y, int w, int h, Component label, int mx, int my) {
        boolean hovered = mx >= x && mx <= x + w && my >= y && my <= y + h;
        g.fill(x, y, x + w, y + h, hovered ? 0xFF66518A : 0xFF393545);
        g.outline(x, y, w, h, hovered ? 0xFFD9B8FF : 0xFF777184);
        int textX = x + (w - this.font.width(label)) / 2;
        g.text(this.font, label, textX, y + 6, 0xFFFFFFFF, true);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (event.button() != 0) return super.mouseClicked(event, doubleClick);
        int mx = (int) event.x(), my = (int) event.y();
        int buttonY = panelY + 338;
        if (inside(mx, my, panelX + 14, buttonY, 124, 20)) {
            VoidCosmeticsClient.CONFIG.setSelected(CosmeticType.NONE);
            return true;
        }
        if (inside(mx, my, panelX + panelWidth - 94, buttonY, 80, 20)) {
            onClose();
            return true;
        }
        for (int i = 0; i < ITEMS.length; i++) {
            int x = startX + (i % 2) * (cardWidth + 8);
            int y = startY + (i / 2) * 58;
            if (inside(mx, my, x, y, cardWidth, 51)) {
                VoidCosmeticsClient.CONFIG.setSelected(ITEMS[i]);
                return true;
            }
        }
        return super.mouseClicked(event, doubleClick);
    }

    private boolean inside(int mx, int my, int x, int y, int w, int h) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
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

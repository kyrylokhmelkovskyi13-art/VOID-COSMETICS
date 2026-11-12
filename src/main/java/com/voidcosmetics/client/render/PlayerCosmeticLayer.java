package com.voidcosmetics.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.voidcosmetics.client.VoidCosmeticsClient;
import com.voidcosmetics.client.config.CosmeticType;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;

/**
 * Player-attached cosmetic render hook for Minecraft 26.2.
 * Geometry submission is implemented separately; this class intentionally
 * keeps the player render pipeline integration isolated and version-specific.
 */
public final class PlayerCosmeticLayer extends RenderLayer<AvatarRenderState, PlayerModel> {
    public PlayerCosmeticLayer(RenderLayerParent<AvatarRenderState, PlayerModel> parent) {
        super(parent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int light,
                       AvatarRenderState state, float limbAngle, float limbDistance) {
        CosmeticType selected = VoidCosmeticsClient.CONFIG.getSelected();
        if (selected == CosmeticType.NONE) {
            return;
        }

        // Cosmetic geometry will be submitted here using Minecraft 26.2's
        // SubmitNodeCollector pipeline. No legacy immediate-mode rendering.
    }
}

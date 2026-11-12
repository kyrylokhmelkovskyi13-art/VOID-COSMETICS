package com.voidcosmetics.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.voidcosmetics.client.VoidCosmeticsClient;
import com.voidcosmetics.client.config.CosmeticType;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;

/**
 * Local-only cosmetic geometry submitted through Minecraft 26.2's render queue.
 */
public final class PlayerCosmeticLayer extends RenderLayer<AvatarRenderState, PlayerModel> {
    public PlayerCosmeticLayer(RenderLayerParent<AvatarRenderState, PlayerModel> parent) {
        super(parent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int light,
                       AvatarRenderState state, float limbAngle, float limbDistance) {
        CosmeticType selected = VoidCosmeticsClient.CONFIG.getSelected();
        if (selected != CosmeticType.DEMONIC_WINGS && selected != CosmeticType.ANGEL_WINGS) {
            return;
        }

        boolean angelic = selected == CosmeticType.ANGEL_WINGS;
        int primary = angelic ? 0xFFFFD86B : 0xFF6B173F;
        int secondary = angelic ? 0xFFFFF2C2 : 0xFFB52C52;
        int edge = angelic ? 0xFFFFFFFF : 0xFF241027;

        poseStack.pushPose();
        // Attach the wings behind the upper torso. Coordinates are player-model units.
        poseStack.translate(0.0F, 1.38F, 0.16F);
        nodeCollector.submitCustomGeometry(poseStack, RenderTypes.debugFilledBox(), (pose, vertices) -> {
            // Each wing is built from crisp cuboid feathers, not a smooth mesh.
            wing(vertices, pose, -1, primary, secondary, edge);
            wing(vertices, pose, 1, primary, secondary, edge);
        });
        poseStack.popPose();
    }

    private static void wing(VertexConsumer out, PoseStack.Pose pose, int side,
                             int primary, int secondary, int edge) {
        float s = side;
        // Shoulder plate and three stepped feathers, mirrored for the opposite side.
        cuboid(out, pose, s * 0.12F, -0.12F, 0.00F, s * 0.34F, 0.18F, 0.10F, edge);
        cuboid(out, pose, s * 0.28F, 0.02F, 0.015F, s * 0.56F, 0.28F, 0.095F, primary);
        cuboid(out, pose, s * 0.43F, -0.12F, 0.02F, s * 0.72F, 0.12F, 0.09F, secondary);
        cuboid(out, pose, s * 0.58F, -0.27F, 0.025F, s * 0.91F, -0.02F, 0.085F, primary);
        cuboid(out, pose, s * 0.69F, -0.40F, 0.03F, s * 0.98F, -0.18F, 0.08F, secondary);
    }

    private static void cuboid(VertexConsumer out, PoseStack.Pose pose,
                               float x1, float y1, float z1,
                               float x2, float y2, float z2, int color) {
        // Six faces, each emitted as two triangles for the filled-box render type.
        face(out, pose, color, x1,y1,z1, x1,y2,z1, x2,y2,z1, x2,y1,z1);
        face(out, pose, color, x1,y1,z2, x2,y1,z2, x2,y2,z2, x1,y2,z2);
        face(out, pose, color, x1,y1,z1, x1,y1,z2, x1,y2,z2, x1,y2,z1);
        face(out, pose, color, x2,y1,z1, x2,y2,z1, x2,y2,z2, x2,y1,z2);
        face(out, pose, color, x1,y2,z1, x1,y2,z2, x2,y2,z2, x2,y2,z1);
        face(out, pose, color, x1,y1,z1, x2,y1,z1, x2,y1,z2, x1,y1,z2);
    }

    private static void face(VertexConsumer out, PoseStack.Pose pose, int color,
                             float ax, float ay, float az,
                             float bx, float by, float bz,
                             float cx, float cy, float cz,
                             float dx, float dy, float dz) {
        vertex(out, pose, color, ax, ay, az);
        vertex(out, pose, color, bx, by, bz);
        vertex(out, pose, color, cx, cy, cz);
        vertex(out, pose, color, ax, ay, az);
        vertex(out, pose, color, cx, cy, cz);
        vertex(out, pose, color, dx, dy, dz);
    }

    private static void vertex(VertexConsumer out, PoseStack.Pose pose, int color,
                               float x, float y, float z) {
        out.addVertex(pose, x, y, z).setColor(color);
    }
}

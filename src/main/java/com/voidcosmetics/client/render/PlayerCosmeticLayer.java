package com.voidcosmetics.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.voidcosmetics.client.VoidCosmeticsClient;
import com.voidcosmetics.client.config.CosmeticType;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;

/** Local-only blocky accessories attached to animated player model parts. */
public final class PlayerCosmeticLayer extends RenderLayer<AvatarRenderState, PlayerModel> {
    public PlayerCosmeticLayer(RenderLayerParent<AvatarRenderState, PlayerModel> parent) { super(parent); }

    @Override
    public void submit(PoseStack stack, SubmitNodeCollector collector, int light,
                       AvatarRenderState state, float limbAngle, float limbDistance) {
        CosmeticType selected = VoidCosmeticsClient.CONFIG.getSelected();
        if (selected == null || selected == CosmeticType.NONE) return;

        PlayerModel model = getParentModel();
        switch (selected) {
            case DEMONIC_WINGS -> wings(stack, collector, model.body, false);
            case ANGEL_WINGS -> wings(stack, collector, model.body, true);
            case OBSIDIAN_HORNS -> horns(stack, collector, model.head);
            case SUNLIT_HALO -> halo(stack, collector, model.head);
            case VOID_CROWN -> crown(stack, collector, model.head);
            case RAVEN_MASK -> mask(stack, collector, model.head);
            case DRAGON_TAIL -> tail(stack, collector, model.body);
            case SPECTRAL_SWORDS -> swords(stack, collector, model.body);
            default -> { }
        }
    }

    private static void draw(PoseStack stack, SubmitNodeCollector collector, ModelPart anchor, Shape shape) {
        stack.pushPose();
        anchor.translateAndRotate(stack);
        collector.submitCustomGeometry(stack, RenderTypes.debugFilledBox(), (pose, vertex) -> shape.draw(vertex, pose));
        stack.popPose();
    }

    @FunctionalInterface private interface Shape { void draw(VertexConsumer vertex, PoseStack.Pose pose); }

    // Coordinates below are block units relative to the selected model part.
    // Head local origin is at the neck; negative Y points upward and negative Z faces forward.
    private static void wings(PoseStack s, SubmitNodeCollector c, ModelPart body, boolean angel) {
        int base=angel?0xFFFFE7A0:0xFF4A1734;
        int a=angel?0xFFFFD86B:0xFF6B173F;
        int b=angel?0xFFFFF2C2:0xFFB52C52;
        int edge=angel?0xFFFFFFFF:0xFF241027;
        draw(s,c,body,(v,p)->{
            // A shared back plate keeps both wings visibly attached to the torso.
            box(v,p,-.24F,-.16F,.10F,.24F,.12F,.23F,base);
            for(int side:new int[]{-1,1}) {
                // Overlapping stepped feathers form one continuous wing silhouette.
                box(v,p,side*.10F,-.12F,.12F,side*.34F,.12F,.24F,edge);
                box(v,p,side*.22F,-.20F,.13F,side*.52F,.08F,.235F,a);
                box(v,p,side*.36F,-.30F,.14F,side*.68F,.00F,.23F,b);
                box(v,p,side*.50F,-.40F,.145F,side*.82F,-.10F,.225F,a);
                box(v,p,side*.62F,-.47F,.15F,side*.91F,-.20F,.22F,b);
                // Lower feather overlaps the middle section instead of floating below it.
                box(v,p,side*.36F,-.26F,.145F,side*.70F,-.08F,.235F,edge);
            }
        });
    }

    private static void horns(PoseStack s,SubmitNodeCollector c,ModelPart head) {
        draw(s,c,head,(v,p)->{for(int side:new int[]{-1,1}){float x=side;
            box(v,p,x*.13F,-.03F,-.08F,x*.28F,.10F,.08F,0xFF21172D);
            box(v,p,x*.20F,-.19F,-.07F,x*.34F,-.01F,.07F,0xFF4A294E);
            box(v,p,x*.29F,-.33F,-.06F,x*.39F,-.16F,.055F,0xFF21172D);
            box(v,p,x*.34F,-.45F,-.05F,x*.40F,-.28F,.045F,0xFF9A6BC2);
        }});
    }

    private static void halo(PoseStack s,SubmitNodeCollector c,ModelPart head) {
        draw(s,c,head,(v,p)->{int g=0xFFFFD45C,h=0xFFFFF2B0;
            box(v,p,-.28F,-.68F,-.035F,-.18F,-.625F,.035F,g);
            box(v,p,-.18F,-.645F,-.035F,.18F,-.59F,.035F,h);
            box(v,p,.18F,-.68F,-.035F,.28F,-.625F,.035F,g);
            box(v,p,-.28F,-.695F,-.035F,.28F,-.655F,.035F,g);
        });
    }

    private static void crown(PoseStack s,SubmitNodeCollector c,ModelPart head) {
        draw(s,c,head,(v,p)->{int d=0xFF24152F,q=0xFF673A87;
            box(v,p,-.30F,-.57F,-.16F,.30F,-.49F,.16F,d);
            box(v,p,-.27F,-.49F,-.13F,.27F,-.42F,.13F,q);
            box(v,p,-.25F,-.70F,-.12F,-.17F,-.49F,.02F,d);
            box(v,p,-.06F,-.76F,-.12F,.06F,-.49F,.02F,d);
            box(v,p,.17F,-.70F,-.12F,.25F,-.49F,.02F,d);
            box(v,p,-.045F,-.66F,-.145F,.045F,-.58F,-.11F,0xFFB56BFF);
        });
    }

    private static void mask(PoseStack s,SubmitNodeCollector c,ModelPart head) {
        draw(s,c,head,(v,p)->{int k=0xFF17151D,f=0xFF34303F,e=0xFFB63C55;
            box(v,p,-.25F,-.30F,-.53F,.25F,-.07F,-.48F,k);
            box(v,p,-.22F,-.12F,-.54F,-.03F,-.06F,-.47F,f);
            box(v,p,.03F,-.12F,-.54F,.22F,-.06F,-.47F,f);
            box(v,p,-.18F,-.235F,-.55F,-.07F,-.195F,-.525F,e);
            box(v,p,.07F,-.235F,-.55F,.18F,-.195F,-.525F,e);
            box(v,p,-.055F,-.39F,-.525F,.055F,-.28F,-.475F,k);
        });
    }

    private static void tail(PoseStack s,SubmitNodeCollector c,ModelPart body) {
        draw(s,c,body,(v,p)->{int d=0xFF35152F,r=0xFF8F294C;
            box(v,p,-.10F,.35F,.08F,.10F,.51F,.24F,d);
            box(v,p,-.08F,.48F,.21F,.08F,.64F,.38F,r);
            box(v,p,-.06F,.61F,.35F,.06F,.78F,.50F,d);
            box(v,p,-.04F,.74F,.46F,.04F,.91F,.62F,r);
            box(v,p,-.08F,.87F,.57F,.08F,.99F,.71F,d);
        });
    }

    private static void swords(PoseStack s,SubmitNodeCollector c,ModelPart body) {
        draw(s,c,body,(v,p)->{for(int side:new int[]{-1,1}){float x=side;
            box(v,p,x*.24F,-.18F,.20F,x*.32F,.19F,.27F,0xFF33234D);
            box(v,p,x*.18F,.17F,.21F,x*.38F,.24F,.26F,0xFFB17AFF);
            box(v,p,x*.25F,-.40F,.22F,x*.31F,.17F,.25F,0xFFBDEBFF);
            box(v,p,x*.22F,-.50F,.225F,x*.34F,-.39F,.245F,0xFFFFFFFF);
        }});
    }

    private static void box(VertexConsumer v,PoseStack.Pose p,float x1,float y1,float z1,
                            float x2,float y2,float z2,int color) {
        float minX=Math.min(x1,x2), maxX=Math.max(x1,x2);
        float minY=Math.min(y1,y2), maxY=Math.max(y1,y2);
        float minZ=Math.min(z1,z2), maxZ=Math.max(z1,z2);
        face(v,p,color,minX,minY,minZ,minX,maxY,minZ,maxX,maxY,minZ,maxX,minY,minZ);
        face(v,p,color,minX,minY,maxZ,maxX,minY,maxZ,maxX,maxY,maxZ,minX,maxY,maxZ);
        face(v,p,color,minX,minY,minZ,minX,minY,maxZ,minX,maxY,maxZ,minX,maxY,minZ);
        face(v,p,color,maxX,minY,minZ,maxX,maxY,minZ,maxX,maxY,maxZ,maxX,minY,maxZ);
        face(v,p,color,minX,maxY,minZ,minX,maxY,maxZ,maxX,maxY,maxZ,maxX,maxY,minZ);
        face(v,p,color,minX,minY,minZ,maxX,minY,minZ,maxX,minY,maxZ,minX,minY,maxZ);
    }

    private static void face(VertexConsumer v,PoseStack.Pose p,int color,
                             float ax,float ay,float az,float bx,float by,float bz,
                             float cx,float cy,float cz,float dx,float dy,float dz) {
        vertex(v,p,color,ax,ay,az); vertex(v,p,color,bx,by,bz); vertex(v,p,color,cx,cy,cz);
        vertex(v,p,color,ax,ay,az); vertex(v,p,color,cx,cy,cz); vertex(v,p,color,dx,dy,dz);
    }

    private static void vertex(VertexConsumer v,PoseStack.Pose p,int color,float x,float y,float z) {
        v.addVertex(p,x,y,z).setColor(color);
    }
}

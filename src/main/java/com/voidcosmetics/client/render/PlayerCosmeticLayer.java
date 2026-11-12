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

/** Local-only blocky accessories for Minecraft 26.2. */
public final class PlayerCosmeticLayer extends RenderLayer<AvatarRenderState, PlayerModel> {
    public PlayerCosmeticLayer(RenderLayerParent<AvatarRenderState, PlayerModel> parent) { super(parent); }

    @Override
    public void submit(PoseStack stack, SubmitNodeCollector collector, int light,
                       AvatarRenderState state, float limbAngle, float limbDistance) {
        CosmeticType selected = VoidCosmeticsClient.CONFIG.getSelected();
        if (selected == null || selected == CosmeticType.NONE) return;
        switch (selected) {
            case DEMONIC_WINGS -> wings(stack,collector,false);
            case ANGEL_WINGS -> wings(stack,collector,true);
            case OBSIDIAN_HORNS -> horns(stack,collector);
            case SUNLIT_HALO -> halo(stack,collector);
            case VOID_CROWN -> crown(stack,collector);
            case RAVEN_MASK -> mask(stack,collector);
            case DRAGON_TAIL -> tail(stack,collector);
            case SPECTRAL_SWORDS -> swords(stack,collector);
            default -> { }
        }
    }

    private static void draw(PoseStack s, SubmitNodeCollector c, float tx,float ty,float tz, Shape shape) {
        s.pushPose(); s.translate(tx,ty,tz);
        c.submitCustomGeometry(s,RenderTypes.debugFilledBox(),(pose,v)->shape.draw(v,pose));
        s.popPose();
    }
    @FunctionalInterface private interface Shape { void draw(VertexConsumer v,PoseStack.Pose p); }

    private static void wings(PoseStack s,SubmitNodeCollector c,boolean angel) {
        int a=angel?0xFFFFD86B:0xFF6B173F, b=angel?0xFFFFF2C2:0xFFB52C52, edge=angel?0xFFFFFFFF:0xFF241027;
        draw(s,c,0,1.38F,.16F,(v,p)->{for(int side:new int[]{-1,1}){float x=side;
            box(v,p,x*.12F,-.12F,0,x*.34F,.18F,.10F,edge);
            box(v,p,x*.28F,.02F,.015F,x*.56F,.28F,.095F,a);
            box(v,p,x*.43F,-.12F,.02F,x*.72F,.12F,.09F,b);
            box(v,p,x*.58F,-.27F,.025F,x*.91F,-.02F,.085F,a);
            box(v,p,x*.69F,-.40F,.03F,x*.98F,-.18F,.08F,b);
        }});
    }
    private static void horns(PoseStack s,SubmitNodeCollector c) {
        draw(s,c,0,1.72F,0,(v,p)->{for(int side:new int[]{-1,1}){float x=side;
            box(v,p,x*.13F,-.05F,-.08F,x*.28F,.08F,.08F,0xFF21172D);
            box(v,p,x*.20F,.04F,-.07F,x*.34F,.22F,.07F,0xFF4A294E);
            box(v,p,x*.29F,.17F,-.06F,x*.39F,.34F,.055F,0xFF21172D);
            box(v,p,x*.34F,.29F,-.05F,x*.40F,.43F,.045F,0xFF9A6BC2);
        }});
    }
    private static void halo(PoseStack s,SubmitNodeCollector c) {
        draw(s,c,0,1.98F,0,(v,p)->{int g=0xFFFFD45C,h=0xFFFFF2B0;
            box(v,p,-.28F,0,-.035F,-.18F,.055F,.035F,g);
            box(v,p,-.18F,.035F,-.035F,.18F,.09F,.035F,h);
            box(v,p,.18F,0,-.035F,.28F,.055F,.035F,g);
            box(v,p,-.28F,-.015F,-.035F,.28F,.025F,.035F,g);
        });
    }
    private static void crown(PoseStack s,SubmitNodeCollector c) {
        draw(s,c,0,1.78F,0,(v,p)->{int d=0xFF24152F,q=0xFF673A87;
            box(v,p,-.30F,-.06F,-.16F,.30F,.02F,.16F,d);
            box(v,p,-.27F,.02F,-.13F,.27F,.09F,.13F,q);
            box(v,p,-.25F,.09F,-.12F,-.17F,.23F,.02F,d);
            box(v,p,-.06F,.09F,-.12F,.06F,.29F,.02F,d);
            box(v,p,.17F,.09F,-.12F,.25F,.23F,.02F,d);
            box(v,p,-.045F,.12F,-.145F,.045F,.20F,-.11F,0xFFB56BFF);
        });
    }
    private static void mask(PoseStack s,SubmitNodeCollector c) {
        draw(s,c,0,1.53F,-.19F,(v,p)->{int k=0xFF17151D,f=0xFF34303F,e=0xFFB63C55;
            box(v,p,-.25F,-.10F,-.035F,.25F,.13F,.035F,k);
            box(v,p,-.22F,.06F,-.045F,-.03F,.12F,.045F,f);
            box(v,p,.03F,.06F,-.045F,.22F,.12F,.045F,f);
            box(v,p,-.18F,.005F,-.05F,-.07F,.045F,-.025F,e);
            box(v,p,.07F,.005F,-.05F,.18F,.045F,-.025F,e);
            box(v,p,-.055F,-.19F,-.025F,.055F,-.08F,.025F,k);
        });
    }
    private static void tail(PoseStack s,SubmitNodeCollector c) {
        draw(s,c,0,1.05F,.18F,(v,p)->{int d=0xFF35152F,r=0xFF8F294C;
            box(v,p,-.10F,-.08F,0,.10F,.08F,.16F,d);
            box(v,p,-.08F,-.20F,.13F,.08F,-.04F,.30F,r);
            box(v,p,-.06F,-.34F,.27F,.06F,-.17F,.42F,d);
            box(v,p,-.04F,-.47F,.39F,.04F,-.30F,.54F,r);
            box(v,p,-.08F,-.55F,.49F,.08F,-.43F,.63F,d);
        });
    }
    private static void swords(PoseStack s,SubmitNodeCollector c) {
        draw(s,c,0,1.20F,.28F,(v,p)->{for(int side:new int[]{-1,1}){float x=side;
            box(v,p,x*.24F,-.22F,-.035F,x*.32F,.15F,.035F,0xFF33234D);
            box(v,p,x*.18F,.13F,-.025F,x*.38F,.20F,.025F,0xFFB17AFF);
            box(v,p,x*.25F,.20F,-.018F,x*.31F,.62F,.018F,0xFFBDEBFF);
            box(v,p,x*.22F,.62F,-.012F,x*.34F,.72F,.012F,0xFFFFFFFF);
        }});
    }

    private static void box(VertexConsumer v,PoseStack.Pose p,float x1,float y1,float z1,
                            float x2,float y2,float z2,int color) {
        face(v,p,color,x1,y1,z1,x1,y2,z1,x2,y2,z1,x2,y1,z1);
        face(v,p,color,x1,y1,z2,x2,y1,z2,x2,y2,z2,x1,y2,z2);
        face(v,p,color,x1,y1,z1,x1,y1,z2,x1,y2,z2,x1,y2,z1);
        face(v,p,color,x2,y1,z1,x2,y2,z1,x2,y2,z2,x2,y1,z2);
        face(v,p,color,x1,y2,z1,x1,y2,z2,x2,y2,z2,x2,y2,z1);
        face(v,p,color,x1,y1,z1,x2,y1,z1,x2,y1,z2,x1,y1,z2);
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

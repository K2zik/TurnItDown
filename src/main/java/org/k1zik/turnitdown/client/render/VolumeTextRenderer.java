package org.k1zik.turnitdown.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import org.k1zik.turnitdown.ConfigJson;
import org.k1zik.turnitdown.client.event.VolumeScroller;

//? if >=1.21.6 {
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.DeltaTracker;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
//?} else if >=1.21 {
/*import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;*/
//?} else if >=1.20 {
/*import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;*/
//?} else if >=1.19 {
/*import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;*/
//?} else {
/*import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;*/
//?}

//? if >=26.1 {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?} else if >=1.21.6 {
/*import net.minecraft.client.gui.GuiGraphics;*/
//?}

public class VolumeTextRenderer {
    //? if >=1.21.6 {
    private static final Identifier HUD_ID = Identifier.fromNamespaceAndPath("turnitdown", "volume_message");
    //?}

    public static void register() {
        //? if >=26.1 {
        HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, HUD_ID, VolumeTextRenderer::extract);
        //?} else if >=1.21.6 {
        /*HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, HUD_ID, VolumeTextRenderer::renderHud);*/
        //?} else {
        /*HudRenderCallback.EVENT.register(VolumeTextRenderer::onHudRender);*/
        //?}
    }

    //? if >=26.1 {
    private static void extract(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        renderText((font, text, x, y, color) -> graphics.text(font, text, x, y, color, false));
    }
    //?} else if >=1.21.6 {
    /*private static void renderHud(GuiGraphics graphics, DeltaTracker deltaTracker) {
        renderText((font, text, x, y, color) -> graphics.drawString(font, text, x, y, color, false));
    }*/
    //?} else if >=1.21 {
    /*private static void onHudRender(GuiGraphics graphics, DeltaTracker deltaTracker) {
        renderText((font, text, x, y, color) -> graphics.drawString(font, text, x, y, color, false));
    }*/
    //?} else if >=1.20 {
    /*private static void onHudRender(GuiGraphics graphics, float tickDelta) {
        renderText((font, text, x, y, color) -> graphics.drawString(font, text, x, y, color, false));
    }*/
    //?} else if >=1.19 {
    /*private static void onHudRender(PoseStack poseStack, float tickDelta) {
        renderLegacy(poseStack);
    }*/
    //?} else {
    /*private static void onHudRender(PoseStack poseStack, float tickDelta) {
        renderLegacy(poseStack);
    }*/
    //?}

    //? if >=1.20 {
    @FunctionalInterface
    private interface TextDrawer {
        void draw(Font font, Component text, int x, int y, int color);
    }

    private static void renderText(TextDrawer drawer) {
        Minecraft client = Minecraft.getInstance();
        if (client.level == null || client.player == null) {
            return;
        }

        GameRenderer gameRenderer = client.gameRenderer;
        //? if >=26.2 {
        if (gameRenderer == null || !gameRenderer.mainCamera().isInitialized()) {
        //?} else {
        /*if (gameRenderer == null || !gameRenderer.getMainCamera().isInitialized()) {*/
        //?}
            return;
        }

        VolumeScroller.VolumeMessage latestMsg = latestMessage();
        if (latestMsg == null) {
            return;
        }

        Font font = client.font;
        int screenWidth = client.getWindow().getGuiScaledWidth();
        int screenHeight = client.getWindow().getGuiScaledHeight();
        int screenY = screenHeight - ConfigJson.getHudPositionY();

        Component labelText = Component.translatable("turnitdown.hud.label");
        Component percentText = Component.literal(latestMsg.text().getString());
        int labelWidth = font.width(labelText);
        int percentWidth = font.width(percentText);
        int startX = screenWidth / 2 - (labelWidth + percentWidth) / 2;

        drawer.draw(font, labelText, startX + 1, screenY, 0xAA000000);
        drawer.draw(font, percentText, startX + labelWidth + 1, screenY, 0xAA000000);
        drawer.draw(font, labelText, startX, screenY - 1, 0xFFFFAA00);
        drawer.draw(font, percentText, startX + labelWidth, screenY - 1, 0xFFFFFF00);
    }
    //?} else {
    /*private static void renderLegacy(PoseStack poseStack) {
        Minecraft client = Minecraft.getInstance();
        if (client.level == null || client.player == null) {
            return;
        }

        VolumeScroller.VolumeMessage latestMsg = latestMessage();
        if (latestMsg == null) {
            return;
        }

        Font font = client.font;
        int screenWidth = client.getWindow().getGuiScaledWidth();
        int screenHeight = client.getWindow().getGuiScaledHeight();
        int screenY = screenHeight - ConfigJson.getHudPositionY();

        //? if >=1.19 {
        Component labelText = Component.translatable("turnitdown.hud.label");
        Component percentText = Component.literal(latestMsg.text().getString());
        //?} else {
        TranslatableComponent labelText = new TranslatableComponent("turnitdown.hud.label");
        TextComponent percentText = new TextComponent(latestMsg.text().getString());
        //?}

        int labelWidth = font.width(labelText);
        int percentWidth = font.width(percentText);
        int startX = screenWidth / 2 - (labelWidth + percentWidth) / 2;

        font.draw(poseStack, labelText, startX + 1, screenY, 0xAA000000);
        font.draw(poseStack, percentText, startX + labelWidth + 1, screenY, 0xAA000000);
        font.draw(poseStack, labelText, startX, screenY - 1, 0xFFFFAA00);
        font.draw(poseStack, percentText, startX + labelWidth, screenY - 1, 0xFFFFFF00);
    }*/
    //?}

    private static VolumeScroller.VolumeMessage latestMessage() {
        VolumeScroller.VolumeMessage latestMsg = null;
        long latestTime = 0;
        for (VolumeScroller.VolumeMessage msg : VolumeScroller.getActiveMessages().values()) {
            if (msg.timestamp() > latestTime) {
                latestTime = msg.timestamp();
                latestMsg = msg;
            }
        }
        if (latestMsg == null) {
            return null;
        }
        if (System.currentTimeMillis() - latestMsg.timestamp() > 3000) {
            return null;
        }
        return latestMsg;
    }
}

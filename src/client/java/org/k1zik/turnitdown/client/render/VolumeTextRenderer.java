package org.k1zik.turnitdown.client.render;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.Camera;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.k1zik.turnitdown.ConfigJson;
import org.k1zik.turnitdown.client.event.VolumeScroller;

public class VolumeTextRenderer {

    public static void register() {
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.world == null || client.player == null) return;
            if (client.options.hudHidden) return;

            Camera camera = client.gameRenderer.getCamera();
            if (!camera.isReady()) return;

                VolumeScroller.VolumeMessage latestMsg = null;
                long latestTime = 0;
                for (var msg : VolumeScroller.getActiveMessages().values()) {
                    if (msg.timestamp() > latestTime) {
                        latestTime = msg.timestamp();
                        latestMsg = msg;
                    }
                }
                if (latestMsg == null) return;

                long age = System.currentTimeMillis() - latestMsg.timestamp();
                if (age > 3000) return;

                TextRenderer tr = client.textRenderer;
                int screenWidth = client.getWindow().getScaledWidth();
                int screenHeight = client.getWindow().getScaledHeight();

                int screenX = screenWidth / 2;
                int screenY = screenHeight - ConfigJson.getHudPositionY();

                String percent = latestMsg.text().getString();

                Text labelText = Text.translatable("turnitdown.hud.label");
                Text percentText = Text.literal(percent);

                int labelWidth = tr.getWidth(labelText);
                int percentWidth = tr.getWidth(percentText);
                int totalWidth = labelWidth + percentWidth;

                int labelColor = 0xFFFFAA00;
                int percentColor = 0xFFFFFF00;

                int startX = screenX - totalWidth / 2;

                int shadowColor = 0xAA000000;
                drawContext.drawText(tr, labelText, startX + 1, screenY, shadowColor, false);
                drawContext.drawText(tr, percentText, startX + labelWidth + 1, screenY, shadowColor, false);

                drawContext.drawText(tr, labelText, startX, screenY - 1, labelColor, false);
                drawContext.drawText(tr, percentText, startX + labelWidth, screenY - 1, percentColor, false);
        });
    }
}
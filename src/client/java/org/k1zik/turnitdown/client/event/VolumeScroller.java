package org.k1zik.turnitdown.client.event;

import org.k1zik.turnitdown.ConfigJson;

import de.maxhenkel.voicechat.VoicechatClient;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector2i;

import java.util.HashMap;
import java.util.Map;

public class VolumeScroller {
    private static final String MUSIC_DISC_CATEGORY = "music_discs";
    private static final Map<BlockPos, VolumeMessage> VOLUME_MESSAGES = new HashMap<>();

    private VolumeScroller() {
    }

    public static boolean handleScroll(Vector2i vector) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (!client.options.sneakKey.isPressed()) {
            return false;
        }

        if (client.getWindow() == null || client.isPaused()) {
            return false;
        }

        if (client.crosshairTarget == null || client.crosshairTarget.getType() != HitResult.Type.BLOCK) {
            return false;
        }

        BlockHitResult blockHit = (BlockHitResult) client.crosshairTarget;
        BlockPos blockPos = blockHit.getBlockPos();
        Block block = client.world.getBlockState(blockPos).getBlock();

        if (block != Blocks.JUKEBOX) {
            return false;
        }

        if (VoicechatClient.CATEGORY_VOLUME_CONFIG == null) {
            return false;
        }

        try {
            double prevVolume = VoicechatClient.CATEGORY_VOLUME_CONFIG.getVolume(MUSIC_DISC_CATEGORY, ConfigJson.getInitialVolume());

            double scrollDelta = (vector.y == 0 ? -vector.x : vector.y);
            double step = prevVolume >= 1.0 ? 0.1 : 0.05;
            double newVolume = MathHelper.clamp(prevVolume + step * scrollDelta, 0.0, 4.0);
            
            if (Math.abs(newVolume - prevVolume) > 0.001) {
                VoicechatClient.CATEGORY_VOLUME_CONFIG.setVolume(MUSIC_DISC_CATEGORY, newVolume);
                VoicechatClient.CATEGORY_VOLUME_CONFIG.save();

                int percent = (int) Math.round(100.0 * (newVolume - 1.0));
                String percentStr = percent >= 0 ? "+" + percent : String.valueOf(percent);
                VOLUME_MESSAGES.put(blockPos, new VolumeMessage(Text.literal(percentStr + "%")));

                return true;
            }
        } catch (Exception e) {
            System.err.println("[TurnItDown] Error changing volume: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }

    public static VolumeMessage getVolumeMessage(BlockPos pos) {
        VolumeMessage message = VOLUME_MESSAGES.get(pos);
        if (message != null && (System.currentTimeMillis() - message.timestamp) < 3000L) {
            return message;
        }
        if (message != null) {
            VOLUME_MESSAGES.remove(pos);
        }
        return null;
    }
    public static Map<BlockPos, VolumeMessage> getActiveMessages() {
        return VOLUME_MESSAGES;
    }
    public record VolumeMessage(Text text, long timestamp) {
        public VolumeMessage(Text text) {
            this(text, System.currentTimeMillis());
        }
        
        public Text text() {
            return text;
        }
    }
}

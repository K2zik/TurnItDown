package org.k1zik.turnitdown.client.event;

import de.maxhenkel.voicechat.VoicechatClient;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.joml.Vector2i;
import org.k1zik.turnitdown.ConfigJson;

//? if >=1.19 {
import net.minecraft.network.chat.Component;
//?} else {
/*import net.minecraft.network.chat.TextComponent;*/
//?}

import java.util.HashMap;
import java.util.Map;

public class VolumeScroller {
    private static final String MUSIC_DISC_CATEGORY = "music_discs";
    private static final Map<BlockPos, VolumeMessage> VOLUME_MESSAGES = new HashMap<>();

    private VolumeScroller() {
    }

    public static boolean handleScroll(Vector2i vector) {
        Minecraft client = Minecraft.getInstance();

        if (!client.options.keyShift.isDown()) {
            return false;
        }

        if (client.getWindow() == null || client.isPaused()) {
            return false;
        }

        if (client.hitResult == null || client.hitResult.getType() != HitResult.Type.BLOCK) {
            return false;
        }

        BlockHitResult blockHit = (BlockHitResult) client.hitResult;
        BlockPos blockPos = blockHit.getBlockPos();
        Block block = client.level.getBlockState(blockPos).getBlock();

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
            double newVolume = Mth.clamp(prevVolume + step * scrollDelta, 0.0, 4.0);

            if (Math.abs(newVolume - prevVolume) > 0.001) {
                VoicechatClient.CATEGORY_VOLUME_CONFIG.setVolume(MUSIC_DISC_CATEGORY, newVolume);
                VoicechatClient.CATEGORY_VOLUME_CONFIG.save();

                int percent = (int) Math.round(100.0 * (newVolume - 1.0));
                String percentStr = percent >= 0 ? "+" + percent : String.valueOf(percent);
                //? if >=1.19 {
                VOLUME_MESSAGES.put(blockPos, new VolumeMessage(Component.literal(percentStr + "%")));
                //?} else {
                /*VOLUME_MESSAGES.put(blockPos, new VolumeMessage(new TextComponent(percentStr + "%")));*/
                //?}

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

    //? if >=1.19 {
    public record VolumeMessage(Component text, long timestamp) {
        public VolumeMessage(Component text) {
            this(text, System.currentTimeMillis());
        }
    }
    //?} else {
    /*public static final class VolumeMessage {
        private final TextComponent text;
        private final long timestamp;

        public VolumeMessage(TextComponent text) {
            this.text = text;
            this.timestamp = System.currentTimeMillis();
        }

        public TextComponent text() {
            return text;
        }

        public long timestamp() {
            return timestamp;
        }
    }*/
    //?}
}

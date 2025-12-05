package org.k1zik.turnitdown.client;

import de.maxhenkel.voicechat.VoicechatClient;
import net.fabricmc.api.ClientModInitializer;
import org.k1zik.turnitdown.client.render.VolumeTextRenderer;
public class TurnitdownClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        VolumeTextRenderer.register();
        System.out.println("[TurnItDown] mod initialized");
    }
}

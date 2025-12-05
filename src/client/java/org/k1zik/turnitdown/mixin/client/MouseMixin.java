package org.k1zik.turnitdown.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Mouse;
import org.joml.Vector2i;
import org.k1zik.turnitdown.client.event.VolumeScroller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public abstract class MouseMixin {
    
    @Inject(
        method = "onMouseScroll",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/network/ClientPlayerEntity;isSpectator()Z"
        ),
        cancellable = true
    )
    private void handleMouseScroll(CallbackInfo ci, @Local Vector2i vector) {
        if (VolumeScroller.handleScroll(vector)) {
            ci.cancel();
        }
    }
}

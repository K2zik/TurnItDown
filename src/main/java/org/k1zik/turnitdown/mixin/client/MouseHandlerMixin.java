package org.k1zik.turnitdown.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MouseHandler;
import org.joml.Vector2i;
import org.k1zik.turnitdown.client.event.VolumeScroller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {

    //? if >=1.20.2 {
    @Inject(
        method = "onScroll",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/LocalPlayer;isSpectator()Z"
        ),
        cancellable = true
    )
    private void handleMouseScroll(CallbackInfo ci, @Local Vector2i wheelXY) {
        if (VolumeScroller.handleScroll(wheelXY)) {
            ci.cancel();
        }
    }
    //?} else if >=1.19 {
    /*@Inject(
        method = "onScroll",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/LocalPlayer;isSpectator()Z"
        ),
        cancellable = true
    )
    private void handleMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci, @Local int scrollY) {
        if (VolumeScroller.handleScroll(new Vector2i(0, scrollY))) {
            ci.cancel();
        }
    }*/
    //?} else if >=1.17 {
    /*@Inject(
        method = "onScroll",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/LocalPlayer;isSpectator()Z"
        ),
        cancellable = true
    )
    private void handleMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        int scroll = (int) Math.signum(vertical);
        if (scroll != 0 && VolumeScroller.handleScroll(new Vector2i(0, scroll))) {
            ci.cancel();
        }
    }*/
    //?} else {
    /*@Inject(
        method = "onScroll",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/LocalPlayer;isSpectator()Z"
        ),
        cancellable = true
    )
    private void handleMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        int scroll = (int) Math.signum(vertical);
        if (scroll != 0 && VolumeScroller.handleScroll(new Vector2i(0, scroll))) {
            ci.cancel();
        }
    }*/
    //?}
}

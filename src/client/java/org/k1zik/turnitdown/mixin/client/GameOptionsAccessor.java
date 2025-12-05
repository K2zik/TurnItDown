package org.k1zik.turnitdown.mixin.client;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(GameOptions.class)
public interface GameOptionsAccessor {
    @Accessor("soundVolumeLevels")
    Map<SoundCategory, SimpleOption<Double>> getSoundVolumeLevels();
}


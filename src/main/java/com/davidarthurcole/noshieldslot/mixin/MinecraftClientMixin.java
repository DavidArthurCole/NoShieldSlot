package com.davidarthurcole.noshieldslot.mixin;

import com.davidarthurcole.noshieldslot.NoShieldSlotMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Redirect(
        method = "handleInputEvents",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/option/KeyBinding;wasPressed()Z"
        )
    )
    private boolean redirectWasPressed(KeyBinding keyBinding) {
        // Prevent the shield slot from being toggled by the key binding
        if (!NoShieldSlotMod.CONFIG.getDisableKeybind() || !keyBinding.getTranslationKey().equals("key.swapOffhand")) {
            return keyBinding.wasPressed();
        }
        return false;
    }

}

package com.davidarthurcole.noshieldslot.mixin;

import com.davidarthurcole.noshieldslot.NoShieldSlotMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyBinding.class)
public abstract class KeyBindingMixin {
    @Inject(
        method = "getBoundKeyLocalizedText()Lnet/minecraft/text/Text;",
        at = @At("HEAD"),
        cancellable = true
    )
    private void onGetBoundKeyLocalizedText(CallbackInfoReturnable<Text> cir) {
        KeyBinding self = (KeyBinding)(Object)this;
        if (NoShieldSlotMod.CONFIG.getDisableKeybind() && self == MinecraftClient.getInstance().options.swapHandsKey) {
            cir.setReturnValue(Text.literal("—").formatted(Formatting.GRAY));
        }
    }
}
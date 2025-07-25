package com.davidarthurcole.noshieldslot.mixin;

import com.davidarthurcole.noshieldslot.NoShieldSlotMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.option.ControlsListWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ControlsListWidget.KeyBindingEntry.class)
public abstract class KeyBindingEntryMixin extends ControlsListWidget.Entry {
    @Final
    @Shadow
    private KeyBinding binding;
    @Final
    @Shadow
    private ButtonWidget editButton;
    @Final
    @Shadow
    private ButtonWidget resetButton;

    @Inject(method = "update()V", at = @At("HEAD"))
    private void onUpdateDisableButtons(CallbackInfo ci) {
        if (NoShieldSlotMod.CONFIG.getEnabled() && NoShieldSlotMod.CONFIG.getDisableKeybind() && binding == MinecraftClient.getInstance().options.swapHandsKey) {
            editButton.active = false;
            resetButton.active = false;
        }
    }

    @ModifyArg(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)I"
        ),
        index = 1
    )
    private Text onDrawKeyName(Text original) {
        if (NoShieldSlotMod.CONFIG.getEnabled() && NoShieldSlotMod.CONFIG.getDisableKeybind() && binding == MinecraftClient.getInstance().options.swapHandsKey) {
            return Text.literal(original.getString()).formatted(Formatting.GRAY, Formatting.STRIKETHROUGH);
        }
        return original;
    }
}

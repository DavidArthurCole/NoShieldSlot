package com.davidarthurcole.noshieldslot.mixin;

import com.davidarthurcole.noshieldslot.NoShieldSlotMod;
import com.davidarthurcole.noshieldslot.hooks.GuiContainerHook;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Partially adapted from SkyHanni's MixinGuiContainer implementation
// https://github.com/hannibal002/SkyHanni/blob/beta/src/main/java/at/hannibal2/skyhanni/mixins/transformers/gui/MixinGuiContainer.java
@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin<T extends ScreenHandler> extends Screen {

    protected HandledScreenMixin(Text title) {
        super(title);
    }

    @Unique
    private final GuiContainerHook nss$hook = new GuiContainerHook(this);

    @Redirect(method = "drawSlotHighlightBack", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;canBeHighlighted()Z"))
    private boolean canBeHighlightedBack(Slot slot) {
        if (slot.getIndex() != PlayerInventory.OFF_HAND_SLOT) return slot.canBeHighlighted();
        return !NoShieldSlotMod.CONFIG.getHideSlot();
    }

    @Redirect(method = "drawSlotHighlightFront", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;canBeHighlighted()Z"))
    private boolean canBeHighlightedFront(Slot slot) {
        if (slot.getIndex() != PlayerInventory.OFF_HAND_SLOT) return slot.canBeHighlighted();
        return !NoShieldSlotMod.CONFIG.getHideSlot();
    }

    @Redirect(
        method = "drawSlot",
        at = @At(
        value = "INVOKE",
            target = "Lnet/minecraft/screen/slot/Slot;getBackgroundSprite()Lnet/minecraft/util/Identifier;"
        )
    )
    private Identifier onGetBackgroundSprite(Slot slot) {
        if (NoShieldSlotMod.CONFIG.getHideSlot()
                && slot.inventory instanceof PlayerInventory
                && slot.getIndex() == PlayerInventory.OFF_HAND_SLOT) {
            return null;
        }
        return slot.getBackgroundSprite();
    }

    @Inject(
        method = "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo cir) {
        nss$hook.onMouseClick(slot, slotId, button, actionType.getIndex(), cir);
    }

    @Unique
    private static Boolean getKeybindResult(boolean vanillaResult) {
        return !NoShieldSlotMod.CONFIG.getDisableKeybind() && vanillaResult;
    }

    @Redirect(
        method = "onMouseClick(I)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/option/KeyBinding;matchesMouse(I)Z"
        )
    )
    private boolean onMouseClick(KeyBinding instance, int code) {
        assert this.client != null;
        return getKeybindResult(instance.matchesMouse(code));
    }

    @Redirect(
        method = "handleHotbarKeyPressed",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/option/KeyBinding;matchesKey(II)Z"
        )
    )
    private boolean onHotbarKeyPressed(KeyBinding instance, int keyCode, int scanCode) {
        assert this.client != null;
        return getKeybindResult(instance.matchesKey(keyCode, scanCode));
    }
}

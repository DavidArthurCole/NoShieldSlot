package com.davidarthurcole.noshieldslot.mixin;

import com.davidarthurcole.noshieldslot.NoShieldSlotMod;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HandledScreen.class)
abstract class HandledScreenMixin {

    @Redirect(method = "drawSlotHighlightBack", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;canBeHighlighted()Z"))
    private boolean canBeHighlightedBack(Slot slot) {
        return !NoShieldSlotMod.CONFIG.getEnabled();
    }

    @Redirect(method = "drawSlotHighlightFront", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;canBeHighlighted()Z"))
    private boolean canBeHighlightedFront(Slot slot) {
        return !NoShieldSlotMod.CONFIG.getEnabled();
    }

    @Redirect(
        method = "drawSlot",
        at = @At(
        value = "INVOKE",
            target = "Lnet/minecraft/screen/slot/Slot;getBackgroundSprite()Lnet/minecraft/util/Identifier;"
        )
    )
    private Identifier onGetBackgroundSprite(Slot slot) {
        if (NoShieldSlotMod.CONFIG.getEnabled()
                && slot.inventory instanceof PlayerInventory
                && slot.getIndex() == PlayerInventory.OFF_HAND_SLOT) {
            return null;
        }
        return slot.getBackgroundSprite();
    }
}

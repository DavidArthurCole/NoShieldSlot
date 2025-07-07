package com.davidarthurcole.noshieldslot.mixin;

import com.davidarthurcole.noshieldslot.NoShieldSlotMod;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ScreenHandler.class)
abstract class ScreenHandlerMixin {
    @Inject(
            method = "addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onAddSlot(Slot slot, CallbackInfoReturnable<Slot> cir) {
        if (!NoShieldSlotMod.CONFIG.getEnabled()) return;
        if (slot.inventory instanceof PlayerInventory && slot.getIndex() == 40) {
            cir.setReturnValue(slot);
            cir.cancel();
        }
    }

}

package com.davidarthurcole.noshieldslot.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public abstract class SlotMixin {
    @Final
    @Shadow
    private int index;
    @Final
    @Shadow
    public Inventory inventory;

    @Inject(
            method = "getBackgroundSprite()Lnet/minecraft/util/Identifier;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void hideOffhandBackground(CallbackInfoReturnable<Identifier> cir) {
        if (inventory instanceof PlayerInventory && index == 40) {
            cir.setReturnValue(null);
        }
    }
}

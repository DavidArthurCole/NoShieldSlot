package com.davidarthurcole.noshieldslot.mixin;

import com.davidarthurcole.noshieldslot.NoShieldSlotMod;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Function;

@Mixin(InventoryScreen.class)
abstract class InventoryScreenMixin {
    @Unique
    private static final Identifier NSS_BACKGROUND_TEXTURE = Identifier.of(
    "no-shield-slot",
    "textures/gui/container/inventory.png"
    );

    @ModifyArg(
        method = "drawBackground",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIFFIIII)V"
        ),
        index = 1
    )
    private Identifier swapInventoryBackground(Identifier original) {
        return NoShieldSlotMod.CONFIG.getEnabled() ? NSS_BACKGROUND_TEXTURE : original;
    }
}

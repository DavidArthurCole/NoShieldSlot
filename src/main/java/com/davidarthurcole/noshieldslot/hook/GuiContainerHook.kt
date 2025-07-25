package com.davidarthurcole.noshieldslot.hook

import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.Items
import net.minecraft.screen.PlayerScreenHandler
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

// Adapted from SkyHanni's implementation of GuiContainerHook
// https://github.com/hannibal002/SkyHanni/blob/beta/src/main/java/at/hannibal2/skyhanni/mixins/hooks/GuiContainerHook.kt
class GuiContainerHook(guiAny: Any) {

    private val gui: HandledScreen<*> = guiAny as HandledScreen<*>
    private val container: ScreenHandler get() = gui.screenHandler

    fun onMouseClick(slot: Slot?, slotId: Int, clickedButton: Int, clickType: Int, ci: CallbackInfo) {
        val item = container.stacks?.takeIf { it.size > slotId && slotId >= 0 }?.get(slotId)
        if (slot == null || container !is PlayerScreenHandler) return
        if (slot.index != PlayerInventory.OFF_HAND_SLOT) return
        if (item == null || item.item == Items.AIR) {
            ci.cancel()
        }
    }
}
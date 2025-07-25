package com.davidarthurcole.noshieldslot

import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer
import net.fabricmc.api.ModInitializer
import net.minecraft.util.ActionResult

class NoShieldSlotMod : ModInitializer {

    override fun onInitialize() {
        registerConfig()
    }

    companion object {
        @JvmStatic
        lateinit var CONFIG: NoShieldSlotConfig

        fun registerConfig() {
            val holder = AutoConfig.register(NoShieldSlotConfig::class.java, ::JanksonConfigSerializer)
            CONFIG = AutoConfig.getConfigHolder(NoShieldSlotConfig::class.java).config

            holder.registerSaveListener { _, newConfig ->
                CONFIG = newConfig
                ActionResult.SUCCESS
            }
        }
    }
}

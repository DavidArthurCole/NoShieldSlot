package com.davidarthurcole.noshieldslot

import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer
import net.fabricmc.api.ModInitializer

class NoShieldSlotMod : ModInitializer {

    override fun onInitialize() {
        registerConfig()
    }

    companion object {
        lateinit var CONFIG: NoShieldSlotConfig

        fun registerConfig() {
            AutoConfig.register(NoShieldSlotConfig::class.java, ::JanksonConfigSerializer)
            CONFIG = AutoConfig.getConfigHolder(NoShieldSlotConfig::class.java).config
        }
    }
}

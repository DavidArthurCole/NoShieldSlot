package com.davidarthurcole.noshieldslot

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry

@Config(name = "no-shield-slot")
class NoShieldSlotConfig : ConfigData {
    @ConfigEntry.Gui.Tooltip
    var enabled: Boolean = true

    @ConfigEntry.Gui.Tooltip
    var disableKeybind: Boolean = true
}
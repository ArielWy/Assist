package me.olios.plugins.assist.handlers

import me.olios.plugins.assist.utils.ConfigHandler
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import java.util.*

object ToggleManager {

    fun toggleTemporary(player: Player): Boolean {
        return if (StaffManager.getStaffPlayers().contains(player)) {
            StaffManager.removeTemporary(player)
            true // now disabled
        } else {
            StaffManager.addTemporary(player)
            false // now enabled
        }
    }

    fun togglePermanent(player: Player): Boolean {
        return if (ConfigHandler.isPermanentlyDisabled(player.name)) {
            ConfigHandler.removePermanentlyDisabled(player.name)
            StaffManager.addTemporary(player)
            false // now enabled
        } else {
            ConfigHandler.addPermanentlyDisabled(player.name)
            StaffManager.removeTemporary(player)
            true // now disabled
        }
    }

    fun isPermanentlyEnabled(player: Player): Boolean {
        return !ConfigHandler.isPermanentlyDisabled(player.name)
    }

    fun setTemporary(player: Player, enabled: Boolean) {
        if (enabled) StaffManager.addTemporary(player)
        else StaffManager.removeTemporary(player)
    }

    fun setPermanent(player: Player, enabled: Boolean) {
        val currentlyDisabled = ConfigHandler.isPermanentlyDisabled(player.name)
        if (enabled && currentlyDisabled) {
            ConfigHandler.removePermanentlyDisabled(player.name)
            StaffManager.addTemporary(player)
        } else if (!enabled && !currentlyDisabled) {
            ConfigHandler.addPermanentlyDisabled(player.name)
            StaffManager.removeTemporary(player)
        }
    }
}

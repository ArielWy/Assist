package me.olios.plugins.assist.handlers

import me.olios.plugins.assist.utils.ConfigHandler
import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object StaffManager : Listener {
    private val staffPlayers = mutableSetOf<Player>()

    fun init() {
        staffPlayers.clear()
        for (player in Bukkit.getOnlinePlayers()) {
            if (checkPerm(player)) {
                staffPlayers.add(player)
            }
        }
    }

    fun getStaffPlayers(): Set<Player> = staffPlayers

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        if (checkPerm(event.player)) {
            staffPlayers.add(event.player)
        }
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        staffPlayers.remove(event.player)
    }

    private fun checkPerm(player: Player): Boolean {
        if (!player.hasPermission("assist.notify") || ConfigHandler.isPermanentlyDisabled(player.name)) {
            staffPlayers.remove(player)
            return false
        }
        return true
    }

    fun addTemporary(player: Player) {
        if (player.hasPermission("assist.notify") && !ConfigHandler.isPermanentlyDisabled(player.name)) {
            staffPlayers.add(player)
        }
    }

    fun removeTemporary(player: Player) {
        staffPlayers.remove(player)
    }
}

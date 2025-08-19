package me.olios.plugins.assist.handlers

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object StaffManager : Listener {
    private val staffPlayers = mutableSetOf<Player>()

    fun init() {
        // On plugin enable, preload staff list
        for (player in Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("assist.notify")) {
                staffPlayers.add(player)
            }
        }
    }

    fun getStaffPlayers(): Set<Player> = staffPlayers

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        if (player.hasPermission("assist.notify")) {
            staffPlayers.add(player)
        }
        Bukkit.getLogger().info(staffPlayers.joinToString(", "))
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        staffPlayers.remove(event.player)

        Bukkit.getLogger().info(staffPlayers.joinToString(", "))
    }

    fun refreshPermissions() {
        // Call this if you ever suspect permission changes mid-session
        staffPlayers.clear()
        for (player in Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("assist.notify")) {
                staffPlayers.add(player)
            }
        }
    }

    fun checkPerm(player: Player): Boolean {
        if (!player.hasPermission("assist.notify")) {
            staffPlayers.remove(player)
            return false
        }
        return true
    }
}
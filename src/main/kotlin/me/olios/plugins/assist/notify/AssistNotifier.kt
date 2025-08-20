package me.olios.plugins.assist.notify

import me.olios.plugins.assist.handlers.StaffManager
import me.olios.plugins.assist.models.AssistRequest
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import java.util.*

object AssistNotifier {

    fun notifyStaff(request: AssistRequest) {
        for (player in StaffManager.getStaffPlayers()) {
            playSound(player)
            sendChatMessage(player, request)
            sendTitle(player, request)
        }
    }

    fun updatePersistentStatus(pendingRequests: List<AssistRequest>) {
        for (player in StaffManager.getStaffPlayers()) {
            if (pendingRequests.isEmpty()) {
                player.sendActionBar("") // Clear the ActionBar
                continue
            }

            val subtitle = if (pendingRequests.size == 1) {
                "§e${pendingRequests[0].requesterName} needs assist"
            } else {
                "§e${pendingRequests.size} players need assist"
            }

            player.sendActionBar(subtitle)
        }
    }

    fun notifyPlayerClaimed(playerId: UUID, staff: Player) {
        val player = Bukkit.getPlayer(playerId) ?: return
        player.sendMessage("§aYour assistance request has been claimed by §e${staff.name}§a. Please wait while they assist you.")
    }

    fun notifyStaffClaimed(staff: Player, request: AssistRequest) {
        staff.sendMessage("§aYou have successfully claimed the assistance request from §e${request.requesterName}§a.")
        staff.sendMessage("§7Type §e/assist help§7 to see the available actions (msg, tp, close, etc).")
    }

    fun notifyPlayerClosed(playerId: UUID, staffId: UUID, description: String) {
        val player = Bukkit.getPlayer(playerId)
        val staff = Bukkit.getPlayer(staffId)

        player?.sendMessage("§aYour assistance request has been closed by §e${staff?.name ?: "Staff"}§a.")
        player?.sendMessage("§7Reason: $description")
    }

    fun notifyStaffClosed(staffId: UUID, request: AssistRequest, description: String) {
        val staff = Bukkit.getPlayer(staffId)
        StaffManager.getStaffPlayers()
            .forEach { it.sendMessage("§e${staff?.name ?: "A staff"} §7closed an assist request from §e${request.requesterName} §7with reason: §f$description") }
    }

    fun notifyPlayerMessage(player: Player, staff: Player, message: String) {
        player.sendMessage("§6[Assist] §e${staff.name}: §f$message")
    }

    fun notifyStaffMessage(staff: Player, player: Player, message: String) {
        staff.sendMessage("§6[Assist → ${player.name}] §f$message")
    }


    private fun playSound(player: Player) {
        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
    }

    private fun sendChatMessage(player: Player, request: AssistRequest) {
        player.sendMessage("§e[Assist] §f${request.requesterName} §7needs help: §b${request.message}")
    }

    private fun sendTitle(player: Player, request: AssistRequest) {
        player.sendTitle(
            "§eAssist Request!",
            "§f${request.requesterName} needs help",
            10, 40, 10
        )
    }

}
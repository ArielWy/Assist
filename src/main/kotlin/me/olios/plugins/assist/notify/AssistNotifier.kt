package me.olios.plugins.assist.notify

import me.olios.plugins.assist.handlers.StaffManager
import me.olios.plugins.assist.models.AssistRequest
import org.bukkit.Sound
import org.bukkit.entity.Player

object AssistNotifier {

    fun notifyStaff(request: AssistRequest) {
        for (player in StaffManager.getStaffPlayers()) {
            playSound(player)
            sendChatMessage(player, request)
            sendTitle(player, request)
        }
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

    fun updatePersistentStatus(pendingRequests: List<AssistRequest>) {
        for (player in StaffManager.getStaffPlayers()) {
            if (pendingRequests.isEmpty()) {
                player.sendActionBar("") // Clear the ActionBar
                continue
            }

            val subtitle = if (pendingRequests.size == 1) {
                "${pendingRequests[0].requesterName} needs assist"
            } else {
                "${pendingRequests.size} players need assist"
            }

            val message = "§e$subtitle"

            player.sendActionBar(message)
        }
    }

}
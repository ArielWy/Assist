package me.olios.plugins.assist.services

import me.olios.plugins.assist.handlers.AssistHandler
import me.olios.plugins.assist.handlers.AssistRequestManager
import me.olios.plugins.assist.notify.AssistNotifier
import org.bukkit.Bukkit
import java.util.*

object MsgAction {
    fun sendMessage(staffId: UUID, message: String) {
        val request = AssistRequestManager.getClaimedRequest(staffId)
            ?: throw IllegalStateException("You don’t have an active assist request.")

        val staff = Bukkit.getPlayer(staffId)
            ?: throw IllegalStateException("Staff member not online.")

        val assistedPlayer = Bukkit.getPlayer(request.requesterName)
            ?: throw IllegalStateException("Assisted player not online.")

        // Delegate to notifier so all formatting & messaging is centralized
        AssistNotifier.notifyPlayerMessage(assistedPlayer, staff, message)
        AssistNotifier.notifyStaffMessage(staff, assistedPlayer, message)
    }
}

package me.olios.plugins.assist.services

import me.olios.plugins.assist.handlers.AssistHandler
import me.olios.plugins.assist.models.AssistRequest
import me.olios.plugins.assist.notify.AssistNotifier
import org.bukkit.entity.Player

object ClaimAction {
    fun execute(request: AssistRequest, staff: Player) {
        // ensure request is still pending
        if (request.handlerId != null) {
            staff.sendMessage("§cThis request is already claimed by someone else.")
            return
        }

        // mark as claimed
        AssistHandler.claimRequest(request, staff.uniqueId)

        // notify relevant parties
        AssistNotifier.notifyPlayerClaimed(request.requesterId, staff)
        AssistNotifier.notifyStaffClaimed(staff, request)

        // teleport staff to player
        // AssistTeleporter.teleportToPlayer(staff, request.player)
    }
}

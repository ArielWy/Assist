package me.olios.plugins.assist.handlers

import me.olios.plugins.assist.models.AssistRequest
import me.olios.plugins.assist.notify.AssistNotifier
import org.bukkit.entity.Player
import java.util.*

object AssistHandler {
    private val notifier = AssistNotifier

    fun handleRequest(player: Player, message: String) {
        val request = AssistRequest(player.uniqueId, player.name, message)

        // Add to the centralized manager
        AssistRequestManager.addRequest(request)

        // Notify staff once when the request is created
        notifier.notifyStaff(request)
    }

    fun claimRequest(request: AssistRequest, uuid: UUID) {
        request.handlerId = uuid
    }

}


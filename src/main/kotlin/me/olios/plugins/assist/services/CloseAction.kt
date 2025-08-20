package me.olios.plugins.assist.services

import me.olios.plugins.assist.handlers.AssistRequestManager
import me.olios.plugins.assist.models.AssistRequest
import me.olios.plugins.assist.notify.AssistNotifier
import java.util.*

object CloseAction {

    fun closeRequest(request: AssistRequest, staffId: UUID, description: String) {
        // Remove the request from manager
        AssistRequestManager.removeRequest(request)

        // Reset staff claim
        AssistRequestManager.removeRequest(request)

        // Notify player
        AssistNotifier.notifyPlayerClosed(request.requesterId, staffId, description)

        // Notify staff
        AssistNotifier.notifyStaffClosed(staffId, request, description)

        // (Optional) teleport staff back if you want
        // TeleportManager.teleportBack(staffId)

        // Disable assist-only chat if enabled
        // StaffChatManager.disableChatMode(staffId)
    }
}

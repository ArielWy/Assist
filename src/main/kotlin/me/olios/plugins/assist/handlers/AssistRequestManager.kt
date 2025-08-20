package me.olios.plugins.assist.handlers

import com.sun.net.httpserver.Request
import me.olios.plugins.assist.models.AssistRequest
import java.util.UUID

object AssistRequestManager {
    private val pendingRequests = mutableListOf<AssistRequest>()

    fun addRequest(request: AssistRequest) {
        pendingRequests.add(request)
    }

    fun removeRequest(request: AssistRequest) {
        pendingRequests.remove(request)
    }

    fun getPendingRequests(): List<AssistRequest> = pendingRequests.toList()

    fun count(): Int = pendingRequests.size

    fun getClaimedRequest(uuid: UUID): AssistRequest? {
        return pendingRequests.find { it.handlerId == uuid }
    }
}

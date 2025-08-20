package me.olios.plugins.assist.handlers

import me.olios.plugins.assist.models.AssistRequest
import java.util.UUID

object AssistRequestManager {
    private val allRequests = mutableListOf<AssistRequest>()

    fun addRequest(request: AssistRequest) {
        allRequests.add(request)
    }

    fun removeRequest(request: AssistRequest) {
        allRequests.remove(request)
    }

    fun getAllRequests(): List<AssistRequest> = allRequests

    fun getPendingRequests(): List<AssistRequest> = allRequests.filter { it.handlerId == null }

    fun getClaimedRequests(): List<AssistRequest> = allRequests.filter { it.handlerId != null }

    fun count(): Int = allRequests.size

    fun getClaimedRequest(uuid: UUID): AssistRequest? = allRequests.find { it.handlerId == uuid }
}

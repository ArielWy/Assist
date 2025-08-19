package me.olios.plugins.assist.handlers

import me.olios.plugins.assist.models.AssistRequest

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
}

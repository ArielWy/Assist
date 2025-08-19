package me.olios.plugins.assist.models

import java.util.*

data class AssistRequest(
    val requesterId: UUID,
    val requesterName: String,
    val message: String,
    var handlerId: UUID? = null,  // Staff who claimed it
    val timestamp: Long = System.currentTimeMillis()
)

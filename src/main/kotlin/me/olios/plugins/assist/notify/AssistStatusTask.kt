package me.olios.plugins.assist.notify

import me.olios.plugins.assist.Assist
import me.olios.plugins.assist.models.AssistRequest
import org.bukkit.scheduler.BukkitRunnable

class AssistStatusTask(
    private val plugin: Assist,
    private val pendingRequests: () -> List<AssistRequest>
) : BukkitRunnable() {
    override fun run() {
        val requests = pendingRequests()
        AssistNotifier.updatePersistentStatus(requests)
    }

    companion object {
        fun start(plugin: Assist, pendingRequests: () -> List<AssistRequest>) {
            AssistStatusTask(plugin, pendingRequests).runTaskTimer(plugin, 0L, 40L)
        }
    }
}

package me.olios.plugins.assist.notify

import me.olios.plugins.assist.handlers.StaffManager
import me.olios.plugins.assist.models.AssistRequest
import me.olios.plugins.assist.utils.ChatUtils
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import java.util.*

object AssistNotifier {

    fun notifyStaff(request: AssistRequest) {
        for (player in StaffManager.getStaffPlayers()) {
            playSound(player)
            sendChatMessage(player, request)
            sendTitle(player, request)
        }
    }

    fun updatePersistentStatus(pendingRequests: List<AssistRequest>) {
        for (player in StaffManager.getStaffPlayers()) {
            if (pendingRequests.isEmpty()) {
                player.sendActionBar("") // Clear the ActionBar
                continue
            }
            val msg: Component = if (pendingRequests.size == 1) {
                ChatUtils.get(
                    "request.notifyStaff",
                    mapOf("PLAYER" to pendingRequests[0].requesterName)
                )
            } else {
                ChatUtils.get(
                    "request.notifyStaffMultiple",
                    mapOf("COUNT" to pendingRequests.size.toString())
                )
            }

            player.sendActionBar(msg)
        }
    }

    fun notifyPlayerClaimed(playerId: UUID, staff: Player) {
        val player = Bukkit.getPlayer(playerId) ?: return
        ChatUtils.send(
            player,
            "request.claimedByStaff",
            mapOf("STAFF" to staff.name)
        )
    }

    fun notifyStaffClaimed(staff: Player, request: AssistRequest) {
        ChatUtils.send(
            staff,
            "handle.claimSuccess",
            mapOf("PLAYER" to request.requesterName)
        )
        ChatUtils.send(staff, "handle.helpTip")
    }

    fun notifyPlayerClosed(playerId: UUID, staffId: UUID, description: String) {
        val player = Bukkit.getPlayer(playerId) ?: return
        val staff = Bukkit.getPlayer(staffId)

        ChatUtils.send(
            player,
            "request.closedByStaff",
            mapOf("STAFF" to (staff?.name ?: "Staff"))
        )
        ChatUtils.send(
            player,
            "request.closedReason",
            mapOf("DESCRIPTION" to description)
        )
    }

    fun notifyStaffClosed(staffId: UUID, request: AssistRequest, description: String) {
        val staff = Bukkit.getPlayer(staffId)
        StaffManager.getStaffPlayers().forEach {
            ChatUtils.send(
                it,
                "handle.closeSuccess",
                mapOf(
                    "PLAYER" to request.requesterName,
                    "DESCRIPTION" to description
                )
            )
        }
    }

    fun notifyPlayerMessage(player: Player, staff: Player, message: String) {
        ChatUtils.send(
            player,
            "msg.playerToStaff",
            mapOf("STAFF" to staff.name, "MESSAGE" to message)
        )
    }

    fun notifyStaffMessage(staff: Player, player: Player, message: String) {
        ChatUtils.send(
            staff,
            "msg.staffToPlayer",
            mapOf("PLAYER" to player.name, "MESSAGE" to message)
        )
    }

    private fun playSound(player: Player) {
        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
    }

    private fun sendChatMessage(player: Player, request: AssistRequest) {
        ChatUtils.send(
            player,
            "request.broadcast",
            mapOf(
                "PLAYER" to request.requesterName,
                "MESSAGE" to request.message
            )
        )
    }

    private fun sendTitle(player: Player, request: AssistRequest) {
        val title = ChatUtils.get(
            "request.titleHeader"
        )
        val subtitle = ChatUtils.get(
            "request.titleSub",
            mapOf("PLAYER" to request.requesterName)
        )
        player.showTitle(net.kyori.adventure.title.Title.title(title, subtitle))
    }
}
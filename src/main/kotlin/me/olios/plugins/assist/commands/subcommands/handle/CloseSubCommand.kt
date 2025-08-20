package me.olios.plugins.assist.commands.subcommands.handle

import me.olios.plugins.assist.commands.interfaces.SubCommand
import me.olios.plugins.assist.handlers.AssistRequestManager
import me.olios.plugins.assist.services.CloseAction
import me.olios.plugins.assist.utils.ChatUtils
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CloseSubCommand : SubCommand {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) {
            ChatUtils.send(sender, "general.onlyStaff")
            return true
        }

        if (args.isEmpty()) {
            ChatUtils.send(sender, "handle.usage", mapOf("COMMAND" to "close <description>"))
            return true
        }

        val description = args.joinToString(" ")

        // Find the active request this staff has claimed
        val request = AssistRequestManager.getClaimedRequest(sender.uniqueId)
        if (request == null) {
            ChatUtils.send(sender, "handle.noClaimed")
            return true
        }

        // Delegate to CloseAction
        CloseAction.closeRequest(request, sender.uniqueId, description)

        ChatUtils.send(
            sender,
            "handle.closeSuccess",
            mapOf("PLAYER" to request.requesterName, "DESCRIPTION" to description)
        )

        return true
    }


    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        return if (args.isEmpty()) {
            listOf("<description>")
        } else emptyList()
    }
}

package me.olios.plugins.assist.commands.subcommands.handle

import me.olios.plugins.assist.commands.interfaces.SubCommand
import me.olios.plugins.assist.handlers.AssistRequestManager
import me.olios.plugins.assist.services.CloseAction
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CloseSubCommand : SubCommand {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§cOnly staff players can use this command.")
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage("§cUsage: /assist close <description>")
            return true
        }

        val description = args.joinToString(" ")

        // Find the active request this staff has claimed
        val request = AssistRequestManager.getClaimedRequest(sender.uniqueId)
        if (request == null) {
            sender.sendMessage("§cYou have not claimed any active assistance request.")
            return true
        }

        // Delegate to CloseAction
        CloseAction.closeRequest(request, sender.uniqueId, description)
        sender.sendMessage("§aYou have closed the request from §e${request.requesterName}§a with description: §7$description")

        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        return if (args.isEmpty()) {
            listOf("<description>")
        } else emptyList()
    }
}

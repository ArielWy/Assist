package me.olios.plugins.assist.commands.subcommands.handle

import me.olios.plugins.assist.commands.interfaces.SubCommand
import me.olios.plugins.assist.handlers.AssistRequestManager
import me.olios.plugins.assist.services.MsgAction
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.UUID

class MsgSubCommand: SubCommand {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§cOnly staff players can use this command.")
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage("§cUsage: /assist msg <msg>")
            return true
        }

        val msg = args.joinToString(" ")

        // Find the active request this staff has claimed
        val request = AssistRequestManager.getClaimedRequest(sender.uniqueId)
        val staffId = request?.handlerId
        if (request == null || staffId !is UUID) {
            sender.sendMessage("§cYou have not claimed any active assistance request.")
            return true
        }

        // Delegate to CloseAction
        MsgAction.sendMessage(staffId, msg)

        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        return if (args.isEmpty()) {
            listOf("<msg>")
        } else emptyList()
    }
}
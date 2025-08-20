package me.olios.plugins.assist.commands.subcommands.handle

import me.olios.plugins.assist.commands.interfaces.SubCommand
import me.olios.plugins.assist.handlers.AssistRequestManager
import me.olios.plugins.assist.services.MsgAction
import me.olios.plugins.assist.utils.ChatUtils
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.UUID

class MsgSubCommand: SubCommand {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) {
            ChatUtils.send(sender, "general.onlyStaff")
            return true
        }

        if (args.isEmpty()) {
            ChatUtils.send(sender, "msg.usage")
            return true
        }

        val msg = args.joinToString(" ")

        // Find the active request this staff has claimed
        val request = AssistRequestManager.getClaimedRequest(sender.uniqueId)
        val staffId = request?.handlerId
        if (request == null || staffId !is UUID) {
            ChatUtils.send(sender, "handle.noClaimed")
            return true
        }

        // Delegate to MsgAction
        MsgAction.sendMessage(staffId, msg)

        return true
    }


    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        return if (args.isEmpty()) {
            listOf("<msg>")
        } else emptyList()
    }
}
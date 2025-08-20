package me.olios.plugins.assist.commands.subcommands

import me.olios.plugins.assist.commands.interfaces.SubCommand
import me.olios.plugins.assist.handlers.AssistHandler
import me.olios.plugins.assist.utils.ChatUtils
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RequestCommand(override val permission: String) : SubCommand {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) {
            ChatUtils.send(sender, "general.onlyPlayers")
            return true
        }

        if (args.isEmpty()) {
            ChatUtils.send(sender, "general.usageRequest")
            return true
        }

        val message = args.joinToString(" ")
        AssistHandler.handleRequest(sender, message)

        ChatUtils.send(sender, "request.sent")
        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        return emptyList()
    }
}
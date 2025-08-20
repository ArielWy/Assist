package me.olios.plugins.assist.commands.subcommands

import me.olios.plugins.assist.commands.interfaces.SubCommand
import me.olios.plugins.assist.handlers.AssistHandler
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RequestCommand: SubCommand {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§cOnly players can request assistance.")
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage("§cUsage: /assist request <message>")
            return true
        }

        val message = args.joinToString(" ")
        AssistHandler.handleRequest(sender, message)

        sender.sendMessage("§aYour assistance request has been sent to the staff.")
        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        return emptyList()
    }
}
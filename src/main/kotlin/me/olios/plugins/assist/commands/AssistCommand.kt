package me.olios.plugins.assist.commands

import me.olios.plugins.assist.handlers.AssistHandler
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AssistCommand : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§cOnly players can request assistance.")
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage("§cUsage: /assist <message>")
            return true
        }

        val message = args.joinToString(" ")
        AssistHandler.handleRequest(sender, message)

        sender.sendMessage("§aYour assistance request has been sent to the staff.")
        return true
    }
}

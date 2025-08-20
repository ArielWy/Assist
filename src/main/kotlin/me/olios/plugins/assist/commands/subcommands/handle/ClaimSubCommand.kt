package me.olios.plugins.assist.commands.subcommands.handle

import me.olios.plugins.assist.commands.interfaces.SubCommand
import me.olios.plugins.assist.handlers.AssistHandler
import me.olios.plugins.assist.handlers.AssistRequestManager
import me.olios.plugins.assist.services.ClaimAction
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ClaimSubCommand: SubCommand {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        val player = sender as? Player ?: return false

        if (args.isEmpty()) {
            sender.sendMessage("§cUsage: /assist handle claim <player>")
            return true
        }

        val targetName = args[0]
        val targetPlayer = Bukkit.getOnlinePlayers().find { it.name.equals(targetName, ignoreCase = true) }

        if (targetPlayer == null) {
            sender.sendMessage("§cPlayer '$targetName' is not online.")
            return true
        }

        val request = AssistRequestManager.getPendingRequests().find { it.requesterId == targetPlayer.uniqueId }

        if (request == null) {
            sender.sendMessage("§cNo pending assist request from ${targetPlayer.name}.")
            return true
        }

        ClaimAction.execute(request, sender)
        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        return Bukkit.getOnlinePlayers()
            .map { it.name }
            .filter { it.startsWith(args.getOrNull(0) ?: "", ignoreCase = true) }
    }
}
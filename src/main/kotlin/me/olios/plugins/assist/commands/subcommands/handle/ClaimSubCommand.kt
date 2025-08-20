package me.olios.plugins.assist.commands.subcommands.handle

import me.olios.plugins.assist.commands.interfaces.SubCommand
import me.olios.plugins.assist.handlers.AssistHandler
import me.olios.plugins.assist.handlers.AssistRequestManager
import me.olios.plugins.assist.services.ClaimAction
import me.olios.plugins.assist.utils.ChatUtils
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ClaimSubCommand: SubCommand {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        val player = sender as? Player ?: return false

        if (args.isEmpty()) {
            ChatUtils.send(sender, "handle.usageClaim")
            return true
        }

        val targetName = args[0]
        val targetPlayer = Bukkit.getOnlinePlayers().find { it.name.equals(targetName, ignoreCase = true) }

        if (targetPlayer == null) {
            ChatUtils.send(
                sender,
                "handle.notOnline",
                mapOf("PLAYER" to targetName)
            )
            return true
        }

        val request = AssistRequestManager.getPendingRequests().find { it.requesterId == targetPlayer.uniqueId }

        if (request == null) {
            ChatUtils.send(
                sender,
                "handle.noPendingFrom",
                mapOf("PLAYER" to targetPlayer.name)
            )
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
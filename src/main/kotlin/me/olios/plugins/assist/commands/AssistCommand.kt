package me.olios.plugins.assist.commands

import me.olios.plugins.assist.utils.ChatUtils
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter


class AssistCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) return false

        val subCommand = SubCommandManager.getCommand(args[0])
        if (subCommand == null) {
            ChatUtils.send(sender, "general.unknownSubcommand", mapOf("subcommand" to args[0]))
            return true
        }

        return subCommand.execute(sender, args.sliceArray(1 until args.size))
    }

    override fun onTabComplete(
        sender: CommandSender,
        p1: Command,
        p2: String,
        args: Array<out String>
    ): MutableList<String>? {
        val subCommands = SubCommandManager.getAllCommands().filter { sender.hasPermission("assist.${it.lowercase()}") }


        /** debugging
        val allCommands = SubCommandManager.getAllCommands()

        val debugInfo = allCommands.map { command ->
            val permission = "assist.${command.lowercase()}"
            val hasPermission = sender.hasPermission(permission)
            "$command → Permission: \"$permission\" → Has: $hasPermission"
        }

        sender.sendMessage("SubCommands Debug:\n${debugInfo.joinToString("\n")}")
        **/


        if (args.isEmpty()) return subCommands.toMutableList()

        val subCommand = SubCommandManager.getCommand(args[0])
        if (args.size == 1) {
            return subCommands
                .filter { it.startsWith(args[0], ignoreCase = true) }
                .toMutableList()
        }

        return subCommand?.tabComplete(sender, args.sliceArray(1 until args.size))?.toMutableList()
    }
}

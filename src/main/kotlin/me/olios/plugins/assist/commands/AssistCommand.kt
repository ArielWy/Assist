package me.olios.plugins.assist.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter


class AssistCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) return false

        val subCommand = SubCommandManager.getCommand(args[0])
        if (subCommand == null) {
            sender.sendMessage("§cUnknown subcommand: ${args[0]}")
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
        val subCommands = SubCommandManager.getAllCommands().filter { sender.hasPermission("Assist.$it") }

        if (args == null || args.isEmpty()) return subCommands.toMutableList()

        val subCommand = SubCommandManager.getCommand(args[0])
        if (args.size == 1) {
            return subCommands
                .filter { it.startsWith(args[0], ignoreCase = true) }
                .toMutableList()
        }

        return subCommand?.tabComplete(sender, args.sliceArray(1 until args.size))?.toMutableList()
    }
}

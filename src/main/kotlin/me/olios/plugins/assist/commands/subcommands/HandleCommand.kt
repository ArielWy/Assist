package me.olios.plugins.assist.commands.subcommands

import me.olios.plugins.assist.commands.interfaces.ParentSubCommand
import me.olios.plugins.assist.commands.interfaces.SubCommand
import me.olios.plugins.assist.commands.subcommands.handle.*
import me.olios.plugins.assist.utils.ChatUtils
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class HandleCommand : ParentSubCommand {

    private val subCommands: MutableMap<String, SubCommand> = mutableMapOf()

    init {
        subCommands["claim"] = ClaimSubCommand()
        subCommands["close"] = CloseSubCommand()
        subCommands["msg"] = MsgSubCommand()
    }

    override fun getSubCommand(name: String): SubCommand? = subCommands[name.lowercase()]
    override fun getSubCommandNames(): Set<String> = subCommands.keys

    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) {
            ChatUtils.send(sender, "general.onlyPlayers")
            return true
        }

        if (!sender.hasPermission("assist.staff")) {
            ChatUtils.send(sender, "general.noPermission")
            return true
        }

        if (args.isEmpty()) {
            ChatUtils.send(
                sender,
                "general.usageHandle",
                mapOf("subcommands" to getSubCommandNames().joinToString("|"))
            )
            return true
        }

        val sub = getSubCommand(args[0])
        if (sub == null) {
            ChatUtils.send(sender, "general.unknownSubcommand", mapOf("subcommand" to args[0]))
            return true
        }

        return sub.execute(sender, args.sliceArray(1 until args.size))
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        if (args.isEmpty()) return getSubCommandNames().toList()

        val sub = getSubCommand(args[0])
        return if (args.size == 1) {
            getSubCommandNames().filter { it.startsWith(args[0], ignoreCase = true) }
        } else {
            sub?.tabComplete(sender, args.sliceArray(1 until args.size)) ?: emptyList()
        }
    }
}

package me.olios.plugins.assist.commands.subcommands

import me.olios.plugins.assist.commands.interfaces.ParentSubCommand
import me.olios.plugins.assist.commands.interfaces.SubCommand
import me.olios.plugins.assist.commands.subcommands.handle.*
import me.olios.plugins.assist.utils.ChatUtils
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class HandleCommand(override val permission: String) : ParentSubCommand {

    private val subCommands: MutableMap<String, SubCommand> = mutableMapOf()

    init {
        subCommands["claim"] = ClaimSubCommand("assist.handle.claim")
        subCommands["close"] = CloseSubCommand("assist.handle.close")
        subCommands["msg"]   = MsgSubCommand("assist.handle.msg")
        // subCommands["tp"] = TpSubCommand("assist.handle.tp")
    }

    override fun getSubCommand(name: String): SubCommand? = subCommands[name.lowercase()]
    override fun getSubCommandNames(): Set<String> = subCommands.keys

    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) {
            ChatUtils.send(sender, "general.onlyPlayers")
            return true
        }

        if (args.isEmpty()) {
            ChatUtils.send(
                sender,
                "handle.usage",
                mapOf("SUBCOMMANDS" to getSubCommandNames().joinToString("|"))
            )
            return true
        }

        val sub = getSubCommand(args[0])
        if (sub == null) {
            ChatUtils.send(
                sender,
                "general.unknownSubcommand",
                mapOf("SUBCOMMAND" to args[0])
            )
            return true
        }

        if (!sender.hasPermission(sub.permission)) {
            ChatUtils.send(sender, "general.noPermission")
            return true
        }

        return sub.execute(sender, args.sliceArray(1 until args.size))
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        val availableSubCommands = getSubCommandNames()
            .filter { sender.hasPermission("assist.handle.${it.lowercase()}") }

        return if (args.size == 1) {
            availableSubCommands.filter { it.startsWith(args[0], ignoreCase = true) }
        } else {
            val sub = getSubCommand(args[0])
            sub?.tabComplete(sender, args.sliceArray(1 until args.size)) ?: emptyList()
        }
    }
}
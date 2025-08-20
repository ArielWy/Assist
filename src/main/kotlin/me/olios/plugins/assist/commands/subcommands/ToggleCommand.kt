package me.olios.plugins.assist.commands.subcommands

import me.olios.plugins.assist.commands.interfaces.SubCommand
import me.olios.plugins.assist.handlers.ToggleManager
import me.olios.plugins.assist.utils.ChatUtils
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ToggleCommand(
    override val permission: String
) : SubCommand {

    private val permanentPermission = "$permission.permanent"

    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) {
            ChatUtils.send(sender, "general.onlyPlayers")
            return true
        }

        if (args.isNotEmpty() && args[0].lowercase() == "permanent") {
            if (!sender.hasPermission(permanentPermission)) {
                ChatUtils.send(sender, "general.noPermission")
                return true
            }
            val nowDisabled = ToggleManager.togglePermanent(sender)
            val state = if (nowDisabled) "Disabled" else "Enabled"
            ChatUtils.send(sender, "toggle.permanently$state")
        } else {
            val nowDisabled = ToggleManager.toggleTemporary(sender)
            val state = if (nowDisabled) "temporarilyDisabled" else "temporarilyEnabled"
            ChatUtils.send(sender, "toggle.$state")
        }
        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        return if (args.size == 1 && sender.hasPermission(permanentPermission)) {
            listOf("permanent").filter { it.startsWith(args[0], ignoreCase = true) }
        } else emptyList()
    }
}

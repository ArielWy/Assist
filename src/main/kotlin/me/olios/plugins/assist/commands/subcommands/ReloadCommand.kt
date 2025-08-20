package me.olios.plugins.assist.commands.subcommands

import me.olios.plugins.assist.Assist
import me.olios.plugins.assist.commands.interfaces.SubCommand
import me.olios.plugins.assist.handlers.StaffManager
import me.olios.plugins.assist.utils.ChatUtils
import me.olios.plugins.assist.utils.ConfigHandler
import org.bukkit.command.CommandSender

class ReloadCommand(override val permission: String) : SubCommand {

    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (!sender.hasPermission(permission)) {
            ChatUtils.send(sender, "general.noPermission")
            return true
        }

        val plugin = Assist.getInstance()

        ConfigHandler.reload() // load config
        ChatUtils.init(plugin) // load message.yml
        StaffManager.init() // load staff member in the server

        ChatUtils.send(sender, "general.reloadSuccess")
        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        return emptyList()
    }
}
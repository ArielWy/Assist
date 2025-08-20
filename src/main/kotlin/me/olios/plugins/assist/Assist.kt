package me.olios.plugins.assist

import me.olios.plugins.assist.commands.AssistCommand
import me.olios.plugins.assist.commands.SubCommandManager
import me.olios.plugins.assist.commands.subcommands.HandleCommand
import me.olios.plugins.assist.commands.subcommands.RequestCommand
import me.olios.plugins.assist.commands.subcommands.ToggleCommand
import me.olios.plugins.assist.handlers.AssistHandler
import me.olios.plugins.assist.handlers.AssistRequestManager
import me.olios.plugins.assist.handlers.StaffManager
import me.olios.plugins.assist.notify.AssistStatusTask
import me.olios.plugins.assist.utils.ChatUtils
import me.olios.plugins.assist.utils.ConfigHandler
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Assist : JavaPlugin() {

    companion object {
        // define the plugin instance
        private lateinit var instance: Assist
        fun getInstance(): Assist = instance
    }

    override fun onEnable() {
        //define plugin instance
        instance = this

        // init objects
        ConfigHandler.init(this) // load config
        ChatUtils.init(this) // load message.yml
        StaffManager.init() // load staff member in the server

        // start the persistent AssistStatusTask
        AssistStatusTask.start(this) { AssistRequestManager.getPendingRequests() }

        // register commands and listeners
        registerCommands()
        registerListeners()
    }

    private fun registerCommands() {
        getCommand("assist")?.setExecutor(AssistCommand())

        SubCommandManager.registerCommand("request", RequestCommand("assist.request"))
        SubCommandManager.registerCommand("handle", HandleCommand("assist.handle"))
        SubCommandManager.registerCommand("toggle", ToggleCommand("assist.toggle"))
    }

    private fun registerListeners() {
        Bukkit.getServer().pluginManager.registerEvents(StaffManager, this)
    }


    override fun onDisable() {
        // Cancel all tasks started by this plugin
        Bukkit.getScheduler().cancelTasks(this)
    }
}

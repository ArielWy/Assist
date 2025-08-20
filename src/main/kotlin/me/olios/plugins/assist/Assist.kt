package me.olios.plugins.assist

import me.olios.plugins.assist.commands.AssistCommand
import me.olios.plugins.assist.commands.SubCommandManager
import me.olios.plugins.assist.commands.subcommands.HandleCommand
import me.olios.plugins.assist.commands.subcommands.RequestCommand
import me.olios.plugins.assist.handlers.AssistHandler
import me.olios.plugins.assist.handlers.AssistRequestManager
import me.olios.plugins.assist.handlers.StaffManager
import me.olios.plugins.assist.notify.AssistStatusTask
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

        // start the persistent AssistStatusTask
        AssistStatusTask.start(this) { AssistRequestManager.getPendingRequests() }

        // init StaffManager
        StaffManager.init()

        // register commands and listeners
        registerCommands()
        registerListeners()
    }

    private fun registerCommands() {
        getCommand("assist")?.setExecutor(AssistCommand())

        SubCommandManager.registerCommand("request", RequestCommand())
        SubCommandManager.registerCommand("handle", HandleCommand())
    }

    private fun registerListeners() {
        Bukkit.getServer().pluginManager.registerEvents(StaffManager, this)
    }


    override fun onDisable() {
        // Cancel all tasks started by this plugin
        Bukkit.getScheduler().cancelTasks(this)
    }
}

package me.olios.plugins.assist.commands

import me.olios.plugins.assist.commands.interfaces.SubCommand

object SubCommandManager {
    private val commands: MutableMap<String, SubCommand> = mutableMapOf()

    fun registerCommand(name: String, command: SubCommand) {
        commands[name] = command
    }

    fun getCommand(name: String): SubCommand? {
        return commands[name.lowercase()]
    }

    fun getAllCommands(): Set<String> {
        return commands.keys
    }
}
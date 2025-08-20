package me.olios.plugins.assist.commands.interfaces

interface ParentSubCommand : SubCommand {
    fun getSubCommand(name: String): SubCommand?
    fun getSubCommandNames(): Set<String>
}

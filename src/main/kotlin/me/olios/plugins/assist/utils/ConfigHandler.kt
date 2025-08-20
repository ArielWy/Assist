package me.olios.plugins.assist.utils

import me.olios.plugins.assist.Assist
import org.bukkit.configuration.file.FileConfiguration

object ConfigHandler {
    private lateinit var config: FileConfiguration
    private lateinit var plugin: Assist

    fun init(plugin: Assist) {
        this.plugin = plugin
        plugin.saveDefaultConfig()
        config = plugin.config
    }

    fun reload() {
        plugin.reloadConfig()
        config = plugin.config
    }

    fun save() {
        plugin.saveConfig()
    }

    // ---- Permanent Toggle ----
    fun getPermanentlyDisabled(): Set<String> {
        return config.getStringList("toggle.permanentDisabled").toSet()
    }

    fun addPermanentlyDisabled(playerName: String) {
        val list = config.getStringList("toggle.permanentDisabled").toMutableSet()
        list.add(playerName)
        config.set("toggle.permanentDisabled", list.toList())
        save()
    }

    fun removePermanentlyDisabled(playerName: String) {
        val list = config.getStringList("toggle.permanentDisabled").toMutableSet()
        list.remove(playerName)
        config.set("toggle.permanentDisabled", list.toList())
        save()
    }

    fun isPermanentlyDisabled(playerName: String): Boolean {
        return config.getStringList("toggle.permanentDisabled").contains(playerName)
    }
}

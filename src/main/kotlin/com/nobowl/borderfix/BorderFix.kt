package com.nobowl.borderfix

import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

class BorderFix : JavaPlugin() {

    val stats: MutableMap<UUID, PlayerStats> = HashMap()

    lateinit var enabledWorlds: Set<String>
    var blockedMessage: String = ""
    var autoCorrect: Boolean = false
    var checkIntervalTicks: Long = 20L
    var knockbackEnabled: Boolean = false
    var knockbackStrength: Double = 1.2
    var knockbackVertical: Double = 0.4
    var pearlCancelMidair: Boolean = true
    var pluginTeleportBlock: Boolean = true
    var detectAote: Boolean = false
    var aoteName: String = ""
    var loggingEnabled: Boolean = false

    override fun onEnable() {
        saveDefaultConfig()
        reloadCachedConfig()

        server.pluginManager.registerEvents(BorderListener(this), this)
        server.pluginManager.registerEvents(PearlListener(this), this)

        BorderTask(this).start()

        getCommand("borderfix")!!.setExecutor(DebugCommand(this))

        logger.info("[BorderFix] Loaded successfully.")
    }

    fun reloadCachedConfig() {
        reloadConfig()
        enabledWorlds = config.getStringList("enabled-worlds").toHashSet()
        blockedMessage = ChatColor.translateAlternateColorCodes(
            '&', config.getString("blocked-message") ?: "&cYou cannot go past the world border!"
        )
        autoCorrect = config.getBoolean("auto-correct")
        checkIntervalTicks = config.getLong("check-interval-ticks")
        knockbackEnabled = config.getBoolean("knockback.enabled")
        knockbackStrength = config.getDouble("knockback.strength")
        knockbackVertical = config.getDouble("knockback.vertical")
        pearlCancelMidair = config.getBoolean("pearl.cancel-midair")
        pluginTeleportBlock = config.getBoolean("plugin-teleport.block")
        detectAote = config.getBoolean("plugin-teleport.detect-aote")
        aoteName = config.getString("plugin-teleport.aote-name") ?: ""
        loggingEnabled = config.getBoolean("logging.enabled")
    }
}

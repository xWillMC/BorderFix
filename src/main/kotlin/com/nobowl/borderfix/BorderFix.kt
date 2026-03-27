package com.nobowl.borderfix

import org.bukkit.plugin.java.JavaPlugin

class BorderFix : JavaPlugin() {

    lateinit var stats: MutableMap<java.util.UUID, PlayerStats>

    override fun onEnable() {
        saveDefaultConfig()

        stats = HashMap()

        server.pluginManager.registerEvents(BorderListener(this), this)
        server.pluginManager.registerEvents(PearlListener(this), this)

        BorderTask(this).start()

        getCommand("borderfix")!!.setExecutor(DebugCommand(this))

        logger.info("[BorderFix] Loaded successfully.")
    }
}
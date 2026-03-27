package com.nobowl.borderfix

import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent

class BorderListener(private val plugin: BorderFix) : Listener {

    @EventHandler(ignoreCancelled = true)
    fun onTeleport(event: PlayerTeleportEvent) {
        val player = event.player
        val world = player.world

        if (!Util.enabled(plugin, world)) return

        val to = event.to ?: return
        if (Util.inside(world, to)) return

        val cause = event.cause

        val isPearl = cause == PlayerTeleportEvent.TeleportCause.ENDER_PEARL
        val isChorus = cause == PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT
        val isPlugin = cause == PlayerTeleportEvent.TeleportCause.PLUGIN

        if (!isPearl && !isChorus && !isPlugin) return

        if (isPlugin && plugin.config.getBoolean("plugin-teleport.detect-aote")) {
            val item = player.inventory.itemInMainHand

            if (item.hasItemMeta() && item.itemMeta!!.hasDisplayName()) {
                val name = ChatColor.stripColor(item.itemMeta!!.displayName) ?: ""

                if (name.contains(plugin.config.getString("plugin-teleport.aote-name")!!, true)) {
                    Util.flag(plugin, player, "AOTE_ATTEMPT")
                }
            }
        }

        if (isPlugin && !plugin.config.getBoolean("plugin-teleport.block")) return

        event.isCancelled = true
        player.sendMessage(Util.msg(plugin))

        Util.flag(plugin, player, "BLOCKED")
        Util.log(plugin, "${player.name} blocked teleport ($cause)")
    }
}
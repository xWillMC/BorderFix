package com.nobowl.borderfix

import org.bukkit.Bukkit

class BorderTask(private val plugin: BorderFix) {

    fun start() {
        Bukkit.getScheduler().runTaskTimer(plugin, Runnable {

            if (!plugin.autoCorrect) return@Runnable

            for (player in Bukkit.getOnlinePlayers()) {
                val world = player.world

                if (!Util.enabled(plugin, world)) continue
                if (Util.inside(world, player.location)) continue

                if (plugin.knockbackEnabled) {
                    Util.knockback(plugin, player)
                } else {
                    player.teleport(Util.safe(world))
                }

                player.sendMessage(plugin.blockedMessage)
                Util.flag(plugin, player, "OUTSIDE")
            }

        }, 20L, plugin.checkIntervalTicks)
    }
}

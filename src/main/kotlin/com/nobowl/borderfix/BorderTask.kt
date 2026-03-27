package com.nobowl.borderfix

import org.bukkit.Bukkit

class BorderTask(private val plugin: BorderFix) {

    fun start() {
        Bukkit.getScheduler().runTaskTimer(plugin, Runnable {

            if (!plugin.config.getBoolean("auto-correct")) return@Runnable

            for (player in Bukkit.getOnlinePlayers()) {
                val world = player.world

                if (!Util.enabled(plugin, world)) continue
                if (Util.inside(world, player.location)) continue

                if (plugin.config.getBoolean("knockback.enabled")) {
                    Util.knockback(plugin, player)
                } else {
                    player.teleport(Util.safe(world))
                }

                player.sendMessage(Util.msg(plugin))
                Util.flag(plugin, player, "OUTSIDE")
            }

        }, 20L, plugin.config.getLong("check-interval-ticks"))
    }
}
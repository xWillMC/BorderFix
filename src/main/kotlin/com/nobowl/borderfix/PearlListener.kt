package com.nobowl.borderfix

import org.bukkit.Bukkit
import org.bukkit.entity.EnderPearl
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileLaunchEvent

class PearlListener(private val plugin: BorderFix) : Listener {

    @EventHandler
    fun onPearl(event: ProjectileLaunchEvent) {
        val pearl = event.entity as? EnderPearl ?: return
        val player = pearl.shooter as? Player ?: return

        if (!plugin.config.getBoolean("pearl.cancel-midair")) return
        if (!Util.enabled(plugin, player.world)) return

        Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            if (!pearl.isValid) return@Runnable

            if (!Util.inside(player.world, pearl.location)) {
                pearl.remove()
                player.sendMessage(Util.msg(plugin))

                Util.flag(plugin, player, "PEARL")
                Util.log(plugin, "${player.name} pearl exploit prevented")
            }
        }, 1L, 1L)
    }
}
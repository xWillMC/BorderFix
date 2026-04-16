package com.nobowl.borderfix

import org.bukkit.Bukkit
import org.bukkit.entity.EnderPearl
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.scheduler.BukkitTask

class PearlListener(private val plugin: BorderFix) : Listener {

    @EventHandler
    fun onPearl(event: ProjectileLaunchEvent) {
        val pearl = event.entity as? EnderPearl ?: return
        val player = pearl.shooter as? Player ?: return

        if (!plugin.pearlCancelMidair) return
        if (!Util.enabled(plugin, player.world)) return

        var task: BukkitTask? = null
        task = Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            // Pearl landed or despawned naturally — cancel the tracker
            if (!pearl.isValid) {
                task?.cancel()
                return@Runnable
            }

            if (!Util.inside(player.world, pearl.location)) {
                pearl.remove()
                task?.cancel()
                player.sendMessage(plugin.blockedMessage)
                Util.flag(plugin, player, "PEARL")
                Util.log(plugin, "${player.name} pearl exploit prevented")
            }
        }, 1L, 1L)
    }
}

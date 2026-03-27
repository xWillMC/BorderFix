package com.nobowl.borderfix

import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.util.Vector

object Util {

    fun enabled(plugin: BorderFix, world: World): Boolean {
        return plugin.config.getStringList("enabled-worlds").contains(world.name)
    }

    fun msg(plugin: BorderFix): String {
        return ChatColor.translateAlternateColorCodes('&',
            plugin.config.getString("blocked-message")!!
        )
    }

    fun inside(world: World, loc: Location): Boolean {
        val b = world.worldBorder
        val c = b.center
        val s = b.size / 2

        return loc.x in (c.x - s)..(c.x + s) &&
               loc.z in (c.z - s)..(c.z + s)
    }

    fun safe(world: World): Location {
        val c = world.worldBorder.center
        val y = world.getHighestBlockYAt(c.blockX, c.blockZ) + 1
        return Location(world, c.x, y.toDouble(), c.z)
    }

    fun knockback(plugin: BorderFix, player: Player) {
        val c = player.world.worldBorder.center
        val dir = c.toVector().subtract(player.location.toVector()).normalize()

        val strength = plugin.config.getDouble("knockback.strength")
        val y = plugin.config.getDouble("knockback.vertical")

        val vec = dir.multiply(strength)
        vec.y = y

        player.velocity = vec
    }

    fun flag(plugin: BorderFix, player: Player, type: String) {
        val stats = plugin.stats.computeIfAbsent(player.uniqueId) { PlayerStats() }
        stats.add(type)
    }

    fun log(plugin: BorderFix, msg: String) {
        if (plugin.config.getBoolean("logging.enabled")) {
            plugin.logger.info("[BorderFix] $msg")
        }
    }
}
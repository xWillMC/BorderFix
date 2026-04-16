package com.nobowl.borderfix

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player

object Util {

    fun enabled(plugin: BorderFix, world: World): Boolean {
        return plugin.enabledWorlds.contains(world.name)
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

        val vec = dir.multiply(plugin.knockbackStrength)
        vec.y = plugin.knockbackVertical

        player.velocity = vec
    }

    fun flag(plugin: BorderFix, player: Player, type: String) {
        plugin.stats.computeIfAbsent(player.uniqueId) { PlayerStats() }.add(type)
    }

    fun log(plugin: BorderFix, msg: String) {
        if (plugin.loggingEnabled) {
            plugin.logger.info("[BorderFix] $msg")
        }
    }
}

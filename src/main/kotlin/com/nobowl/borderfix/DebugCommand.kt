package com.nobowl.borderfix

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class DebugCommand(private val plugin: BorderFix) : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {

        sender.sendMessage("§6--- BorderFix Debug ---")
        sender.sendMessage("§ePlayers tracked: §f${plugin.stats.size}")

        for ((uuid, stats) in plugin.stats) {
            val name = Bukkit.getOfflinePlayer(uuid).name ?: "Unknown"
            sender.sendMessage("§b$name §7-> ${stats.summary()}")
        }

        return true
    }
}
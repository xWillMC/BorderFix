package com.nobowl.borderfix

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class DebugCommand(private val plugin: BorderFix) : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sendDebug(sender)
            return true
        }

        when (args[0].lowercase()) {
            "debug" -> sendDebug(sender)
            "reload" -> {
                plugin.reloadCachedConfig()
                sender.sendMessage("§a[BorderFix] Config reloaded.")
            }
            else -> sender.sendMessage("§cUsage: /borderfix [debug|reload]")
        }

        return true
    }

    private fun sendDebug(sender: CommandSender) {
        sender.sendMessage("§6--- BorderFix Debug ---")
        sender.sendMessage("§ePlayers tracked: §f${plugin.stats.size}")

        for ((uuid, stats) in plugin.stats) {
            val name = Bukkit.getOfflinePlayer(uuid).name ?: "Unknown"
            sender.sendMessage("§b$name §7-> ${stats.summary()}")
        }
    }
}

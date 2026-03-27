BorderFix
World border enforcement plugin for Paper/Spigot 1.21+.

Structure:
core: main plugin logic handling movement interception and border enforcement
listeners: event hooks for teleportation, projectile motion, and ability usage
anti-exploit: violation tracking + detection for abnormal border bypass attempts
commands: debug and admin tooling (/borderfix debug)
config: lightweight configuration handling and defaults

Why:
Stupid children on Voidsent keep glitching outside of the border.
BorderFix exists to enforce consistent border rules across all movement types without relying on heavy world manipulation or external proxies.

How It Works:
Player movement is monitored during teleportation events and projectile launches.

If a movement vector would result in a position outside the world border, the action is intercepted before execution.

Ender pearls are cancelled mid-flight if their trajectory predicts a border violation.

Chorus fruit teleportation is validated before relocation occurs and prevented if unsafe.

MMOItems-based teleport abilities are treated as controlled teleports and run through the same validation layer.

If a player still ends up outside the border (edge-case desync or plugin conflict), they are automatically returned inside using a calculated knockback vector instead of a direct teleport to preserve natural movement feel.

A per-player violation tracker logs repeated attempts and suspicious patterns for debugging and anti-exploit visibility.

A lightweight debug command exposes runtime stats such as interception counts, violation totals, and recent blocked actions.

package net.royalmind.minecraft.plugin.nick;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.royalmind.minecraft.plugin.nick.commands.NickCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.haoshoku.nick.api.NickAPI;

import java.util.UUID;

public final class Nick extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("nick").setExecutor(new NickCommand());
        this.messageService();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void messageService() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (final UUID uuid : NickAPI.getNickedPlayers().keySet()) {
                    final Player player = Bukkit.getPlayer(uuid);
                    if (player == null) return;
                    if (!(player.isOnline())) return;
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Currently you are Nicked"));
                }
            }
        }.runTaskTimer(this, 0L, 20L);
    }
}

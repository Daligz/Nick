package net.royalmind.minecraft.plugin.nick;

import net.royalmind.minecraft.plugin.nick.commands.NickCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Nick extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("nick").setExecutor(new NickCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

package net.royalmind.minecraft.plugin.nick.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.haoshoku.nick.api.NickAPI;

public class NickCommand implements CommandExecutor {

    private static final String[] HELP_MESSAGES = {
            "/nick uui",
            "/nick off",
            "/nick <Player>",
    };

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("No puedes ejecutar este comando en consola!");
            return true;
        }
        final Player player = (Player) sender;
        if (!(player.hasPermission("royalmind.nick.use"))) {
            player.sendMessage(ChatColor.RED + "No tienes permiso de ejecutar este comando!");
            return true;
        }
        if (args.length <= 0) {
            for (final String helpMessage : HELP_MESSAGES) {
                player.sendMessage(ChatColor.YELLOW + helpMessage);
            }
            return true;
        }
        final String arg = args[0];
        if (arg.equalsIgnoreCase("off")) {
            NickAPI.resetNick(player);
            NickAPI.resetGameProfileName(player);
            NickAPI.resetSkin(player);
            NickAPI.resetUniqueId(player);
            NickAPI.refreshPlayer(player);
            player.sendMessage(ChatColor.YELLOW + "Vuelves a ser " + player.getDisplayName());
            return true;
        } else if (arg.equalsIgnoreCase("uuid")) {
            player.sendMessage(ChatColor.RED + "Tu UUID es : " + player.getUniqueId());
            return true;
        }
        if (NickAPI.isNicked(player)) {
            player.sendMessage(ChatColor.RED + "Ya tienes un nick!");
            return true;
        }

        NickAPI.nick(player, arg);
        NickAPI.setSkin(player, arg);
        NickAPI.setUniqueId(player, arg);
        NickAPI.setGameProfileName(player, arg);
        NickAPI.refreshPlayer(player);
        player.sendMessage(ChatColor.GREEN + "Ahora eres " + arg);
        return true;
    }
}

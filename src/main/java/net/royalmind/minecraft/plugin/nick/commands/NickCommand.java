package net.royalmind.minecraft.plugin.nick.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.haoshoku.nick.api.NickAPI;
import xyz.haoshoku.nick.api.NickScoreboard;

public class NickCommand implements CommandExecutor {

    private static final String[] HELP_MESSAGES = {
            "/nick uui",
            "/nick update",
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
            player.setDisplayName(player.getName());
            player.sendMessage(ChatColor.YELLOW + "Vuelves a ser " + player.getDisplayName());
            return true;
        } else if (arg.equalsIgnoreCase("uuid")) {
            player.sendMessage(ChatColor.RED + "Tu UUID es : " + player.getUniqueId());
            return true;
        } else if (arg.equalsIgnoreCase("update")) {
            NickAPI.refreshPlayer(player);
        }
        if (NickAPI.isNicked(player)) {
            player.sendMessage(ChatColor.RED + "Ya tienes un nick!");
            return true;
        }
        player.setDisplayName(arg);
        NickAPI.nick(player, arg);
        NickAPI.setSkin(player, arg);
        NickAPI.setUniqueId(player, arg);
        NickAPI.setGameProfileName(player, arg);
        if (arg.equalsIgnoreCase("zKevsh")) {
            NickScoreboard.write(arg, "testa", translate("&4[&fYoutube&4] "), translate(" &8[&4あ&8]"), true, ChatColor.RED);
        } else {
            NickScoreboard.write(arg, "testa", translate("&a[VIP] &a"), "", true, ChatColor.RED);
        }
        NickAPI.refreshPlayer(player);
        NickScoreboard.updateAllScoreboard();
        player.sendMessage(ChatColor.GREEN + "Ahora eres " + arg);
        return true;
    }

    private String translate(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}

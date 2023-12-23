package cn.chengzhiya.mhdfplaytime.Commands;

import cn.chengzhiya.mhdfplaytime.main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static cn.chengzhiya.mhdfplaytime.Util.ChatColor;

public final class playtimereload implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        main.main.reloadConfig();
        sender.sendMessage(ChatColor("&a重载成功!"));
        return false;
    }
}

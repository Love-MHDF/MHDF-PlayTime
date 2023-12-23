package cn.chengzhiya.mhdfplaytime.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static cn.chengzhiya.mhdfplaytime.Util.ChatColor;
import static cn.chengzhiya.mhdfplaytime.Util.getPlayTimeString;

public final class playtime implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(ChatColor("&e你的游玩时间: &b" + getPlayTimeString((Player) sender)));
        return false;
    }
}

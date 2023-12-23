package cn.chengzhiya.mhdfplaytime.Tasks;

import cn.chengzhiya.mhdfplaytime.main;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static cn.chengzhiya.mhdfplaytime.Util.*;

public final class RunAction extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            int PlayTime = getPlayTime(player);
            List<String> ActionList = main.main.getConfig().getStringList("RunAction." + PlayTime + ".ActionList");
            if (!ActionList.isEmpty()) {
                for (String Actions : ActionList) {
                    String[] Action = Actions.split("\\|");
                    if (Action[0].equals("player")) {
                        Bukkit.getScheduler().runTask(main.main, () -> {
                            player.chat("/" + PlaceholderAPI.setPlaceholders(player, Action[1]));
                        });
                        continue;
                    }
                    if (Action[0].equals("console")) {
                        Bukkit.getScheduler().runTask(main.main, () -> {
                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(player, Action[1]));
                        });
                        continue;
                    }
                    if (Action[0].equals("title")) {
                        player.sendTitle(PlaceholderAPI.setPlaceholders(player, Action[1]), PlaceholderAPI.setPlaceholders(player, Action[2]), Integer.parseInt(Action[3]), Integer.parseInt(Action[4]), Integer.parseInt(Action[5]));
                        continue;
                    }
                    if (Action[0].equals("message")) {
                        if (Action.length == 2) {
                            player.sendMessage(ChatColor(PlaceholderAPI.setPlaceholders(player, Action[1])));
                        } else {
                            StringBuilder Message = new StringBuilder();
                            for (int i = 1; i < Action.length; i++) {
                                if (i != Action.length-1) {
                                    Message.append(Action[i]).append("\n");
                                } else {
                                    Message.append(Action[i]);
                                }
                            }
                            player.sendMessage(ChatColor(PlaceholderAPI.setPlaceholders(player, Message.toString())));
                        }
                        continue;
                    }
                    if (Action[0].equals("sound")) {
                        try {
                            player.playSound(player, Sound.valueOf(Action[1]), 100.0F, 1.0F);
                        } catch (Exception e) {
                            ColorLog("&c[MHDF-PlayTime]音频不存在,无法播放");
                        }
                        continue;
                    }
                }
            }
        }
    }
}

package cn.chengzhiya.mhdfplaytime.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static cn.chengzhiya.mhdfplaytime.Util.addPlayTime;

public final class PlayTime extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            addPlayTime(player, 1);
        }
    }
}

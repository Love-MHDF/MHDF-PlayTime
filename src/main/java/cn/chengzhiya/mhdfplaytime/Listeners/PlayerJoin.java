package cn.chengzhiya.mhdfplaytime.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static cn.chengzhiya.mhdfplaytime.Util.initializationPlayerData;

public final class PlayerJoin implements Listener {
    @EventHandler
    public void onEvent(PlayerJoinEvent event) {
        initializationPlayerData(event.getPlayer());
    }
}

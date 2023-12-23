package cn.chengzhiya.mhdfplaytime.Hooks;

import cn.chengzhiya.mhdfplaytime.main;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static cn.chengzhiya.mhdfplaytime.Util.getPlayTimeString;

public final class PlaceholderAPI extends PlaceholderExpansion {
    public PlaceholderAPI() {
    }

    @Override
    public @NotNull String getAuthor() {
        return "ChengZhiYa";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "PlayTime";
    }

    @Override
    public @NotNull String getVersion() {
        return main.descriptionFile.getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (params.equalsIgnoreCase("get")) {
            return getPlayTimeString((Player) player);
        }
        return null;
    }
}

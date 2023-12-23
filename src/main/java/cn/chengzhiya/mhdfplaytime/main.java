package cn.chengzhiya.mhdfplaytime;

import cn.chengzhiya.mhdfplaytime.Commands.playtime;
import cn.chengzhiya.mhdfplaytime.Commands.playtimereload;
import cn.chengzhiya.mhdfplaytime.Hooks.PlaceholderAPI;
import cn.chengzhiya.mhdfplaytime.Listeners.PlayerJoin;
import cn.chengzhiya.mhdfplaytime.Tasks.PlayTime;
import cn.chengzhiya.mhdfplaytime.Tasks.RunAction;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.TimeZone;

import static cn.chengzhiya.mhdfplaytime.Util.ColorLog;
import static cn.chengzhiya.mhdfplaytime.YamlFileUtil.SaveResource;

public final class main extends JavaPlugin {
    public static main main;
    public static Statement statement;
    public static HikariDataSource dataSource;
    public static PluginDescriptionFile descriptionFile;

    @Override
    public void onEnable() {
        // Plugin startup logic
        main = this;
        descriptionFile = this.getDescription();

        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://" + main.getConfig().getString("DatabaseSettings.Host") + "/" + main.getConfig().getString("DatabaseSettings.Database") + "?autoReconnect=true&serverTimezone=" + TimeZone.getDefault().getID());
            config.setUsername(main.getConfig().getString("DatabaseSettings.User"));
            config.setPassword(main.getConfig().getString("DatabaseSettings.Password"));
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            dataSource = new HikariDataSource(config);
            statement = dataSource.getConnection().createStatement();
        } catch (SQLException ignored) {
            ColorLog("[MHDF-PlayTime]无法连接数据库!");
        }

        File PluginHome = new File(String.valueOf(getDataFolder()));
        File ConfigFile = new File(PluginHome, "config.yml");

        if (!PluginHome.exists()) {
            boolean Stats = PluginHome.mkdirs();
            if (!Stats) {
                ColorLog("[MHDF-PlayTime]插件数据文件夹创建失败!");
            }
        }
        if (!ConfigFile.exists()) {
            SaveResource(this.getDataFolder().getPath(), "config.yml", "config.yml", true);
        }

        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Objects.requireNonNull(getCommand("playtime")).setExecutor(new playtime());
        Objects.requireNonNull(getCommand("playtimereload")).setExecutor(new playtimereload());
        new PlayTime().runTaskTimerAsynchronously(this, 0L, 20L);
        new RunAction().runTaskTimerAsynchronously(this, 0L, 20L);

        new PlaceholderAPI().register();

        ColorLog("&f============&6梦回东方-在线时长与奖励&f============");
        ColorLog("&e插件启动完成!");
        ColorLog("&f============&6梦回东方-在线时长与奖励&f============");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        main = null;

        new PlaceholderAPI().unregister();

        ColorLog("&f============&6梦回东方-在线时长与奖励&f============");
        ColorLog("&e插件已卸载!");
        ColorLog("&f============&6梦回东方-在线时长与奖励&f============");
    }
}

package cn.chengzhiya.mhdfplaytime;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static cn.chengzhiya.mhdfplaytime.main.dataSource;

public final class Util {
    static Map<Object, Integer> PlayTimeHashMap = new HashMap<>();

    public static String ChatColor(String Message) {
        return ChatColor.translateAlternateColorCodes('&', Message);
    }

    public static void ColorLog(String Message) {
        ConsoleCommandSender consoleCommandSender = Bukkit.getConsoleSender();
        consoleCommandSender.sendMessage(ChatColor(Message));
    }

    public static Map<Object, Integer> getPlayTimeHashMap() {
        return PlayTimeHashMap;
    }

    public static Boolean ifExists(Player player) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + main.main.getConfig().getString("DatabaseSettings.Table") + " WHERE PlayerName = ? LIMIT 1");
            ps.setString(1, player.getName());
            ResultSet rs = ps.executeQuery();
            boolean 结果 = rs.next();
            rs.close();
            ps.close();
            connection.close();
            return 结果;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void initializationPlayerData(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(main.main, () -> {
            if (!ifExists(player)) {
                try {
                    getPlayTimeHashMap().put(player.getName(), 0);
                    Connection connection = dataSource.getConnection();
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO " + main.main.getConfig().getString("DatabaseSettings.Table") + " (PlayerName, PlayTime) VALUES (?,0)");
                    ps.setString(1, player.getName());
                    ps.executeUpdate();
                    ps.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                if (getPlayTimeHashMap().get(player.getName()) == null) {
                    getPlayTimeHashMap().put(player.getName(), getPlayTime(player));
                }
            }
        });
    }

    public static void addPlayTime(Player player, int Time) {
        Bukkit.getScheduler().runTaskAsynchronously(main.main, () -> {
            if (ifExists(player)) {
                try {
                    getPlayTimeHashMap().put(player.getName(), getPlayTime(player) + Time);
                    Connection connection = dataSource.getConnection();
                    PreparedStatement ps = connection.prepareStatement("UPDATE " + main.main.getConfig().getString("DatabaseSettings.Table") + " SET PlayTime = PlayTime+? WHERE PlayerName = ?");
                    ps.setObject(1, Time);
                    ps.setString(2, player.getName());
                    ps.executeUpdate();
                    ps.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static int getPlayTime(Player player) {
        if (ifExists(player)) {
            if (getPlayTimeHashMap().get(player.getName()) == null) {
                try {
                    Connection connection = dataSource.getConnection();
                    PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + main.main.getConfig().getString("DatabaseSettings.Table") + " WHERE PlayerName = ? LIMIT 1");
                    ps.setString(1, player.getName());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        return rs.getInt("PlayTime");
                    }
                    rs.close();
                    ps.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                return getPlayTimeHashMap().get(player.getName());
            }
        }
        return 0;
    }

    public static String getPlayTimeString(Player player) {
        int totalSeconds = getPlayTime(player);
        int seconds = totalSeconds % 60;

        int totalMinutes = totalSeconds / 60;
        int minutes = totalMinutes % 60;

        int totalHours = totalMinutes / 60;
        int hours = totalHours % 24;

        int totalDays = totalHours / 24;
        int days = totalDays % 30;

        int totalMonths = totalDays / 30;
        int months = totalMonths % 12;

        int years = totalMonths / 12;

        StringBuilder sb = new StringBuilder();
        if (years > 0) {
            sb.append(years).append("年");
        }
        if (months > 0) {
            sb.append(months).append("月");
        }
        if (days > 0) {
            sb.append(days).append("日");
        }
        if (hours > 0) {
            sb.append(hours).append("时");
        }
        if (minutes > 0) {
            sb.append(minutes).append("分");
        }
        if (seconds > 0) {
            sb.append(seconds).append("秒");
        }
        return sb.toString();
    }
}



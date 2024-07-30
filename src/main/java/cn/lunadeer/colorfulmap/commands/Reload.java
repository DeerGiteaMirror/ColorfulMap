package cn.lunadeer.colorfulmap.commands;

import cn.lunadeer.colorfulmap.ColorfulMap;
import cn.lunadeer.colorfulmap.MapManager;
import cn.lunadeer.colorfulmap.utils.Notification;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Reload implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ColorfulMap.config.reload();
        MapManager.instance.reloadImages();
        Notification.info(sender, "重载成功");
        return true;
    }
}

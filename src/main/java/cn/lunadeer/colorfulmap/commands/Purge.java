package cn.lunadeer.colorfulmap.commands;

import cn.lunadeer.colorfulmap.ColorfulMap;
import cn.lunadeer.colorfulmap.MapManager;
import cn.lunadeer.colorfulmap.StorageMaps;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Purge implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ColorfulMap.instance.getServer().getWorlds().forEach(world -> {
            List<Integer> worldMapIds = new ArrayList<>();
            world.getEntities().forEach(entity -> {
                if (!(entity instanceof org.bukkit.entity.ItemFrame)) {
                    return;
                }
                ItemFrame itemFrame = (ItemFrame) entity;
                if (!itemFrame.getItem().getType().equals(org.bukkit.Material.FILLED_MAP)) {
                    return;
                }
                ItemStack item = itemFrame.getItem();
                MapView mapView = ((org.bukkit.inventory.meta.MapMeta) item.getItemMeta()).getMapView();
                if (mapView == null) {
                    return;
                }
                worldMapIds.add(mapView.getId());
            });
            List<Integer> currentIds = MapManager.instance.getMapIds(world);
            for (Integer id : currentIds) {
                if (!worldMapIds.contains(id)) {
                    MapManager.instance.removeMap(world, id);
                }
            }
        });
        StorageMaps.purgeStorageFolder();
        MapManager.instance.reloadImages();
        return true;
    }
}

package cn.lunadeer.colorfulmap.generator;

import cn.lunadeer.colorfulmap.ColorfulMap;
import cn.lunadeer.colorfulmap.StorageMaps;
import cn.lunadeer.colorfulmap.utils.ImageTool;
import cn.lunadeer.colorfulmap.utils.Notification;
import cn.lunadeer.colorfulmap.utils.UrlTools;
import cn.lunadeer.colorfulmap.utils.VaultConnect.VaultConnect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static cn.lunadeer.colorfulmap.StorageMaps.getThumbnailPath;
import static cn.lunadeer.colorfulmap.generator.ImageRenderer.getMapItemFromImageTile;

public class Multi {

    public static ItemStack generate(Player player, String url, Float scale) {
        try {
            if (!UrlTools.isInWhiteList(url)) {
                Notification.error(player, "无法生成地图画: 此图床地址不被允许使用");
                return null;
            }
            URL _url = new URL(url);
            BufferedImage raw_image = ImageIO.read(_url);
            if (raw_image == null) {
                Notification.error(player, "无法读取有效图片，请检查图片地址是否正确或者更换图床");
                return null;
            }
            BufferedImage resized_image;
            if (scale != 1.0) {
                resized_image = ImageTool.resize(raw_image, scale);
            } else {
                resized_image = raw_image;
            }
            int image_width = resized_image.getWidth();
            int image_height = resized_image.getHeight();
            int x_count = (int) Math.ceil(image_width / 128.0);
            int y_count = (int) Math.ceil(image_height / 128.0);
            if (x_count > ColorfulMap.config.getMaxFrameX() || y_count > ColorfulMap.config.getMaxFrameY()) {
                Notification.error(player, "无法生成地图画: 图片太大，分辨率不得超过" + ColorfulMap.config.getMaxFrameX() * 128 + "x" + ColorfulMap.config.getMaxFrameY() * 128);
                return null;
            }
            int new_width = x_count * 128;
            int new_height = y_count * 128;
            resized_image = ImageTool.center(resized_image, new_width, new_height);
            image_width = resized_image.getWidth();
            image_height = resized_image.getHeight();
            List<BufferedImage> split_images = new ArrayList<>();
            for (int y = 0; y < y_count; y++) {
                for (int x = 0; x < x_count; x++) {
                    int width = Math.min(128, image_width - x * 128);
                    int height = Math.min(128, image_height - y * 128);
                    BufferedImage sub_image = resized_image.getSubimage(x * 128, y * 128, width, height);
                    split_images.add(sub_image);
                }
            }
            if (split_images.size() == 0) {
                Notification.error(player, "无法生成地图画: 图片为空");
                return null;
            }

            UUID map_images_uuid = StorageMaps.save(player, raw_image, ImageTool.thumb(raw_image), split_images, x_count, y_count);
            if (map_images_uuid == null) {
                Notification.error(player, "无法生成地图画: 无法保存图片");
                return null;
            }
            ItemStack mapItem = getMapItemFromImageTile(player, getThumbnailPath(map_images_uuid));
            if (mapItem == null) {
                Notification.error(player, "无法生成地图画: 无法生成地图");
                return null;
            }
            MapMeta meta = (MapMeta) mapItem.getItemMeta();
            List<Component> lore = new ArrayList<>();
            String size_info = "size: " + x_count + "x" + y_count;
            lore.add(Component.text(size_info).hoverEvent(Component.text(map_images_uuid.toString())));
            meta.lore(lore);
            mapItem.setItemMeta(meta);
            return mapItem;
        } catch (Exception e) {
            Notification.error(player, "无法生成地图画: " + e.getMessage());
            return null;
        }
    }
}

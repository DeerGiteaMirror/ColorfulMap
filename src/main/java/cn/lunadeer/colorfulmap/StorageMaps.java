package cn.lunadeer.colorfulmap;

import cn.lunadeer.colorfulmap.utils.Notification;
import cn.lunadeer.colorfulmap.utils.XLogger;
import org.bukkit.entity.Player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StorageMaps {
    private static final File data_folder = ColorfulMap.instance.getDataFolder();

    /**
     * save images to plugins/ColorfulMap/maps/{map_uuid}/x_y.png
     *
     * @param player  player
     * @param images  images
     * @param x_count x_count
     * @param y_count y_count
     */
    public static UUID save(Player player, BufferedImage raw, BufferedImage thumb,
                            List<BufferedImage> images, int x_count, int y_count) {
        if (images.size() != x_count * y_count) {
            return null;
        }
        UUID map_uuid = UUID.randomUUID();
        File map_folder = new File(data_folder, "maps/" + map_uuid);
        if (!map_folder.exists()) {
            if (!map_folder.mkdirs()) {
                Notification.error(player, "Failed to save map: failed to create map folder");
                return null;
            }
        }
        File raw_file = new File(map_folder, "raw.png");
        File thumb_file = new File(map_folder, "thumb.png");
        try {
            ImageIO.write(raw, "png", raw_file);
            ImageIO.write(thumb, "png", thumb_file);
        } catch (Exception e) {
            Notification.error(player, "Failed to save map: " + e.getMessage());
            return null;
        }
        for (int y = 0; y < y_count; y++) {
            for (int x = 0; x < x_count; x++) {
                BufferedImage image = images.get(y * x_count + x);
                File image_file = new File(map_folder, x + "_" + y + ".png");
                try {
                    ImageIO.write(image, "png", image_file);
                } catch (Exception e) {
                    Notification.error(player, "Failed to save map: " + e.getMessage());
                    return null;
                }
            }
        }
        return map_uuid;
    }

    public static String getThumbnailPath(UUID map_uuid) {
        return "maps/" + map_uuid + "/thumb.png";
    }

    public static String getImageTilePath(UUID map_uuid, int x, int y) {
        return "maps/" + map_uuid + "/" + x + "_" + y + ".png";
    }

    public static void writeMeta(UUID map_uuid, List<String> info) {
        File meta_file = new File(data_folder, "maps/" + map_uuid + "/meta.txt");
        try {
            java.nio.file.Files.write(meta_file.toPath(), info);
        } catch (Exception e) {
            XLogger.err("写入地图画操作记录失败：%s", e.getMessage());
        }
    }

    /**
     * load images from plugins/ColorfulMap/maps/{map_uuid}/x_y.png
     *
     * @param player   player
     * @param map_uuid map_uuid
     * @param x_count  x_count
     * @param y_count  y_count
     * @return images
     */
    public static List<BufferedImage> load(Player player, UUID map_uuid, int x_count, int y_count) {
        List<BufferedImage> images = new ArrayList<>();
        for (int y = 0; y < y_count; y++) {
            for (int x = 0; x < x_count; x++) {
                File image_file = new File(data_folder, "maps/" + map_uuid + "/" + x + "_" + y + ".png");
                try {
                    BufferedImage image = ImageIO.read(image_file);
                    images.add(image);
                } catch (Exception e) {
                    Notification.error(player, "Failed to load map: " + e.getMessage());
                    return null;
                }
            }
        }
        return images;
    }

    public static BufferedImage load(String path) {
        File image_file = new File(data_folder, path);
        try {
            return ImageIO.read(image_file);
        } catch (Exception e) {
            return null;
        }
    }
}

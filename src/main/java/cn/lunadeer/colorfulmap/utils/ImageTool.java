package cn.lunadeer.colorfulmap.utils;

import java.awt.image.BufferedImage;

public class ImageTool {

    public static BufferedImage resize(BufferedImage image, float scale) {
        int new_width = (int) (image.getWidth() * scale);
        int new_height = (int) (image.getHeight() * scale);
        BufferedImage newImage = new BufferedImage(new_width, new_height, BufferedImage.TYPE_INT_ARGB);
        newImage.getGraphics().drawImage(image, 0, 0, new_width, new_height, null);
        return newImage;
    }

    public static BufferedImage center(BufferedImage image, int width, int height) {
        int image_width = image.getWidth();
        int image_height = image.getHeight();
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        newImage.getGraphics().drawImage(image, (width - image_width) / 2, (height - image_height) / 2, null);
        return newImage;
    }

    public static BufferedImage thumb(BufferedImage img) {
        double scale;
        if (img.getWidth() > img.getHeight()) {
            scale = 128.0 / img.getWidth();
        } else {
            scale = 128.0 / img.getHeight();
        }
        return center(resize(img, (float) scale), 128, 128);
    }

}

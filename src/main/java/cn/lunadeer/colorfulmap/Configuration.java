package cn.lunadeer.colorfulmap;

import cn.lunadeer.colorfulmap.utils.VaultConnect.VaultConnect;
import cn.lunadeer.colorfulmap.utils.XLogger;
import org.bukkit.configuration.file.FileConfiguration;

public class Configuration {

    public Configuration(ColorfulMap plugin) {
        _plugin = plugin;
        _plugin.saveDefaultConfig();
        reload();
        _plugin.saveConfig();
    }

    public void reload() {
        _plugin.reloadConfig();
        _file = _plugin.getConfig();
        _debug = _file.getBoolean("Debug", false);
        _max_frame_x = _file.getInt("MaxFrameX", 32);
        _max_frame_y = _file.getInt("MaxFrameY", 18);
        _check_update = _file.getBoolean("CheckUpdate", true);
        _economy_enable = _file.getBoolean("Economy.Enable", false);
        _price = (float) _file.getDouble("Economy.CostPerMap", 100.0);
        if (_economy_enable) {
            XLogger.info("已启用经济系统");
            new VaultConnect(_plugin);
        }
    }

    public void saveAll() {
        _file.set("Debug", _debug);
        _file.set("MaxFrameX", _max_frame_x);
        _file.set("MaxFrameY", _max_frame_y);
        _file.set("CheckUpdate", _check_update);
        _file.set("Economy.Enable", _economy_enable);
        _file.set("Economy.CostPerMap", _price);
        _plugin.saveConfig();
    }

    public Boolean isDebug() {
        return _debug;
    }

    public void setDebug(Boolean debug) {
        _debug = debug;
        _file.set("Debug", debug);
        _plugin.saveConfig();
    }

    public Integer getMaxFrameX() {
        return _max_frame_x;
    }

    public void setMaxFrameX(Integer max_frame_x) {
        _max_frame_x = max_frame_x;
        _file.set("MaxFrameX", max_frame_x);
        _plugin.saveConfig();
    }

    public Integer getMaxFrameY() {
        return _max_frame_y;
    }

    public void setMaxFrameY(Integer max_frame_y) {
        _max_frame_y = max_frame_y;
        _file.set("MaxFrameY", max_frame_y);
        _plugin.saveConfig();
    }

    public Boolean isCheckUpdate() {
        return _check_update;
    }

    public void setCheckUpdate(Boolean check_update) {
        _check_update = check_update;
        _file.set("CheckUpdate", check_update);
        _plugin.saveConfig();
    }

    public Boolean isEconomyEnable() {
        return _economy_enable;
    }

    public void setEconomyEnable(Boolean economy_enable) {
        _economy_enable = economy_enable;
        _file.set("Economy.Enable", economy_enable);
        _plugin.saveConfig();
    }

    public Float getPrice() {
        return _price;
    }

    public void setPrice(Float price) {
        _price = price;
        _file.set("Economy.CostPerMap", price);
        _plugin.saveConfig();
    }

    private final ColorfulMap _plugin;
    private FileConfiguration _file;
    private Boolean _debug;
    private Integer _max_frame_x;
    private Integer _max_frame_y;
    private Boolean _check_update;
    private Boolean _economy_enable;
    private Float _price;
}

package cn.lunadeer.colorfulmap.utils.VaultConnect;

import cn.lunadeer.colorfulmap.utils.XLogger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServiceRegisterEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class VaultConnect implements Listener {

    public static VaultConnect instance;
    private VaultInterface vaultInstance = null;
    private JavaPlugin plugin;

    public VaultConnect(JavaPlugin plugin) {
        this.plugin = plugin;
        instance = this;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEnable(ServiceRegisterEvent event) {
    }

    public boolean economyAvailable() {
        if (vaultInstance == null) {
            if (foundClass("net.milkbowl.vault.economy.Economy")) {
                vaultInstance = new Vault();
            } else if (foundClass("net.milkbowl.vault2.economy.Economy")) {
                vaultInstance = new Vault2();
            } else {
                XLogger.err("没有可用的经济插件");
                return false;
            }
            if (!vaultInstance.init(plugin)) {
                vaultInstance = null;
                XLogger.err("没有可用的经济插件");
                return false;
            }
        }
        return true;
    }

    public String currencyNamePlural() {
        if (economyAvailable()) {
            return vaultInstance.currencyNamePlural();
        }
        XLogger.warn("没有可用的经济插件");
        return "";
    }

    public String currencyNameSingular() {
        if (economyAvailable()) {
            return vaultInstance.currencyNameSingular();
        }
        XLogger.warn("没有可用的经济插件");
        return "";
    }

    public void withdrawPlayer(Player player, double amount) {
        if (economyAvailable()) {
            vaultInstance.withdrawPlayer(player, amount);
            return;
        }
        XLogger.warn("没有可用的经济插件");
    }

    public void depositPlayer(Player player, double amount) {
        if (economyAvailable()) {
            vaultInstance.depositPlayer(player, amount);
            return;
        }
        XLogger.warn("没有可用的经济插件");
    }

    public double getBalance(Player player) {
        if (economyAvailable()) {
            return vaultInstance.getBalance(player);
        }
        XLogger.warn("没有可用的经济插件");
        return 0;
    }

    private static boolean foundClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}

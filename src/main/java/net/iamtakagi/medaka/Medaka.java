package net.iamtakagi.medaka;

import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public final class Medaka {


    @Getter
    private final static Map<UUID, Menu> currentlyOpenedMenus = new HashMap<>();

    @Getter
    private static BukkitTask menuUpdateTask;

    /**
     * Medaka.init(org.bukkit.plugin.Plugin);
     * @param plugin
     */
    public static void init (Plugin plugin) {
        plugin.getLogger().info("[medaka] 初期化中...");
        plugin.getLogger().info("[medaka] イベントリスナーを登録しています...");
        plugin.getServer().getPluginManager().registerEvents(new MenuListener(plugin), plugin);
        plugin.getLogger().info("[medaka] イベントリスナーを登録しました");
        plugin.getLogger().info("[medaka] MenuUpdateRunnable を起動しています...");
        menuUpdateTask = plugin.getServer().getScheduler().runTaskTimer(plugin, new MenuUpdateRunnable(), 0L, 1L);
        plugin.getLogger().info("[medaka] MenuUpdateRunnable を起動しました");
        plugin.getLogger().info("[medaka] 初期化が完了しました");
    }
}

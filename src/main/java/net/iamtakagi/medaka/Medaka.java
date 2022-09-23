package net.iamtakagi.medaka;

import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 外部提供される静的中核クラス
 */
public class Medaka {

    /**
     * 現在プレイヤーが開いているメニューを格納する HashMap
     * キーは Player UUID で管理される
     */
    public static Map<UUID, Menu> currentlyOpenedMenus = new HashMap<>();

    /**
     * 初期化処理を行う関数
     * 外部から Medaka.init(org.bukkit.plugin.Plugin); で呼び出すことで処理可能
     * @param plugin
     */
    public static void init (Plugin plugin) {
        plugin.getLogger().info("[medaka] 初期化中...");
        plugin.getLogger().info("[medaka] MenuUpdateRunnable を起動しています");
        plugin.getServer().getScheduler().runTaskTimer(plugin, new MenuUpdateRunnable(), 20L, 20L);
        plugin.getLogger().info("[medaka] MenuUpdateRunnable を起動しました");
        plugin.getLogger().info("[medaka] 初期化が完了しました");
    }
}

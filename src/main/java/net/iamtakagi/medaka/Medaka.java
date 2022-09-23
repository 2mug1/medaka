package net.iamtakagi.medaka;

import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 外部提供される静的中核クラス
 */
public final class Medaka {

    /**
     * 現在プレイヤーが開いているメニューを格納する HashMap
     * キーは Player UUID で管理される
     */
    @Getter
    private final static Map<UUID, Menu> currentlyOpenedMenus = new HashMap<>();

    /**
     * 初期化処理を行う関数
     * 外部から Medaka.init(org.bukkit.plugin.Plugin); を呼び出すことで処理される
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

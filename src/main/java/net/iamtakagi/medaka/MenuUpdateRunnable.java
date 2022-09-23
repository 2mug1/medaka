package net.iamtakagi.medaka;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MenuUpdateRunnable implements Runnable {

    @Override
    public void run() {
        Medaka.getCurrentlyOpenedMenus().forEach((key, value) -> {
            final Player player = Bukkit.getPlayer(key);
            if (player != null) {
                if (value.isAutoUpdate()) {
                    value.openMenu(player);
                }
            }
        });
    }
}
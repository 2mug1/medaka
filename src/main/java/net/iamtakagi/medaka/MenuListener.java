package net.iamtakagi.medaka;

import net.iamtakagi.iroha.strapped.StrappedListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;

public class MenuListener extends StrappedListener {

	public MenuListener(Plugin instance) {
		super(instance);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onButtonPress(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Menu openMenu = Medaka.getCurrentlyOpenedMenus().get(player.getUniqueId());

		if (openMenu != null) {
			if (event.getSlot() != event.getRawSlot()) {
				if ((event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT)) {
					event.setCancelled(true);
				}

				return;
			}

			if (openMenu.getButtons().containsKey(event.getSlot())) {
				Button button = openMenu.getButtons().get(event.getSlot());
				boolean cancel = button.shouldCancel(player, event.getClick());

				if (!cancel && (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT)) {
					event.setCancelled(true);

					if (event.getCurrentItem() != null) {
						player.getInventory().addItem(event.getCurrentItem());
					}
				} else {
					event.setCancelled(cancel);
				}

				button.clicked(player, event.getClick());
				button.clicked(player, event.getSlot(), event.getClick(), event.getHotbarButton());

				if (Medaka.getCurrentlyOpenedMenus().containsKey(player.getUniqueId())) {
					Menu newMenu = Medaka.getCurrentlyOpenedMenus().get(player.getUniqueId());

					if (newMenu == openMenu) {
						boolean buttonUpdate = button.shouldUpdate(player, event.getClick());

						if ((newMenu.isUpdateAfterClick() && buttonUpdate) || buttonUpdate) {
							openMenu.setClosedByMenu(true);
							newMenu.openMenu(player);
						}
					}
				} else if (button.shouldUpdate(player, event.getClick())) {
					openMenu.setClosedByMenu(true);
					openMenu.openMenu(player);
				}

				if (event.isCancelled()) {
					Bukkit.getScheduler().runTaskLater(instance, player::updateInventory, 1L);
				}
			} else {
				if (event.getCurrentItem() != null) {
					event.setCancelled(true);
				}

				if ((event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT)) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInventoryClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		Menu openMenu = Medaka.getCurrentlyOpenedMenus().get(player.getUniqueId());

		if (openMenu != null) {
			openMenu.onClose(player);

			Medaka.getCurrentlyOpenedMenus().remove(player.getUniqueId());
		}
	}

}
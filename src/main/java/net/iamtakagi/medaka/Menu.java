package net.iamtakagi.medaka;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.iamtakagi.iroha.Style;
import net.iamtakagi.iroha.strapped.Strapped;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

@Getter
@Setter
public abstract class Menu extends Strapped {

	public static Map<UUID, Menu> currentlyOpenedMenus = new HashMap<>();

	public static void init (Plugin plugin) {
		plugin.getLogger().info("[medaka] 初期化中...");
		plugin.getLogger().info("[medaka] MenuUpdateRunnable を起動しています");
		plugin.getServer().getScheduler().runTaskTimer(plugin, new MenuUpdateRunnable(), 20L, 20L);
		plugin.getLogger().info("[medaka] MenuUpdateRunnable を起動しました");
		plugin.getLogger().info("[medaka] 初期化が完了しました");
	}

	private Map<Integer, Button> buttons = new HashMap<>();
	private boolean autoUpdate = false;
	private boolean updateAfterClick = true;
	private boolean closedByMenu = false;
	private boolean placeholder = false;
	private Button placeholderButton = Button.placeholder(this.instance, Material.LEGACY_STAINED_GLASS_PANE, (byte) 15, " ");

	public Menu(Plugin instance) {
		super(instance);
	}

	private ItemStack createItemStack(Player player, Button button) {
		ItemStack item = button.getButtonItem(player);

		if (item.getType() != Material.LEGACY_SKULL_ITEM) {
			ItemMeta meta = item.getItemMeta();

			if (meta != null && meta.hasDisplayName()) {
				meta.setDisplayName(meta.getDisplayName() + "§b§c§d§e");
			}

			item.setItemMeta(meta);
		}

		return item;
	}

	public void openMenu(final Player player) {
		this.buttons = this.getButtons(player);

		Menu previousMenu = Menu.currentlyOpenedMenus.get(player.getUniqueId());
		Inventory inventory = null;
		int size = this.getSize() == -1 ? this.size(this.buttons) : this.getSize();
		boolean update = false;
		String title = Style.translate(this.getTitle(player));

		if (title.length() > 32) {
			title = title.substring(0, 32);
		}

		if (player.getOpenInventory() != null) {
			if (previousMenu == null) {
				player.closeInventory();
			} else {
				int previousSize = player.getOpenInventory().getTopInventory().getSize();

				if (previousSize == size && player.getOpenInventory().getTitle().equals(title)) {
					inventory = player.getOpenInventory().getTopInventory();
					update = true;
				} else {
					previousMenu.setClosedByMenu(true);
					player.closeInventory();
				}
			}
		}

		if (inventory == null) {
			inventory = Bukkit.createInventory(player, size, title);
		}

		inventory.setContents(new ItemStack[inventory.getSize()]);

		currentlyOpenedMenus.put(player.getUniqueId(), this);

		for (Map.Entry<Integer, Button> buttonEntry : this.buttons.entrySet()) {
			inventory.setItem(buttonEntry.getKey(), createItemStack(player, buttonEntry.getValue()));
		}

		if (this.isPlaceholder()) {
			for (int index = 0; index < size; index++) {
				if (this.buttons.get(index) == null) {
					this.buttons.put(index, this.placeholderButton);
					inventory.setItem(index, this.placeholderButton.getButtonItem(player));
				}
			}
		}

		if (update) {
			player.updateInventory();
		} else {
			player.openInventory(inventory);
		}

		this.onOpen(player);
		this.setClosedByMenu(false);
	}

	public void openMenu(final Player player, int size) {
		this.buttons = this.getButtons(player);

		Menu previousMenu = Menu.currentlyOpenedMenus.get(player.getUniqueId());
		Inventory inventory = null;
		boolean update = false;
		String title = Style.translate(this.getTitle(player));

		if (title.length() > 32) {
			title = title.substring(0, 32);
		}

		if (player.getOpenInventory() != null) {
			if (previousMenu == null) {
				player.closeInventory();
			} else {
				int previousSize = player.getOpenInventory().getTopInventory().getSize();

				if (previousSize == size && player.getOpenInventory().getTitle().equals(title)) {
					inventory = player.getOpenInventory().getTopInventory();
					update = true;
				} else {
					previousMenu.setClosedByMenu(true);
					player.closeInventory();
				}
			}
		}

		if (inventory == null) {
			inventory = Bukkit.createInventory(player, size, title);
		}

		inventory.setContents(new ItemStack[inventory.getSize()]);

		currentlyOpenedMenus.put(player.getUniqueId(), this);

		for (Map.Entry<Integer, Button> buttonEntry : this.buttons.entrySet()) {
			inventory.setItem(buttonEntry.getKey(), createItemStack(player, buttonEntry.getValue()));
		}

		if (this.isPlaceholder()) {
			for (int index = 0; index < size; index++) {
				if (this.buttons.get(index) == null) {
					this.buttons.put(index, this.placeholderButton);
					inventory.setItem(index, this.placeholderButton.getButtonItem(player));
				}
			}
		}

		if (update) {
			player.updateInventory();
		} else {
			player.openInventory(inventory);
		}

		this.onOpen(player);
		this.setClosedByMenu(false);
	}

	public int size(Map<Integer, Button> buttons) {
		int highest = 0;

		for (int buttonValue : buttons.keySet()) {
			if (buttonValue > highest) {
				highest = buttonValue;
			}
		}

		return (int) (Math.ceil((highest + 1) / 9D) * 9D);
	}

	public int getSize() {
		return -1;
	}

	public int getSlot(int x, int y) {
		return ((9 * y) + x);
	}

	public abstract String getTitle(Player player);

	public abstract Map<Integer, Button> getButtons(Player player);

	public void onOpen(Player player) {
	}

	public void onClose(Player player) {
	}

}

package net.iamtakagi.medaka;

import net.iamtakagi.iroha.strapped.Strapped;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public abstract class Button extends Strapped {

	public Button(Plugin instance) {
		super(instance);
	}

	public static Button placeholder(Plugin instance, final Material material, final byte data, String... title) {
		return (new Button(instance) {
			public ItemStack getButtonItem(Player player) {
				ItemStack it = new ItemStack(material, 1, data);
				ItemMeta meta = it.getItemMeta();

				meta.setDisplayName(StringUtils.join(title));
				it.setItemMeta(meta);

				return it;
			}
		});
	}

	public static void playFail(Player player) {
		player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 20F, 0.1F);
	}

	public static void playSuccess(Player player) {
		player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20F, 15F);
	}

	public static void playNeutral(Player player) {
		player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 20F, 1F);
	}

	public abstract ItemStack getButtonItem(Player player);

	public void clicked(Player player, ClickType clickType) {}

	public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {}

	public boolean shouldCancel(Player player, ClickType clickType) {
		return true;
	}

	public boolean shouldUpdate(Player player, ClickType clickType) {
		return false;
	}

}
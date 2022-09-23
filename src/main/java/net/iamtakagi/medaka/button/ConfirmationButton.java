package net.iamtakagi.medaka.button;

import net.iamtakagi.iroha.callback.TypeCallback;
import net.iamtakagi.medaka.Button;
import net.iamtakagi.medaka.Medaka;
import net.iamtakagi.medaka.Menu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class ConfirmationButton extends Button {

	private boolean confirm;
	private TypeCallback<Boolean> callback;
	private boolean closeAfterResponse;
	private String confirmButtonName = "Confirm";
	private String cancelButtonName = "Cancel";

	public ConfirmationButton(Plugin instance, boolean confirm, TypeCallback<Boolean> callback, boolean closeAfterResponse){
		super(instance);
		this.confirm = confirm;
		this.callback = callback;
		this.closeAfterResponse = closeAfterResponse;
	}

	public ConfirmationButton(Plugin instance, boolean confirm, TypeCallback<Boolean> callback, boolean closeAfterResponse, String confirmButtonName, String cancelButtonName){
		super(instance);
		this.confirm = confirm;
		this.callback = callback;
		this.closeAfterResponse = closeAfterResponse;
		this.confirmButtonName = confirmButtonName;
		this.cancelButtonName = cancelButtonName;
	}

	@Override
	public ItemStack getButtonItem(Player player) {
		ItemStack itemStack = new ItemStack(Material.LEGACY_WOOL, 1, this.confirm ? ((byte) 5) : ((byte) 14));
		ItemMeta itemMeta = itemStack.getItemMeta();

		itemMeta.setDisplayName(this.confirm ? ChatColor.GREEN + confirmButtonName : ChatColor.RED + cancelButtonName);
		itemStack.setItemMeta(itemMeta);

		return itemStack;
	}

	@Override
	public void clicked(Player player, ClickType clickType) {
		if (this.confirm) {
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 20f, 0.1f);
		} else {
			player.playSound(player.getLocation(), Sound.BLOCK_ROOTED_DIRT_BREAK, 20f, 0.1F);
		}

		if (this.closeAfterResponse) {
			Menu menu = Medaka.getCurrentlyOpenedMenus().get(player.getUniqueId());

			if (menu != null) {
				menu.setClosedByMenu(true);
			}

			player.closeInventory();
		}

		this.callback.callback(this.confirm);
	}

}

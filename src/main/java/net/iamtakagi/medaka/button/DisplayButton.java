package net.iamtakagi.medaka.button;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.iamtakagi.medaka.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

@Getter
@Setter
public class DisplayButton extends Button {

	private ItemStack itemStack;
	private boolean cancel;

	public DisplayButton(Plugin instance, ItemStack itemStack, boolean cancel) {
		super(instance);
		this.itemStack = itemStack;
		this.cancel = cancel;
	}

	@Override
	public ItemStack getButtonItem(Player player) {
		if (this.itemStack == null) {
			return new ItemStack(Material.AIR);
		} else {
			return this.itemStack;
		}
	}

	@Override
	public boolean shouldCancel(Player player, ClickType clickType) {
		return cancel;
	}
}

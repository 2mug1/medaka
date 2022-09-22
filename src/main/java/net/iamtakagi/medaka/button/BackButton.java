package net.iamtakagi.medaka.button;

import java.util.Arrays;

import net.iamtakagi.iroha.ItemBuilder;
import net.iamtakagi.iroha.Style;
import net.iamtakagi.medaka.Button;
import net.iamtakagi.medaka.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class BackButton extends Button {

	private Menu back;
	private int size = 0;

	public BackButton(Plugin instance, Menu back){
		super(instance);
		this.back = back;
	}

	public BackButton(Plugin instance, Menu back, int size){
		super(instance);
		this.back = back;
		this.size = size;
	}


	@Override
	public ItemStack getButtonItem(Player player) {
		return new ItemBuilder(Material.LEGACY_WOOD_DOOR)
				.name(Style.RED + Style.BOLD + "Back")
				.lore(Arrays.asList(
						Style.RED + "Click here to return to",
						Style.RED + "the previous menu.")
				)
				.build();
	}

	@Override
	public void clicked(Player player, ClickType clickType) {
		Button.playNeutral(player);
		if(size != 0) {
			back.openMenu(player, size);
		}else{
			back.openMenu(player);
		}
	}

}

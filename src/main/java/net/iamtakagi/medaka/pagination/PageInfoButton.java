package net.iamtakagi.medaka.pagination;

import lombok.AllArgsConstructor;
import net.iamtakagi.iroha.ItemBuilder;
import net.iamtakagi.iroha.strapped.Strapped;
import net.iamtakagi.medaka.Button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class PageInfoButton extends Button {

	private PaginatedMenu menu;

	public PageInfoButton(Plugin instance, PaginatedMenu menu) {
		super(instance);
		this.menu = menu;
	}

	@Override
	public ItemStack getButtonItem(Player player) {
		int pages = menu.getPages(player);

		return new ItemBuilder(Material.PAPER)
				.name(ChatColor.GOLD + "Page Info")
				.lore(
						ChatColor.YELLOW + "You are viewing page #" + menu.getPage() + ".",
						ChatColor.YELLOW + (pages == 1 ? "There is 1 page." : "There are " + pages + " pages."),
						"",
						ChatColor.YELLOW + "Middle click here to",
						ChatColor.YELLOW + "view all pages."
				)
				.build();
	}

	@Override
	public void clicked(Player player, ClickType clickType) {
		if (clickType == ClickType.RIGHT) {
			new ViewAllPagesMenu(this.instance, this.menu).openMenu(player);
			playNeutral(player);
		}
	}

}

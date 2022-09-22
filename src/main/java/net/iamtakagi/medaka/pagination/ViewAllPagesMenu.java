package net.iamtakagi.medaka.pagination;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.iamtakagi.medaka.Button;
import net.iamtakagi.medaka.Menu;
import net.iamtakagi.medaka.button.BackButton;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ViewAllPagesMenu extends Menu {

	@NonNull
	@Getter
	PaginatedMenu menu;

	{
		setAutoUpdate(true);
	}

	public ViewAllPagesMenu(Plugin instance, PaginatedMenu menu) {
		super(instance);
		this.menu = menu;
	}

	@Override
	public String getTitle(Player player) {
		return "Jump to page";
	}

	@Override
	public Map<Integer, Button> getButtons(Player player) {
		HashMap<Integer, Button> buttons = new HashMap<>();

		buttons.put(0, new BackButton(this.instance, menu));

		int index = 10;

		for (int i = 1; i <= menu.getPages(player); i++) {
			buttons.put(index++, new JumpToPageButton(this.instance, i, menu, menu.getPage() == i));

			if ((index - 8) % 9 == 0) {
				index += 2;
			}
		}

		return buttons;
	}

	@Override
	public boolean isAutoUpdate() {
		return true;
	}

}

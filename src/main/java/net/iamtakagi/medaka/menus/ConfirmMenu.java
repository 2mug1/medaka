package net.iamtakagi.medaka.menus;

import java.util.HashMap;
import java.util.Map;

import net.iamtakagi.iroha.callback.TypeCallback;
import net.iamtakagi.medaka.Button;
import net.iamtakagi.medaka.Menu;
import net.iamtakagi.medaka.button.ConfirmationButton;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ConfirmMenu extends Menu {

	private String title;
	private TypeCallback<Boolean> response;
	private boolean closeAfterResponse;
	private Button[] centerButtons;
	private String confirmButtonName = "Confirm";
	private String cancelButtonName = "Cancel";

	public ConfirmMenu(Plugin instance, String title, TypeCallback<Boolean> response, boolean closeAfter, Button... centerButtons) {
		super(instance);
		this.title = title;
		this.response = response;
		this.closeAfterResponse = closeAfter;
		this.centerButtons = centerButtons;
	}

	public ConfirmMenu(Plugin instance, String title, TypeCallback<Boolean> response, boolean closeAfter, String confirmButtonName, String cancelButtonName, Button... centerButtons) {
		super(instance);
		this.title = title;
		this.response = response;
		this.closeAfterResponse = closeAfter;
		this.centerButtons = centerButtons;
		this.confirmButtonName = confirmButtonName;
		this.cancelButtonName = cancelButtonName;
	}

	@Override
	public Map<Integer, Button> getButtons(Player player) {
		HashMap<Integer, Button> buttons = new HashMap<>();

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				buttons.put(getSlot(x, y), new ConfirmationButton(instance, true, response, closeAfterResponse, confirmButtonName, cancelButtonName));
				buttons.put(getSlot(8 - x, y), new ConfirmationButton(instance,false, response, closeAfterResponse, confirmButtonName, cancelButtonName));
			}
		}

		if (centerButtons != null) {
			for (int i = 0; i < centerButtons.length; i++) {
				if (centerButtons[i] != null) {
					buttons.put(getSlot(4, i), centerButtons[i]);
				}
			}
		}

		return buttons;
	}

	@Override
	public String getTitle(Player player) {
		return title;
	}

}

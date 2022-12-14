package net.iamtakagi.medaka;

import lombok.AllArgsConstructor;
import net.iamtakagi.iroha.*;
import net.iamtakagi.medaka.button.DisplayButton;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

import java.util.*;

public class ViewPlayerMenu extends Menu {

    private Player target;

    public ViewPlayerMenu(Plugin instance, Player target) {
        super(instance);
        this.target = target;
        this.setAutoUpdate(true);
    }

    @Override
    public String getTitle(Player player) {
        return Style.GOLD + this.target.getName() + "'s Inventory";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        final Map<Integer, Button> buttons = new HashMap<>();

        if (player == null) {
            return buttons;
        }

        final ItemStack[] fixedContents = InventoryUtil.fixInventoryOrder(this.target.getInventory().getContents());

        for (int i = 0; i < fixedContents.length; i++) {
            final ItemStack itemStack = fixedContents[i];

            if (itemStack == null || itemStack.getType() == Material.AIR) {
                continue;
            }

            buttons.put(i, new DisplayButton(this.instance, itemStack, true));
        }

        for (int i = 0; i < this.target.getInventory().getArmorContents().length; i++) {
            ItemStack itemStack = this.target.getInventory().getArmorContents()[i];

            if (itemStack != null && itemStack.getType() != Material.AIR) {
                buttons.put(39 - i, new DisplayButton(this.instance, itemStack, true));
            }
        }

        int pos = 45;

        buttons.put(
                pos++,
                new HealthButton(this.instance, this.target.getHealth() == 0 ? 0 : (int) Math.round(this.target.getHealth() / 2))
        );
        buttons.put(pos++, new HungerButton(this.instance, this.target.getFoodLevel()));
        buttons.put(pos, new EffectsButton(this.instance, this.target.getActivePotionEffects()));

        return buttons;
    }

    private class HealthButton extends Button {

        private int health;

        public HealthButton(Plugin instance, int health) {
            super(instance);
            this.health = health;
        }

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.MELON)
                    .name(Style.YELLOW + Style.BOLD + "Health: " + Style.PINK + this.health + "/10 " + Style.UNICODE_HEART)
                    .amount(this.health == 0 ? 1 : this.health)
                    .build();
        }

    }

    private class HungerButton extends Button {

        private int hunger;

        public HungerButton(Plugin instance,  int hunger) {
            super(instance);
            this.hunger = hunger;
        }

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.COOKED_BEEF)
                    .name(Style.YELLOW + Style.BOLD + "Hunger: " + Style.PINK + this.hunger + "/20")
                    .amount(this.hunger == 0 ? 1 : this.hunger)
                    .build();
        }

    }

    private class EffectsButton extends Button {

        private Collection<PotionEffect> effects;

        public EffectsButton(Plugin instance, Collection<PotionEffect> effects) {
            super(instance);
            this.effects = effects;
        }

        @Override
        public ItemStack getButtonItem(Player player) {
            final ItemBuilder
                    builder = new ItemBuilder(Material.POTION).name(Style.YELLOW + Style.BOLD + "Potion Effects");

            if (this.effects.isEmpty()) {
                builder.lore(Style.GRAY + "No effects");
            } else {
                final List<String> lore = new ArrayList<>();

                this.effects.forEach(effect -> {
                    final String name = BukkitUtil.getName(effect.getType()) + " " + (effect.getAmplifier() + 1);
                    final String duration = " (" + TimeUtil.millisToTimer((effect.getDuration() / 20) * 1000) + ")";

                    lore.add(Style.PINK + name + duration);
                });

                builder.lore(lore);
            }

            return builder.build();
        }

    }

}

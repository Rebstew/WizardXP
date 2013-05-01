package fr.iwebster.wizardxp;

import java.util.ListIterator;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WizardXPEventsHandler {

	private WizardXP plugin;
	
	public WizardXPEventsHandler(WizardXP plugin){
		this.plugin = plugin;
	}
	public void PlayerInteractEventHandler(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		// if player clicked with a stick, try to make one bottle
		if (p.getItemInHand().getTypeId() == 280) {
			if (p.hasPermission("wizardxp.single")) {
				plugin.addBottles(p, 1);
			} else p.sendMessage(ChatColor.RED+"You're not a powerful enough wizard to convey a single Bottle O' Enchanting");

		}
		// if player clicked with a redstone torch, try to make a stack of
		// bottles
		if (p.getItemInHand().getTypeId() == 76) {
			if (p.hasPermission("wizardxp.stack")) {
				plugin.addBottles(p, 64);
			} else p.sendMessage(ChatColor.RED+"You're not a powerful enough wizard to convey a stack of Bottle O' Enchanting");

		}
		// if player clicked with a blaze rod, try to make to max of bottles
		if (p.getItemInHand().getTypeId() == 369) {
			if (p.hasPermission("wizardxp.fill")) {
				// gets the amount of glass bottles the player has
				int bottles = 0;
				ListIterator<ItemStack> it = p.getInventory().iterator();
				while (it.hasNext()) {
					ItemStack current = it.next();
					if (current != null && current.getType() == Material.GLASS_BOTTLE) {
						bottles += current.getAmount();
						
					}
				}

					plugin.addBottles(p, bottles);
			} else p.sendMessage(ChatColor.RED+"You're not a powerful enough wizard to convey your inventory of Bottle O' Enchanting");
		}

	}
}

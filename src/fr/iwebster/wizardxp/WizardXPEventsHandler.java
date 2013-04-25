package fr.iwebster.wizardxp;

import java.util.ListIterator;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WizardXPEventsHandler {

	private WizardXP plugin;
	
	public WizardXPEventsHandler(WizardXP plugin){
		this.plugin = plugin;
		System.out.println("Handler started.");
	}
	public void PlayerInteractEventHandler(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		// if player clicked with a stick, try to make one bottle
		if (p.getItemInHand().getTypeId() == 280) {
			System.out.println("Clicked with a stick");
			//if (p.hasPermission("wizardxp.single")) {
				plugin.addBottles(p, 1);
			//}

		}
		// if player clicked with a redstone torch, try to make a stack of
		// bottles
		if (p.getItemInHand().getTypeId() == 76) {
			//if (p.hasPermission("wizardxp.stack")) {
			System.out.println("Clicked with a redstone torch");
				plugin.addBottles(p, 64);
			//}

		}
		// if player clicked with a blaze rod, try to make to max of bottles
		if (p.getItemInHand().getTypeId() == 369) {

			System.out.println("Clicked with a blaze rod");
			//if (p.hasPermission("wizardxp.all")) {
				// gets the amount of glass bottles the player has
				int bottles = 0;
				ListIterator<ItemStack> it = p.getInventory().iterator();
				while (it.hasNext()) {
					ItemStack current = it.next();
					if (current.getType() == Material.GLASS_BOTTLE) {
						bottles += current.getAmount();
					}
				}
				
				//if the player doesn't have any bottle, why creating xp bottles ?
				if (bottles == 0) {
					p.sendMessage("You don't have any bottle !");
				} else {
					plugin.addBottles(p, bottles);
				}
			//}
		}

	}
}

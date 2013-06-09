package fr.iwebster.wizardxp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ListIterator;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class WizardXP extends JavaPlugin {

	public static int EXP_COST;
	private FileConfiguration config;

	public void onEnable() {
		WizardXPEventsHandler handler = new WizardXPEventsHandler(this);
		getServer().getPluginManager().registerEvents(
				new WizardXPEventsListener(handler, this), this);
		config = this.getConfig();
		EXP_COST=config.getInt("data.expcost");
	}

	/**
	 * Adds n bottles of enchanting (BOE) in the inventory of the player p
	 * 
	 * @param n
	 */
	public void addBottles(Player p, int n) {
		// if the player has enough xp points and enough empty bottles in his
		// inventory
		int playersXp;
		int playersLvl = p.getLevel();
		
		//player have had following xp to reach that level
		if(playersLvl>=0 && playersLvl<=16){
			playersXp = playersLvl*17;
		} else if(playersLvl>=17 && playersLvl<=31){
			playersXp = (int) (1.5*Math.pow(playersLvl, 2)-29.5*playersLvl+360);
		} else playersXp = (int) (3.5*Math.pow(playersLvl, 2)-151.5*playersLvl+2220);
		
		//player had following xp in his bar
		if(playersLvl >=30){
			playersXp += (62 + (playersLvl - 30) * 7)*p.getExp();
		}else if(playersLvl >=15){
			playersXp += (17 + (playersLvl - 15) * 3)*p.getExp(); 
		} else playersXp += 17*p.getExp();
		
		if (n > 0) {
			if (playersXp >= EXP_COST * n) {
				if (p.getInventory().contains(374, n)) {
					
					//player made n bottles, so he now has following xp
					playersXp -= EXP_COST * n;

					
					// resetting xp points, bar, levels & shit
					p.setLevel(0);
					p.setExp(0);

					// gives player the xp he still has
					p.giveExp(Math.max(playersXp, 0));

					removeBottles(p, n);

					p.getWorld().dropItemNaturally(p.getLocation(),
							new ItemStack(384, n));

					// tiny sound that sounds like magic
					p.playSound(p.getLocation(), Sound.PORTAL_TRAVEL, 0.2F, 1);
					if (n != 1) {
						p.sendMessage(ChatColor.GREEN + "You transmuted " + n
								+ " glass bottles into Bottles O' Enchanting");
					} else
						p.sendMessage(ChatColor.GREEN
								+ "You transmuted 1 glass bottle into a Bottle O' Enchanting");
				} else {
					if (n != 1) {
						p.sendMessage(ChatColor.RED
								+ "You don't have enough glass bottles to convey "
								+ n + " Bottles O' Enchanting.");
					} else {
						p.sendMessage(ChatColor.RED
								+ "You don't have enough glass bottles to convey 1 Bottle O' Enchanting.");
					}
				}
			} else {

				if (n != 1) {
					p.sendMessage(ChatColor.RED
							+ "You don't have enough xp points to be conveyed into "
							+ n + " Bottles O' Enchanting.");
				} else {
					p.sendMessage(ChatColor.RED
							+ "You don't have enough xp points to be conveyed into 1 Bottle O' Enchanting.");
				}
			}
		} else
			p.sendMessage(ChatColor.RED
					+ "You don't have enough glass bottles to be transmuted into any Bottle o' Enchanting.");
	}

	/**
	 * Removes n glass bottles in player p's inventory.
	 * 
	 * @param p
	 * @param n
	 */
	public void removeBottles(Player p, int n) {
		ListIterator<ItemStack> it = p.getInventory().iterator();
		while (it.hasNext() && n > 0) {
			ItemStack current = it.next();

			// if the current item is a glass_bottle itemStack
			if (current != null && current.getType() == Material.GLASS_BOTTLE) {

				// we save the amount of bottles in this stack
				int cAmount = current.getAmount();

				// if there are more bottles to remove than in the stack
				if (cAmount <= n) {

					// we remove the whole ItemStack
					p.getInventory().removeItem(current);
					n -= cAmount;
				} else {
					current.setAmount(cAmount - n);
					n = 0;
				}
			}
		}
	}

	public void usage(CommandSender sender, int page) {
		if (page == 1) {
			sender.sendMessage(commandHeader(1)
					+ "\n"
					+ ChatColor.GREEN
					+ "WizardXP is a plugin about conveying your XP points into glass bottles, tranforming them into Bottles O' Enchanting.\nTo do it, you need to left click on an enchantment table with one of the 3 magical wands that are, sorted in an ascendent power way :  the wooden stick, the redstone torch and the blaze rod.");
		} else if (page == 2) {
			sender.sendMessage(commandHeader(2)
					+ "\n"
					+ ChatColor.GREEN
					+ "Clicking with the stick will eventually gives you a Bottle O' Enchanting, assuming you have enough glass bottles and XP points. \nThe same goes for the torch and the rod, with either one stack of bottles, or the maximum amount of glass bottles you already have.");
		} else if (page == 3) {
			sender.sendMessage(commandHeader(3)
					+ "\n"
					+ ChatColor.GREEN
					+ "But exctracting XP points from your body is a very tough process ! Even the best Wizards of the realm can't really master it, that's why some of the XP points will be lost during the conveying. \nIt costs you 11 xp points to create a single Bottle O' Enchanting, but you're not sure to be able to extract 11 points from it... \nContact - "+ChatColor.WHITE+"w3b5t3r56@gmail.com");
		}
	}

	public String commandHeader(int page) {
		return new String("------- ") + (ChatColor.GREEN)
				+ ("WizardXP - Page ") + ChatColor.WHITE + ("[")
				+ (ChatColor.RED) + (page) + ("/3") + (ChatColor.WHITE)
				+ ("] -------");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("wizardxp")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("God doesn't need to know how to convey XP, He can give it !");
				return false;
			} else {
				if (args.length > 1) {
					usage(sender, 1);
					return false;
				} else {
					try {
						int page = Integer.parseInt(args[0]);
						switch (page) {
						case 2:
							usage(sender, 2);
							break;
						case 3:
							usage(sender, 3);
							break;
						default:
							usage(sender, 1);
						}
					} catch (Exception e) {
						usage(sender, 1);
					}

				}
			}
		}
		return false;

	}

	public void onDisable() {

	}
}
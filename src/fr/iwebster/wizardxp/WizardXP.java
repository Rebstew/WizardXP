package fr.iwebster.wizardxp;

//import org.bukkit.command.Command;
//import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class WizardXP extends JavaPlugin {
 
	public void onEnable(){
		WizardXPEventsHandler handler = new WizardXPEventsHandler(this);
		getServer().getPluginManager().registerEvents(new WizardXPEventsListener(handler), this);
		System.out.println("Started !");
	}

	/**
	 * Adds n bottles of enchanting (BOE) in the inventory of the player
	 * @param n
	 */
	public void addBottles(Player p, int n){
		//if the player has enough xp points and enough empty bottles in his inventory
		if(p.getTotalExperience()>= 11*n ){
			if(p.getInventory().contains(374, n)){
				
				//player gets n BOE in exchange of n empty bottles and 11*n xp 
				p.setTotalExperience(p.getTotalExperience()- 11*n);
				p.getInventory().remove(new ItemStack(374,n));
				p.getInventory().addItem(new ItemStack(384,n));
				p.sendMessage(n+" bottles of xp created.");
			} else {
				p.sendMessage("You don't have enough empty bottles to make "+n+" bottles.");
			}
		} else {
			p.sendMessage("You don't have enough xp points to make "+n+" bottles.");
		}
	}
	
	/*public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("wizxp")){
			if (args.length > 4) {
		           sender.sendMessage("Too many arguments!");
		           return false;
		        } 
		        if (args.length < 2) {
		           sender.sendMessage("Not enough arguments!");
		           return false;
		        }
		}
		return false;
		
	}*/
	
	public void onDisable(){
		
	}
}
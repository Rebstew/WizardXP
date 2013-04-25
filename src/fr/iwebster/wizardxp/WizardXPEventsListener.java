package fr.iwebster.wizardxp;

import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class WizardXPEventsListener implements Listener {
	
	private WizardXP plugin;
	private WizardXPEventsHandler handler;
	
	public WizardXPEventsListener(WizardXPEventsHandler handler, WizardXP plugin){
		this.plugin = plugin;
		this.handler = handler;
	}
	
	public void onPlayerInteractEvent(PlayerInteractEvent e)
    {       
        //if the player clicked on an enchantment table, we handle it
        if(e.getAction()==Action.LEFT_CLICK_BLOCK && e.getClickedBlock().getTypeId()==116)
        {
            handler.PlayerInteractEventHandler(e, plugin);
            e.setCancelled(true);
        }
    }
}

package fr.iwebster.wizardxp;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class WizardXPEventsListener implements Listener {
	
	private WizardXPEventsHandler handler;
	
	public WizardXPEventsListener(WizardXPEventsHandler handler){
		this.handler = handler;
	}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e)
    {       
        //if the player left clicked on an enchantment table without creative mode
        if(e.getPlayer().getGameMode()!=GameMode.CREATIVE && e.getAction()==Action.LEFT_CLICK_BLOCK && e.getClickedBlock().getTypeId()==116)
        {
            handler.PlayerInteractEventHandler(e);
            e.setCancelled(true);
        }
    }
}

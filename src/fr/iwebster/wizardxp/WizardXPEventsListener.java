package fr.iwebster.wizardxp;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class WizardXPEventsListener implements Listener {
	
	private WizardXPEventsHandler handler;
	public static int INTERACT_BLOCK_ID;
	
	public WizardXPEventsListener(WizardXPEventsHandler handler, WizardXP plugin){
		this.handler = handler;
		INTERACT_BLOCK_ID = plugin.getConfig().getInt("blocks.interact");
	}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e)
    {       
		
        //if the player left clicked on an enchantment table without creative mode
        if(e.getPlayer().getGameMode()!=GameMode.CREATIVE && e.getAction()==Action.LEFT_CLICK_BLOCK && e.getClickedBlock().getTypeId()==INTERACT_BLOCK_ID)
        {
            handler.PlayerInteractEventHandler(e);
        }
    }
	
	/* 
	@EventHandler
	public void onExpBottleEvent(ExpBottleEvent e){
		handler.ExpBottleEventHandler(e);
	}*/
}

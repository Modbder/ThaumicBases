package tb.common.event;

import tb.common.item.ItemHerobrinesScythe;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.NameFormat;

public class TBEventHandler {
	
	@SubscribeEvent
	public void nameFormatEvent(NameFormat event)
	{
		if(event.entityPlayer != null && event.entityPlayer.getCurrentEquippedItem() != null && event.entityPlayer.getCurrentEquippedItem().getItem() instanceof ItemHerobrinesScythe)
			event.displayname = "Herobrine";
	}

}

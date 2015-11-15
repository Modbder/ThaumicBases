package tb.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tb.api.RevolverUpgrade;
import tb.common.item.ItemRevolver;
import tb.common.item.ItemUkulele;

public class RevolverEvents
{
	@SubscribeEvent
	public void renderFPHand(RenderHandEvent event)
	{
		if(Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() != null && (Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemRevolver || Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemUkulele))
			Minecraft.getMinecraft().getItemRenderer().resetEquippedProgress();
	}
	
	@SubscribeEvent
	public void renderCrossharEvent(RenderGameOverlayEvent event)
	{
		if(event.type == ElementType.CROSSHAIRS && Minecraft.getMinecraft().thePlayer.isSneaking())
		{
			if(Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() != null && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemRevolver)
			{
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void renderZoomEvent(FOVUpdateEvent event)
	{
		if(Minecraft.getMinecraft().thePlayer.isSneaking())
		{
			if(Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() != null && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemRevolver)
			{
				event.newfov = 0.9F / (ItemRevolver.getUpgradeLevel(Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem(), RevolverUpgrade.accuracy)+1);
			}
		}
	}
}

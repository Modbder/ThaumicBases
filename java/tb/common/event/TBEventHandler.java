package tb.common.event;

import DummyCore.Utils.Coord3D;
import DummyCore.Utils.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ITickable;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.NameFormat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import tb.common.block.BlockPyrofluid;
import tb.common.item.ItemHerobrinesScythe;
import tb.core.TBCore;
import tb.utils.TBConfig;
import tb.utils.TBUtils;

public class TBEventHandler {
	
	public static int clientUkuleleSoundPlayDelay = 0;
	
	@SubscribeEvent
	public void bucketFill(FillBucketEvent event)
	{
		if(event.target != null && event.world != null && event.target.typeOfHit == MovingObjectType.BLOCK && event.world.getBlockState(event.target.getBlockPos()).getBlock() instanceof BlockPyrofluid)
			{event.result = new ItemStack(Items.lava_bucket,1,0);event.setCanceled(true);}
	}
	
	@SubscribeEvent
	public void nameFormatEvent(NameFormat event)
	{
		if(TBConfig.allowHSNicknameChange)
			if(event.entityPlayer != null && event.entityPlayer.getCurrentEquippedItem() != null && event.entityPlayer.getCurrentEquippedItem().getItem() instanceof ItemHerobrinesScythe)
				event.displayname = "Herobrine";
	}
	
	@SubscribeEvent
	public void clientWorldTickEvent(ClientTickEvent event)
	{
		World world = TBCore.proxy.clientWorld();
		if(event.side == Side.CLIENT && event.phase == Phase.END && world != null && world.provider != null)
		{
			--clientUkuleleSoundPlayDelay;
			if(Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.ticksExisted % 20 * 20 == 0)
			{
				TBUtils.loadedClientSpawners.clear();
				if(world.loadedTileEntityList != null)
					for(int i = 0; i < world.loadedTileEntityList.size(); ++i)
					{
						Object obj = world.loadedTileEntityList.get(i);
						if(obj instanceof TileEntity && obj instanceof TileEntityMobSpawner && !TileEntity.class.cast(obj).isInvalid() && TileEntity.class.cast(obj) instanceof ITickable)
						{
							TileEntity tile = TileEntity.class.cast(obj);
							Coord3D coords = new Coord3D(tile.getPos().getX(),tile.getPos().getY(),tile.getPos().getZ());
							Pair<Integer, Coord3D> tileCoords = new Pair<Integer, Coord3D>(world.provider.getDimensionId(),coords);
							TBUtils.loadedClientSpawners.add(tileCoords);
						}
					}
			}
		}
	}

}

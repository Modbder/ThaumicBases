package tb.utils;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import tb.common.block.BlockTBPlant;
import tb.common.block.BlockTBSapling;

public class WAILACompat {
	
	public static void callbackRegister(IWailaRegistrar registrar)
	{
		registrar.registerBodyProvider(new TBDataProvider(), Block.class);
	}
	
	public static class TBDataProvider implements IWailaDataProvider
	{
		@Override
		public ItemStack getWailaStack(IWailaDataAccessor paramIWailaDataAccessor,IWailaConfigHandler paramIWailaConfigHandler) {
			return null;
		}

		@Override
		public List<String> getWailaHead(ItemStack paramItemStack, List<String> paramList,IWailaDataAccessor paramIWailaDataAccessor, IWailaConfigHandler paramIWailaConfigHandler) {
			return paramList;
		}

		@Override
		public List<String> getWailaBody(ItemStack paramItemStack, List<String> paramList,IWailaDataAccessor paramIWailaDataAccessor, IWailaConfigHandler paramIWailaConfigHandler) {
			if(paramItemStack != null && paramItemStack.getItem() != null && paramItemStack.getItem() instanceof ItemBlock)
			{
				Block b = ItemBlock.class.cast(paramItemStack.getItem()).block;
				if(b instanceof BlockTBSapling)
					paramList.add(LangUtil.translateG("hud.msg.growth", new Object[0])+" "+((paramItemStack.getMetadata() / 8) * 50) + "%");
				if(b instanceof BlockTBPlant)
					paramList.add(LangUtil.translateG("hud.msg.growth", new Object[0])+" "+String.format(" %.0f",((float)paramItemStack.getMetadata() / (float)(BlockTBPlant.class.cast(b).growthStages-1) * 100F)) + "%");
			}
			return paramList;
		}

		@Override
		public List<String> getWailaTail(ItemStack paramItemStack, List<String> paramList,IWailaDataAccessor paramIWailaDataAccessor, IWailaConfigHandler paramIWailaConfigHandler) {
			return paramList;
		}

		@Override
		public NBTTagCompound getNBTData(EntityPlayerMP paramEntityPlayerMP, TileEntity paramTileEntity,NBTTagCompound paramNBTTagCompound, World paramWorld, BlockPos paramBlockPos) {
			return paramNBTTagCompound;
		}
		
	}
	
}

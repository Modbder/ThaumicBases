package tb.common.tile;

import java.lang.reflect.Field;

import net.minecraft.nbt.NBTTagCompound;
import tb.utils.TBConfig;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.tiles.TileAlchemyFurnace;

public class TileAdvAlchemicalFurnace extends TileAlchemyFurnace{

	public boolean isFuelAlumentum;
	
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		isFuelAlumentum = tag.getBoolean("isAlumentium");
	}
	
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setBoolean("isAlumentium", isFuelAlumentum);
	}
	
	public void updateEntity()
	{
		if(this.furnaceBurnTime == 0)
		{
			isFuelAlumentum = this.getStackInSlot(1) != null && this.getStackInSlot(1).getItem() == ConfigItems.itemResource && this.getStackInSlot(1).getItemDamage() == 0;
		}
		try
		{
			if(this.isFuelAlumentum || !TBConfig.makeRequireAlumentium)
			{
				Class<TileAlchemyFurnace> furnace = TileAlchemyFurnace.class;
				Field count = furnace.getDeclaredField("count");
				count.setAccessible(true);
				
				count.setInt(this, count.getInt(this)+TBConfig.speedMultiplierForFurnace-1);
				
				count.setAccessible(false);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		super.updateEntity();
	}
	
}

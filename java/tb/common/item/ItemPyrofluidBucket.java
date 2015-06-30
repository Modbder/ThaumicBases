package tb.common.item;

import tb.init.TBBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemPyrofluidBucket extends Item{

	public ItemPyrofluidBucket()
	{
		super();
		this.setMaxStackSize(1);
	}
	
    public boolean onItemUse(ItemStack stk, EntityPlayer player, World w, int x, int y, int z, int side, float vecX, float vecY, float vecZ)
    {
    	ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[side];
    	w.setBlock(x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ, TBBlocks.pyrofluid, 0, 3);
    	player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.bucket,1,0));
    	return true;
    }
	
}

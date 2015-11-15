package tb.common.item;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Utils.IOldItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import tb.init.TBBlocks;

public class ItemPyrofluidBucket extends Item implements IOldItem{

	public ItemPyrofluidBucket()
	{
		super();
		this.setMaxStackSize(1);
	}
	
    public boolean onItemUse(ItemStack stk, EntityPlayer player, World w, BlockPos pos, EnumFacing side, float vecX, float vecY, float vecZ)
    {
    	w.setBlockState(pos.offset(side), TBBlocks.pyrofluid.getDefaultState());
    	player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.bucket,1,0));
    	
    	return true;
    }
	
    Icon icon;
    String textureName;
    
	public Item setTextureName(String s)
	{
		textureName = s;
		return this;
	}

	@Override
	public Icon getIconFromDamage(int meta) {
		return icon;
	}

	@Override
	public Icon getIconFromItemStack(ItemStack stk) {
		return getIconFromDamage(stk.getMetadata());
	}

	@Override
	public void registerIcons(IconRegister reg) {
		icon = reg.registerItemIcon(textureName);
	}

	@Override
	public int getRenderPasses(ItemStack stk) {
		return 0;
	}

	@Override
	public Icon getIconFromItemStackAndRenderPass(ItemStack stk, int pass) {
		return getIconFromItemStack(stk);
	}

	@Override
	public boolean recreateIcon(ItemStack stk) {
		return false;
	}

	@Override
	public boolean render3D(ItemStack stk) {
		return false;
	}
}

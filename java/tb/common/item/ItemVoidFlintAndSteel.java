package tb.common.item;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Utils.IOldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.items.IRepairable;
import thaumcraft.api.items.IWarpingGear;

public class ItemVoidFlintAndSteel extends ItemFlintAndSteel  implements IRepairable,IWarpingGear, IOldItem
{
	
	public EnumRarity getRarity(ItemStack itemstack)
	{
		return EnumRarity.UNCOMMON;
	}
	
	public void onUpdate(ItemStack stk, World w, Entity entity, int slot, boolean held)
	{
		super.onUpdate(stk, w, entity, slot, held);
		if ((stk.isItemDamaged()) && (entity != null) && (entity.ticksExisted % 20 == 0) && ((entity instanceof EntityLivingBase)))
			stk.damageItem(-1, (EntityLivingBase)entity);
	}
	
	public int getWarp(ItemStack itemstack, EntityPlayer player)
	{
		return 1;
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
		return true;
	}
}

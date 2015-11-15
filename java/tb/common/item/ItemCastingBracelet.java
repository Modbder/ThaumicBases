package tb.common.item;

import java.lang.reflect.Method;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import tb.api.BraceletState;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.wands.IWandable;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import thaumcraft.common.items.wands.ItemWand;
import thaumcraft.common.items.wands.WandManager;

public class ItemCastingBracelet extends ItemWand{
	    
	public ItemCastingBracelet()
	{
		super();
		this.setHasSubtypes(true);
	}
	
	public int getMaxVis(ItemStack stack) {
		return BraceletState.forMetadata(stack.getMetadata()).getCapacity(stack);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubItems(Item item, CreativeTabs tab, List lst)
	{
		for(int i = 0; i < BraceletState.braceletStates.size(); ++i)
			lst.add(new ItemStack(item,1,i));
	}
	
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
	{
		MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(world, player, true);
		ItemFocusBasic focus = getFocus(itemstack);
		if (focus != null && !focus.isVisCostPerTick(getFocusStack(itemstack)) && !isOnCooldown(player) && consumeAllVis(itemstack, player, focus.getVisCost(itemstack), false, false))
		{
			WandManager.setCooldown(player, focus.getActivationCooldown(getFocusStack(itemstack))/2);
			if (focus.onFocusActivation(itemstack, world, player, movingobjectposition, 0)) {
				consumeAllVis(itemstack, player, focus.getVisCost(itemstack), !player.worldObj.isRemote, false);
			}
			
			return itemstack;
		}
		
		if (focus != null && focus.isVisCostPerTick(getFocusStack(itemstack)) && !isOnCooldown(player)) {
			player.setItemInUse(itemstack, 2147483647);
			WandManager.setCooldown(player, -1);
			return itemstack;
		}
		
		return super.onItemRightClick(itemstack, world, player);
	}
	
	public boolean isOnCooldown(EntityLivingBase base)
	{
		try
		{
			Class<WandManager> manager = WandManager.class;
			Method method = manager.getDeclaredMethod("isOnCooldown", EntityLivingBase.class);
			boolean access = method.isAccessible();
			if(!access)
				method.setAccessible(true);
			
			boolean ret = Boolean.class.cast(method.invoke(null, base));
			
			if(!access)
				method.setAccessible(false);
			
			return ret;
		}
		catch(Exception e)
		{
			return true;
		}
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stk)
	{
		return StatCollector.translateToLocal(getUnlocalizedName(stk));
	}
	
    public String getUnlocalizedName(ItemStack stk)
    {
        return super.getUnlocalizedName(stk) + "." + BraceletState.forMetadata(stk.getMetadata()).getName(stk);
    }
    
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
    	IWandable iw = getObjectInUse(stack, player.worldObj);
    	if (iw != null)
    	{
    		iw.onUsingWandTick(stack, player, count);
    		++count;
    		iw.onUsingWandTick(stack, player, count);
    	}
    	else 
    	{
    		ItemFocusBasic focus = getFocus(stack);
    		if (focus != null && !isOnCooldown(player))
    			if (consumeAllVis(stack, player, focus.getVisCost(stack), false, false))
    			{
    				MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(player.worldObj, player, true);
    				WandManager.setCooldown(player, focus.getActivationCooldown(getFocusStack(stack))/2);
    				if (focus.onFocusActivation(stack, player.worldObj, player, movingobjectposition, count))
    				{
    					consumeAllVis(stack, player, focus.getVisCost(stack), !player.worldObj.isRemote, false);
    					++count;
    					focus.onFocusActivation(stack, player.worldObj, player, movingobjectposition, count);
    				}
    			}
    			else
    				player.stopUsingItem();
    	}
    }
    
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
	    ItemStack focus = getFocusStack(stack);
	    if (focus != null && !isOnCooldown(entityLiving)) 
	    {
	    	WandManager.setCooldown(entityLiving, getFocus(stack).getActivationCooldown(focus)/2);
	    	return focus.getItem().onEntitySwing(entityLiving, stack);
	    }
	    return super.onEntitySwing(entityLiving, stack);
    }
    
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player)
    {
	    ItemFocusBasic focus = getFocus(itemstack);
	    if (focus != null && !isOnCooldown(player)) 
	    {
	    	WandManager.setCooldown(player, focus.getActivationCooldown(getFocusStack(itemstack))/2);
	    	return focus.onFocusBlockStartBreak(itemstack, pos, player);
	    }
	    
	    return false;
    }
    
    @Override
    public float getConsumptionModifier(ItemStack is, EntityPlayer player, Aspect aspect, boolean crafting) 
    {
    	float consumptionModifier = 0.5F;
    	
    	if ((getCap(is).getSpecialCostModifierAspects() != null) && (getCap(is).getSpecialCostModifierAspects().contains(aspect)))
    		consumptionModifier *= getCap(is).getSpecialCostModifier();
    	else
    		consumptionModifier *= getCap(is).getBaseCostModifier();
    	
    	if (player != null)
    	{
    		consumptionModifier -= WandManager.getTotalVisDiscount(player, aspect);
    	}
    	
    	if (getFocus(is) != null && !crafting)
    		consumptionModifier -= getFocusFrugal(is) / 11.0F;
    	
    	return Math.max(consumptionModifier, 0.1F);
    }
    
    @Override
    public WandRod getRod(ItemStack stack)
    {
    	return BraceletState.forMetadata(stack.getMetadata()).getRod(stack);
    }
    
    @Override
    public WandCap getCap(ItemStack stack)
    {
    	return BraceletState.forMetadata(stack.getMetadata()).getCaps(stack);
    }
    
    public boolean isStaff(ItemStack stack)
    {
    	return true;
    }
    
    public boolean isSceptre(ItemStack stack) 
    {
    	return false;
    }
  
    
}

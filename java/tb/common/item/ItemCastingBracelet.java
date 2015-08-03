package tb.common.item;

import java.lang.reflect.Method;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import tb.init.TBItems;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.wands.IWandable;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.items.wands.WandManager;

public class ItemCastingBracelet extends ItemWandCasting //<- I hope no one will tell things like "OMG, You've stolen Azanor's code!!!, since that is not true. It is very similar in some places, since I need this item to do exactly the same his does.
{
	public static String[] names = new String[]{
		"iron",
		"gold",
		"greatwood",
		"thaumium",
		"silverwood",
		"reed",
		"bone",
		"obsidian",
		"blaze",
		"ice",
		"quartz",
		"void",
		"primal"
	};
	
	public static ResourceLocation[] braceletTextures = new ResourceLocation[]{
		loc("thaumcraft","textures/blocks/metalbase.png"),
		loc("thaumcraft","textures/blocks/goldbase.png"),
		loc("thaumcraft","textures/blocks/planks_greatwood.png"),
		loc("thaumcraft","textures/blocks/thaumiumblock.png"),
		loc("thaumcraft","textures/blocks/planks_silverwood.png"),
		loc("thaumicbases","textures/blocks/sugarcaneblock.png"),
		loc("thaumicbases","textures/blocks/boneblock.png"),
		loc("minecraft","textures/blocks/obsidian.png"),
		loc("thaumicbases","textures/blocks/blazeblock.png"),
		loc("minecraft","textures/blocks/ice_packed.png"),
		loc("minecraft","textures/blocks/quartz_block_bottom.png"),
		loc("thaumicbases","textures/blocks/voidblock.png"),
		loc("thaumcraft","textures/blocks/deco_3.png")
	};
	
	public static WandCap[] caps = new WandCap[]{
		ConfigItems.WAND_CAP_IRON,
		ConfigItems.WAND_CAP_GOLD,
		ConfigItems.WAND_CAP_GOLD,
		TBItems.WAND_CAP_THAUMINITE,
		ConfigItems.WAND_CAP_THAUMIUM,
		ConfigItems.WAND_CAP_THAUMIUM,
		ConfigItems.WAND_CAP_THAUMIUM,
		ConfigItems.WAND_CAP_THAUMIUM,
		ConfigItems.WAND_CAP_THAUMIUM,
		ConfigItems.WAND_CAP_THAUMIUM,
		ConfigItems.WAND_CAP_THAUMIUM,
		ConfigItems.WAND_CAP_THAUMIUM,
		ConfigItems.WAND_CAP_VOID
	};
	
	public static WandRod[] rods = new WandRod[]{
		ConfigItems.WAND_ROD_WOOD,
		ConfigItems.WAND_ROD_WOOD,
		ConfigItems.WAND_ROD_GREATWOOD,
		TBItems.WAND_ROD_THAUMIUM,
		ConfigItems.WAND_ROD_SILVERWOOD,
		ConfigItems.WAND_ROD_REED,
		ConfigItems.WAND_ROD_BONE,
		ConfigItems.WAND_ROD_OBSIDIAN,
		ConfigItems.WAND_ROD_BLAZE,
		ConfigItems.WAND_ROD_ICE,
		ConfigItems.WAND_ROD_QUARTZ,
		TBItems.WAND_ROD_VOID,
		ConfigItems.STAFF_ROD_PRIMAL
	};
	
	public static int[] capacity = new int[]{
		10,
		15,
		17,
		20,
		23,
		23,
		25,
		25,
		25,
		25,
		25,
		27,
		30
	};
	
	public ItemCastingBracelet()
	{
		super();
		this.setHasSubtypes(true);
	}
	
	public int getMaxVis(ItemStack stack) {
		return capacity[Math.min(names.length-1,stack.getItemDamage())]*100;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubItems(Item item, CreativeTabs tab, List lst)
	{
		for(int i = 0; i < names.length; ++i)
			lst.add(new ItemStack(item,1,i));
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
	{
		MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(world, player, true);
		if (movingobjectposition != null)
			if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
			{
				int x = movingobjectposition.blockX;
				int y = movingobjectposition.blockY;
				int z = movingobjectposition.blockZ;
				Block b = world.getBlock(x, y, z);
				
				if (b instanceof IWandable) 
				{
					ItemStack is = ((IWandable)b).onWandRightClick(world, itemstack, player);
					if (is != null) 
						return is;
				}
				
				TileEntity tile = world.getTileEntity(x, y, z);
				if (tile != null && tile instanceof IWandable)
				{
					ItemStack is = ((IWandable)tile).onWandRightClick(world, itemstack, player);
					if (is != null) 
						return is;
				}
			}
		
		ItemFocusBasic focus = getFocus(itemstack);
		if (focus != null && !isOnCooldown(player))
		{
			WandManager.setCooldown(player, focus.getActivationCooldown(getFocusItem(itemstack))/2);
			ItemStack ret = focus.onFocusRightClick(itemstack, world, player, movingobjectposition);
			if (ret != null) 
				return ret;
		}
		
		return itemstack;
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
        return super.getUnlocalizedName(stk) + "." + names[Math.min(names.length-1,stk.getItemDamage())];
    }
    
    @Override
    public float getConsumptionModifier(ItemStack is, EntityPlayer player, Aspect aspect, boolean crafting) 
    {
    	float consumptionModifier = 0.5F;
    	
    	if ((getCap(is).getSpecialCostModifierAspects() != null) && (getCap(is).getSpecialCostModifierAspects().contains(aspect)))
    		consumptionModifier = getCap(is).getSpecialCostModifier()/1.5F;
    	else
    		consumptionModifier = getCap(is).getBaseCostModifier()/1.5F;
    	
    	if (player != null)
    		consumptionModifier -= WandManager.getTotalVisDiscount(player, aspect);
    	
    	if (getFocus(is) != null && !crafting)
    		consumptionModifier -= getFocusFrugal(is) / 11.0F;
    	
    	return Math.max(consumptionModifier, 0.1F);
    }
    
    @Override
    public WandRod getRod(ItemStack stack)
    {
    	return stack.getItemDamage() == 3 ? TBItems.WAND_ROD_THAUMIUM : stack.getItemDamage() == 11 ? TBItems.WAND_ROD_VOID : rods[Math.min(rods.length-1, stack.getItemDamage())];
    }
    
    @Override
    public WandCap getCap(ItemStack stack)
    {
    	return stack.getItemDamage() == 3 ? TBItems.WAND_CAP_THAUMINITE : caps[Math.min(caps.length-1, stack.getItemDamage())];
    }
    
    public boolean isStaff(ItemStack stack)
    {
    	return true;
    }
    
    public boolean isSceptre(ItemStack stack) 
    {
    	return false;
    }
    
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
    	IWandable wandable = getObjectInUse(stack, player.worldObj);
    	if (wandable != null) 
    	{
    		this.animation = ItemFocusBasic.WandFocusAnimation.WAVE;
    		wandable.onUsingWandTick(stack, player, count);
    		++count;
    		wandable.onUsingWandTick(stack, player, count);
    	}
    	else
    	{
    		ItemFocusBasic focus = getFocus(stack);
    		if (focus != null && !isOnCooldown(player))
    		{
    			WandManager.setCooldown(player, focus.getActivationCooldown(getFocusItem(stack))/2);
    			focus.onUsingFocusTick(stack, player, count);
    			++count;
    			focus.onUsingFocusTick(stack, player, count);
    		}
    	}
    }
    
    public static final ResourceLocation loc(String mod, String path)
    {
    	return new ResourceLocation(mod,path);
    }
    
}

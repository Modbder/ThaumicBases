package tb.utils;

import java.util.ArrayList;
import java.util.Random;

import com.google.common.collect.Lists;

import DummyCore.Utils.MiscUtils;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import tb.common.item.ItemKnoseFragment;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.resources.ItemResearchNotes;

public class RecipesFragmentAdditions implements IRecipe{

	public static final Random rnd = new Random();
	
	public boolean isRNCompleted(ItemStack rn)
	{
		return false;
	}
	
	@Override
	public boolean matches(InventoryCrafting ic, World worldIn) {
        ItemStack itemstack = null;
        ArrayList<ItemStack> arraylist = Lists.newArrayList();
        
        for (int i = 0; i < ic.getSizeInventory(); ++i)
        {
            ItemStack itemstack1 = ic.getStackInSlot(i);

            if (itemstack1 != null)
            {
            	if(itemstack1.getItem() instanceof ItemResearchNotes)
            	{
            		if(itemstack != null || isRNCompleted(itemstack1))
            			return false;
            		
            		itemstack = itemstack1;
            	}else
            	{
            		if(!(itemstack1.getItem() instanceof ItemKnoseFragment))
            			return false;
            		
            		arraylist.add(itemstack1);
            	}
            }
        }
        
        return itemstack != null && !arraylist.isEmpty();
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting ic) {
		ItemStack rn = null;
		ArrayList<ItemStack> fragments = Lists.newArrayList();
		
        for (int i = 0; i < ic.getSizeInventory(); ++i)
        {
            ItemStack itemstack1 = ic.getStackInSlot(i);

            if (itemstack1 != null)
            {
            	if(itemstack1.getItem() instanceof ItemResearchNotes)
            	{
            		if(isRNCompleted(itemstack1))
            			return null;
            		rn = itemstack1;
            	}else
            	{
            		if(!(itemstack1.getItem() instanceof ItemKnoseFragment))
            			return null;
            		
            		fragments.add(itemstack1);
            	}
            }
        }
        
        if(rn == null)
        	return null;
        
        if(fragments.isEmpty())
        	return null;
        
        ItemStack arn = rn.copy();
        
        NBTTagCompound mTag = MiscUtils.getStackTag(arn);
        if(!mTag.hasKey("aspects"))
        	return null;
        
        NBTTagList aspects = mTag.getTagList("aspects", 10);
        AspectList al = new AspectList();
        for(ItemStack stk : fragments)
        {
        	int meta = stk.getMetadata();
        	AspectList added = null;
        	if(meta < 7)
        		added = ItemKnoseFragment.addedAspects[meta];
        	else
        	{
        		added = new AspectList();
	    		for(Aspect a : Aspect.getCompoundAspects())
	    		{
	    			if(rnd.nextBoolean())
	    				added.add(a, 1);
    				if(rnd.nextInt(24) == 0)
    					break;
	    		}
        	}
        	al.add(added);
        }
		
        for(int i = 0; i < al.size(); ++i)
        {
        	Aspect added = al.getAspects()[i];
        	if(added == null)
        		continue;
        	int a = al.getAmount(added);
        	String key = added.getTag();
        	NBTTagCompound tag = null;
        	boolean found = false;
        	for(int j = 0; j < aspects.tagCount(); ++j)
        	{
        		NBTTagCompound temp = aspects.getCompoundTagAt(j);
        		if(temp.hasKey("key")&&temp.getString("key").equalsIgnoreCase(key))
        		{
        			tag = temp;
        			found = true;
        		}
        	}
        	if(!found || tag == null)
        	{
        		tag = new NBTTagCompound();
        		tag.setString("key", key);
        		tag.setInteger("amount", 0);
        	}
        	
        	tag.setInteger("amount", tag.getInteger("amount")+a);
        	if(!found)
        		aspects.appendTag(tag);
        }
        
        mTag.setTag("aspects", aspects);
        arn.setTagCompound(mTag);
        
		return arn;
	}

	@Override
	public int getRecipeSize() {
		return 10;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting ic) {
		
        ItemStack[] aitemstack = new ItemStack[ic.getSizeInventory()];

        for (int i = 0; i < aitemstack.length; ++i)
        {
            ItemStack itemstack = ic.getStackInSlot(i);
            aitemstack[i] = net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack);
        }

        return aitemstack;
	}

}

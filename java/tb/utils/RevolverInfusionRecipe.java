package tb.utils;

import java.util.ArrayList;

import DummyCore.Utils.Pair;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import tb.api.RevolverUpgrade;
import tb.common.item.ItemRevolver;
import tb.init.TBItems;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;

public class RevolverInfusionRecipe extends InfusionRecipe{

	RevolverUpgrade upgrade;
	
	public RevolverInfusionRecipe(String research, RevolverUpgrade output, int inst,AspectList aspects2, ItemStack[] recipe)
	{
		super(research, new ItemStack(TBItems.revolver,1,OreDictionary.WILDCARD_VALUE), inst, aspects2, new ItemStack(TBItems.revolver,1,OreDictionary.WILDCARD_VALUE), recipe);
		upgrade = output;
	}

	@Override
	public boolean matches(ArrayList<ItemStack> input, ItemStack central, World world, EntityPlayer player)
	{
		ArrayList<Pair<RevolverUpgrade,Integer>> upgrades = ItemRevolver.getAllUpgradesFor(central);
		for(Pair<RevolverUpgrade,Integer> p : upgrades)
		{
			RevolverUpgrade up = p.getFirst();
			if(up.conflictsWith(upgrade))
				return false;
		}
		
		if(ItemRevolver.getUpgradeLevel(central, upgrade)+1 > upgrade.getMaxLevel())
			return false;
		
		if (getRecipeInput() == null) 
			return false;
		
		if(this.research.length() <= 0)
			return false;
		
		if (this.research.length() > 0 && !ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), this.research)) 
			return false;
		
		ItemStack i2 = central.copy();
		if (getRecipeInput().getItemDamage() == OreDictionary.WILDCARD_VALUE)
			i2.setItemDamage(OreDictionary.WILDCARD_VALUE);
		
		ArrayList<ItemStack> ii = new ArrayList<ItemStack>();
		for (ItemStack is : input)
			ii.add(is.copy());
		
		for (ItemStack comp : getComponents()) 
		{
			boolean b = false;
			for (int a = 0; a < ii.size(); a++) 
			{
				i2 = ((ItemStack)ii.get(a)).copy();
				if (comp.getItemDamage() == OreDictionary.WILDCARD_VALUE)
					i2.setItemDamage(OreDictionary.WILDCARD_VALUE);
				
				if (areItemStacksEqual(i2, comp, true)) 
				{
					ii.remove(a);
					b = true;
					break;
				}
			}
			if (!b) 
				return false;
		}
		
		return ii.size() == 0;
	}
	
	@Override
	public Object getRecipeOutput(ItemStack input) 
	{
		ItemStack istk = input.copy();
		ItemRevolver.addUpgrade(istk, upgrade, ItemRevolver.getUpgradeLevel(istk, upgrade)+1);
		return istk;
	}
	
	@Override
	public AspectList getAspects(ItemStack input) 
	{
		AspectList retAsp = this.aspects.copy();
		
		for(int i = 0; i < ItemRevolver.getUpgradeLevel(input, upgrade); ++i)
			for(int j = 0; j < retAsp.size(); ++j)
				retAsp.add(retAsp.getAspects()[j], retAsp.getAmount(retAsp.getAspects()[j]));
		
		return retAsp;
	}
	
	@Override
	public int getInstability(ItemStack input) 
	{
		return super.getInstability(input)+upgrade.getInstabilityForLevel(ItemRevolver.getUpgradeLevel(input, upgrade));
	}
}

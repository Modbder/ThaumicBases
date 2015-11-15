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
import thaumcraft.api.research.ResearchHelper;

public class RevolverInfusionRecipe extends InfusionRecipe{

	RevolverUpgrade upgrade;
	ItemStack revolverBase;
	ItemStack revolverUpgraded;
	
	public RevolverInfusionRecipe(String research, RevolverUpgrade output, int inst,AspectList aspects2, ItemStack[] recipe)
	{
		super(research, new ItemStack(TBItems.revolver,1,OreDictionary.WILDCARD_VALUE), inst, aspects2, new ItemStack(TBItems.revolver,1,OreDictionary.WILDCARD_VALUE), recipe);
		revolverBase = new ItemStack(TBItems.revolver,1,OreDictionary.WILDCARD_VALUE);
		revolverUpgraded = revolverBase.copy();
		upgrade = output;
		ItemRevolver.addUpgrade(revolverUpgraded, upgrade, ItemRevolver.getUpgradeLevel(revolverUpgraded, upgrade)+1);
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
		
		if(this.research.length <= 0)
			return false;
		
		if (this.research.length > 0 && !ResearchHelper.isResearchComplete(player.getName(), this.research)) 
			return false;
		
		ItemStack i2 = central.copy();
		if (getRecipeInput() instanceof ItemStack && ItemStack.class.cast(getRecipeInput()).getMetadata() == OreDictionary.WILDCARD_VALUE)
			i2.setItemDamage(OreDictionary.WILDCARD_VALUE);
		
		ArrayList<ItemStack> ii = new ArrayList<ItemStack>();
		for (ItemStack is : input)
			ii.add(is.copy());
		
		for (Object o : getComponents()) 
		{
			if(!(o instanceof ItemStack))
				continue;
			
			ItemStack comp = (ItemStack) o;
			
			boolean b = false;
			for (int a = 0; a < ii.size(); a++) 
			{
				i2 = ii.get(a).copy();
				if (comp.getItemDamage() == OreDictionary.WILDCARD_VALUE)
					i2.setItemDamage(OreDictionary.WILDCARD_VALUE);
				
				if (ThaumcraftApiHelper.areItemStacksEqualForCrafting(i2, comp)) 
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
	
	public Object getRecipeOutput() {
		return revolverUpgraded;
	}
	
	@Override
	public Object getRecipeOutput(EntityPlayer player, ItemStack input, ArrayList<ItemStack> comps)
	{
		ItemStack istk = input.copy();
		ItemRevolver.addUpgrade(istk, upgrade, ItemRevolver.getUpgradeLevel(istk, upgrade)+1);
		return istk;
	}
	
	@Override
	public AspectList getAspects(EntityPlayer player, ItemStack input, ArrayList<ItemStack> comps)
	{
		AspectList retAsp = this.aspects.copy();
		
		for(int i = 0; i < ItemRevolver.getUpgradeLevel(input, upgrade); ++i)
			for(int j = 0; j < retAsp.size(); ++j)
				retAsp.add(retAsp.getAspects()[j], retAsp.getAmount(retAsp.getAspects()[j]));
		
		return retAsp;
	}
	
	@Override
	public int getInstability(EntityPlayer player, ItemStack input, ArrayList<ItemStack> comps)
	{
		return super.getInstability(player,input,comps)+upgrade.getInstabilityForLevel(ItemRevolver.getUpgradeLevel(input, upgrade));
	}
}

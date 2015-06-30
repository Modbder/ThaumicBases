package tb.common.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemRosehipSyrup extends Item{

    public ItemStack onItemRightClick(ItemStack stack, World w, EntityPlayer player)
    {
    	player.setItemInUse(stack, getMaxItemUseDuration(stack));
        return stack;
    }
    
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.drink;
    }

    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32;
    }
    
    @SuppressWarnings("unchecked")
	public ItemStack onEaten(ItemStack stack, World w, EntityPlayer player)
    {
        if (!player.capabilities.isCreativeMode)
        {
            --stack.stackSize;
            if(stack.stackSize > 0)
            {
            	if(!player.inventory.addItemStackToInventory(new ItemStack(ConfigItems.itemEssence,1,0)))
            		player.dropPlayerItemWithRandomChoice(new ItemStack(ConfigItems.itemEssence,1,0), false);
            }
        }

        if (!w.isRemote)
        {
        	Collection<PotionEffect> c = player.getActivePotionEffects();
        	Iterator<PotionEffect> $i = c.iterator();
        	ArrayList<Integer> removeEffects = new ArrayList<Integer>();
        	while($i.hasNext())
        	{
        		PotionEffect effect = $i.next();
        		int i = processPotion(player,effect);
        		if(i != -1)
        			removeEffects.add(i);
        	}
        	
        	for(int i = 0; i < removeEffects.size(); ++i)
        	{
        		int j = removeEffects.get(i);
        		if(j != -1 && j < Potion.potionTypes.length && Potion.potionTypes[j] != null)
        		{
        			if(player.getActivePotionEffect(Potion.potionTypes[j]) != null)
        			{
        				player.removePotionEffect(j);
        			}
        		}
        	}
        }

        return stack.stackSize <= 0 ? new ItemStack(ConfigItems.itemEssence,1,0) : stack;
    }
    
    public static int processPotion(EntityPlayer p, PotionEffect effect)
    {
    	if(effect != null && effect.getPotionID() < Potion.potionTypes.length && Potion.potionTypes[effect.getPotionID()] != null)
    	{
    		if(isBadEffect(Potion.potionTypes[effect.getPotionID()]))
    			return effect.getPotionID();
    		
    		if(canDecreaseLevel(Potion.potionTypes[effect.getPotionID()]))
    		{
    			if(effect.getAmplifier() == 0)
    				return effect.getPotionID();
    			else
    			{
    				reflectPotionEffect(p,effect);
    				return -1;
    			}
    		}
    	}
    		
    	return -1;
    }
    
    public static boolean isBadEffect(Potion p)
    {
    	return p != null && (p == Potion.blindness || p == Potion.confusion || p == Potion.digSlowdown || p == Potion.hunger || p == Potion.moveSlowdown || p == Potion.poison || p == Potion.weakness || p == Potion.wither);
    }
    
    public static boolean canDecreaseLevel(Potion p)
    {
    	int id = p.getId();
    	return p != null && (id == Config.potionBlurredID || id == Config.potionInfVisExhaustID || id == Config.potionTaintPoisonID || id == Config.potionThaumarhiaID || id == Config.potionUnHungerID || id == Config.potionVisExhaustID);
    }
    
    public static void reflectPotionEffect(EntityPlayer p, PotionEffect effect)
    {
    	int amp = effect.getAmplifier()-1;
    	int id = effect.getPotionID();
    	int dur = effect.getDuration();
    	boolean transparent = effect.getIsAmbient();
    	p.removePotionEffect(effect.getPotionID());
    	p.addPotionEffect(new PotionEffect(id,dur,amp,transparent));
    }
}

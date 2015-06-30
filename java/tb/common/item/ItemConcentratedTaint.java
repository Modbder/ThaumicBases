package tb.common.item;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import tb.utils.TBUtils;
import thaumcraft.api.damagesource.DamageSourceThaumcraft;
import thaumcraft.api.entities.ITaintedMob;
import thaumcraft.api.nodes.INode;
import thaumcraft.api.nodes.NodeType;
import thaumcraft.codechicken.lib.math.MathHelper;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.playerdata.PacketResearchComplete;
import thaumcraft.common.lib.research.ResearchManager;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemConcentratedTaint extends Item{

    public boolean onItemUse(ItemStack stk, EntityPlayer user, World w, int x, int y, int z, int side, float vecX, float vecY, float vecZ)
    {
    	--stk.stackSize;
    	w.playSound(x, y, z, "break.glass", 1, 1, false);
    	
    	Block b = w.getBlock(x, y, z);
    	if(b == ConfigBlocks.blockAiry)
    	{
    		TileEntity tile = w.getTileEntity(x, y, z);
    		if(tile instanceof INode)
    		{
    			INode.class.cast(tile).setNodeType(NodeType.TAINTED);
    			return true;
    		}
    	}
    	
    	for(int i = 0; i < 100; ++i)
    	{
    		int dX = x + MathHelper.floor_double(MathUtils.randomDouble(w.rand)*16);
    		int dZ = z + MathHelper.floor_double(MathUtils.randomDouble(w.rand)*16);
    		int dY = y + w.rand.nextInt(2) - w.rand.nextInt(2);
    		MiscUtils.changeBiome(w, ThaumcraftWorldGenerator.biomeTaint, dX, dZ);
    		if (w.isBlockNormalCubeDefault(dX, dY - 1, dZ, false) && w.getBlock(dX, dY, dZ).isReplaceable(w, dX, dY, dZ))
    		{
    			w.setBlock(dX, dY, dZ, ConfigBlocks.blockTaintFibres, 0, 3);
    		}
    		Thaumcraft.proxy.bottleTaintBreak(w, dX, dY, dZ);
    	}
    	
    	return true;
    }
    
    public boolean itemInteractionForEntity(ItemStack stk, EntityPlayer user, EntityLivingBase usedOn)
    {
    	if(!(usedOn instanceof ITaintedMob))
    	{
    		--stk.stackSize;
    		user.swingItem();
    		
    		usedOn.addPotionEffect(new PotionEffect(Config.potionTaintPoisonID,200,1,true));
    		
    		if(usedOn instanceof EntityPlayer)
    		{
    			EntityPlayer victum = (EntityPlayer) usedOn;
    			for(int i = 0; i < victum.inventory.armorInventory.length; ++i)
    			{
    				ItemStack armor = victum.inventory.armorInventory[i];
    				if(armor != null)
    				{
    					ItemStack newArmor = armor.copy();
    					EntityItem actualArmor = new EntityItem(victum.worldObj,victum.posX,victum.posY,victum.posZ,newArmor);
    					actualArmor.delayBeforeCanPickup = 1200;
    					if(!victum.worldObj.isRemote)
    						victum.worldObj.spawnEntityInWorld(actualArmor);
    					
    					armor = null;
    					victum.inventory.armorInventory[i] = null;
    				}
    			}
    			victum.addPotionEffect(new PotionEffect(Potion.blindness.id,200,0,true));
    			victum.addPotionEffect(new PotionEffect(Potion.nightVision.id,200,0,true));
    		}
    		
    		return true;
    	}
    	return false;
    }
    
    public ItemStack onItemRightClick(ItemStack stack, World w, EntityPlayer player)
    {
    	if(ResearchManager.isResearchComplete(player.getCommandSenderName(), "TB.TaintMinor"))
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
        
        if(player.getHealth() < player.getMaxHealth())
        {
        	for(int i = 0; i < 99; ++i)
        		player.attackEntityFrom(DamageSourceThaumcraft.taint, Integer.MAX_VALUE);
        }else
        {
        	player.setHealth(1);
        	player.addPotionEffect(new PotionEffect(Potion.blindness.getId(),600,0,true));
        	player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(),600,0,true));
        	player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(),600,4,true));
        	player.addPotionEffect(new PotionEffect(Potion.weakness.getId(),600,4,true));
        	player.addPotionEffect(new PotionEffect(Potion.confusion.getId(),100,0,true));
        	
        	if(player.posY > 6)
        	{
        		player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tb.txt.howlBelow")).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE).setItalic(true)));
        		TBUtils.addWarpToPlayer(player, 6, 0);
        	}else
        	{
        		if(player.worldObj.provider != null && player.worldObj.provider.dimensionId == -1)
        		{
        			player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tb.txt.taintDevelop")).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE).setItalic(true)));
        			TBUtils.addWarpToPlayer(player, 18, 0);
        			TBUtils.addWarpToPlayer(player, 6, 1);
        			TBUtils.addWarpToPlayer(player, 1, 2);
        			if(!ResearchManager.isResearchComplete(player.getCommandSenderName(), "TB.TaintProgress"))
    	    			PacketHandler.INSTANCE.sendTo(new PacketResearchComplete("@TB.TaintProgress"), (EntityPlayerMP)player);
        		}else
        		{
            		player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tb.txt.howlDeeper")).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE).setItalic(true)));
            		TBUtils.addWarpToPlayer(player, 12, 0);
        		}
        	}
        }

        return stack.stackSize <= 0 ? new ItemStack(ConfigItems.itemEssence,1,0) : stack;
    }
	
}

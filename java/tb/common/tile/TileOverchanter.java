package tb.common.tile;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.wands.IWandable;
import thaumcraft.common.lib.events.EssentiaHandler;
import DummyCore.Utils.Coord3D;
import DummyCore.Utils.Lightning;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileOverchanter extends TileEntity implements IInventory, IWandable{

	public ItemStack inventory;
	
	public int enchantingTime;
	public boolean xpAbsorbed;
	public boolean isEnchantingStarted;
	public int syncTimer;
	
	public Lightning renderedLightning;
	
	@Override
	public int getSizeInventory() {
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public void updateEntity() 
	{
		if(syncTimer <= 0)
		{
			syncTimer = 100;
			NBTTagCompound tg = new NBTTagCompound();
			tg.setInteger("0", enchantingTime);
			tg.setBoolean("1", xpAbsorbed);
			tg.setBoolean("2", isEnchantingStarted);
			tg.setInteger("x", xCoord);
			tg.setInteger("y", yCoord);
			tg.setInteger("z", zCoord);
			MiscUtils.syncTileEntity(tg, 0);
		}else
			--syncTimer;
		
		if(this.inventory == null)
		{
			isEnchantingStarted = false;
			xpAbsorbed = false;
			enchantingTime = 0;
			renderedLightning = null;
		}else
        {
        	if(this.isEnchantingStarted)
        	{
        		if(this.worldObj.getWorldTime() % 20 == 0)
        		{
        			renderedLightning = new Lightning(this.worldObj.rand, new Coord3D(0,0,0), new Coord3D(MathUtils.randomDouble(this.worldObj.rand)/50,MathUtils.randomDouble(this.worldObj.rand)/50,MathUtils.randomDouble(this.worldObj.rand)/50), 0.3F, 1,0,1);
        			this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "thaumcraft:infuserstart", 1F, 1.0F);
	        		if(EssentiaHandler.drainEssentia(this, Aspect.MAGIC, ForgeDirection.UNKNOWN, 8, false))
	        		{
	        			++enchantingTime;
	        			if(enchantingTime >= 16 && !this.xpAbsorbed)
	        			{
	        				List<EntityPlayer> players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1).expand(6, 3, 6));
	        				if(!players.isEmpty())
	        				{
	        					for(int i = 0; i < players.size(); ++i)
	        					{
	        						EntityPlayer p = players.get(i);
	        						if(p.experienceLevel >= 30)
	        						{
	        							p.attackEntityFrom(DamageSource.magic, 8);
	        							this.worldObj.playSoundEffect(p.posX,p.posY,p.posZ, "thaumcraft:zap", 1F, 1.0F);
	        							p.experienceLevel -= 30;
	        							xpAbsorbed = true;
	        							break;
	        						}
	        					}
	        				}
	        			}
	        			
	        			if(xpAbsorbed && enchantingTime >= 32)
	        			{
	        				int enchId = this.findEnchantment(inventory);
	        				NBTTagList nbttaglist = this.inventory.getEnchantmentTagList();
	        				for(int i = 0; i < nbttaglist.tagCount(); ++i)
	        				{
	        					NBTTagCompound tag = nbttaglist.getCompoundTagAt(i);
	        					if(tag != null && Integer.valueOf(tag.getShort("id"))==enchId)
	        					{
	        						tag.setShort("lvl", (short) (Integer.valueOf(tag.getShort("lvl"))+1));
	        						NBTTagCompound stackTag = MiscUtils.getStackTag(inventory);
	        						if(!stackTag.hasKey("overchants"))
	        						{
	        							stackTag.setIntArray("overchants", new int[]{enchId});
	        						}else
	        						{
	        							int[] arrayInt = stackTag.getIntArray("overchants");
	        							int[] newArrayInt = new int[arrayInt.length+1];
	        							for(int j = 0; j < arrayInt.length; ++j)
	        							{
	        								newArrayInt[j] = arrayInt[j];
	        							}
	        							newArrayInt[newArrayInt.length-1]=enchId;
	        							
	        							stackTag.setIntArray("overchants", newArrayInt);
	        						}
	        						break;
	        					}
	        				}
	        				isEnchantingStarted = false;
	        				xpAbsorbed = false;
	        				enchantingTime = 0;
	        				renderedLightning = null;
	        				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "thaumcraft:wand", 1F, 1F);
	        			}
	        			
	        		}else
	        		{
	        			--enchantingTime;
	        		}
        		}
        	}
        }
	}
	
	public boolean canStartEnchanting()
	{
		if(!this.isEnchantingStarted)
			if(this.inventory != null)
			{
				if(this.inventory.getEnchantmentTagList() != null && this.inventory.getEnchantmentTagList().tagCount() > 0)
				{
					if(findEnchantment(inventory) != -1)
					{
						return true;
					}
				}
			}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public int findEnchantment(ItemStack enchanted)
	{
		NBTTagCompound stackTag = MiscUtils.getStackTag(inventory);
		LinkedHashMap<Integer,Integer> ench = (LinkedHashMap<Integer, Integer>) EnchantmentHelper.getEnchantments(enchanted);
		Set<Integer> keys = ench.keySet();
		Iterator<Integer> $i = keys.iterator();
		
		while($i.hasNext())
		{
			int i = $i.next();
			if(!stackTag.hasKey("overchants"))
			{
				return i;
			}else
			{
				int[] overchants = stackTag.getIntArray("overchants");
				if(MathUtils.arrayContains(overchants, i))
					continue;
				
				return i;
			}
		}
		
		return -1;
	}
	
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
    	enchantingTime = pkt.func_148857_g().getInteger("0");
    	xpAbsorbed = pkt.func_148857_g().getBoolean("1");
    	isEnchantingStarted = pkt.func_148857_g().getBoolean("2");
    }

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory;
	}

	@Override
	public ItemStack decrStackSize(int slot, int num) {
        if (this.inventory != null)
        {
            ItemStack itemstack;

            if (this.inventory.stackSize <= num)
            {
                itemstack = this.inventory;
                this.inventory = null;
                this.markDirty();
                return itemstack;
            }
            else
            {
                itemstack = this.inventory.splitStack(num);

                if (this.inventory.stackSize == 0)
                {
                    this.inventory = null;
                }

                this.markDirty();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return inventory;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stk) {
		inventory = stk;
	}

	@Override
	public String getInventoryName() {
		return "tb.overchanter";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return player.dimension == this.worldObj.provider.dimensionId && this.worldObj.blockExists(xCoord, yCoord, zCoord);
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
		
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stk) {
		return stk.hasTagCompound() && stk.getEnchantmentTagList() != null;
	}
	
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        
        enchantingTime = tag.getInteger("enchTime");
        xpAbsorbed = tag.getBoolean("xpAbsorbed");
        isEnchantingStarted = tag.getBoolean("enchStarted");
        
        if(tag.hasKey("itm"))
        	inventory = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("itm"));
    }
    
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        
        tag.setInteger("enchTime", enchantingTime);
        tag.setBoolean("xpAbsorbed",xpAbsorbed);
        tag.setBoolean("enchStarted", isEnchantingStarted);
        
        if(inventory != null)
        {
        	NBTTagCompound t = new NBTTagCompound();
        	inventory.writeToNBT(t);
        	tag.setTag("itm", t);
        }
    }
    

	@Override
	public int onWandRightClick(World world, ItemStack wandstack,EntityPlayer player, int x, int y, int z, int side, int md) 
	{
		if(canStartEnchanting())
		{
			isEnchantingStarted = true;
			player.swingItem();
			syncTimer = 0;
			this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "thaumcraft:craftstart", 0.5F, 1.0F);
			return 1;
		}
		return -1;
	}

	@Override
	public ItemStack onWandRightClick(World world, ItemStack wandstack,EntityPlayer player)
	{
		return wandstack;
	}

	@Override
	public void onUsingWandTick(ItemStack wandstack, EntityPlayer player,
			int count) {
	}

	@Override
	public void onWandStoppedUsing(ItemStack wandstack, World world,
			EntityPlayer player, int count) {
	}


}

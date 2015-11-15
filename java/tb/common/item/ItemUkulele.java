package tb.common.item;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import DummyCore.Utils.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import tb.common.event.TBEventHandler;
import tb.core.TBCore;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.items.baubles.ItemAmuletVis;
import thaumcraft.common.items.wands.WandManager;
import thaumcraft.common.lib.aura.AuraHandler;
import thaumcraft.common.lib.potions.PotionWarpWard;

public class ItemUkulele extends ItemAmuletVis{

	public static final String[] types = new String[]{
		"simple",
		"knowledge",
		"calming",
		"electric",
		"resistance",
		"buffing",
		"confusion",
		"growth",
		"love"
	};
	
	public static final boolean[] isCostPerTick = new boolean[]{
		false,
		false,
		false,
		false,
		true,
		false,
		false,
		true,
		true
	};
	
	public static final AspectList[] costs = new AspectList[]{
		new AspectList().add(Aspect.AIR, 0).add(Aspect.FIRE, 0).add(Aspect.ENTROPY, 0).add(Aspect.ORDER, 0).add(Aspect.EARTH, 0).add(Aspect.WATER, 0),
		new AspectList().add(Aspect.AIR, 200).add(Aspect.FIRE, 200).add(Aspect.ENTROPY, 50).add(Aspect.ORDER, 100).add(Aspect.EARTH, 50).add(Aspect.WATER, 50),
		new AspectList().add(Aspect.AIR, 100).add(Aspect.FIRE, 50).add(Aspect.ENTROPY, 20).add(Aspect.ORDER, 200).add(Aspect.EARTH, 100).add(Aspect.WATER, 100),
		new AspectList().add(Aspect.AIR, 50).add(Aspect.FIRE, 50).add(Aspect.ENTROPY, 25).add(Aspect.ORDER, 25).add(Aspect.EARTH, 5).add(Aspect.WATER, 10),
		new AspectList().add(Aspect.AIR, 2).add(Aspect.FIRE, 2).add(Aspect.ENTROPY, 2).add(Aspect.ORDER, 8).add(Aspect.EARTH, 10).add(Aspect.WATER, 5),
		new AspectList().add(Aspect.AIR, 2000).add(Aspect.FIRE, 2000).add(Aspect.ENTROPY, 2000).add(Aspect.ORDER, 2000).add(Aspect.EARTH, 2000).add(Aspect.WATER, 2000),
		new AspectList().add(Aspect.AIR, 100).add(Aspect.FIRE, 100).add(Aspect.ENTROPY, 200).add(Aspect.ORDER, 25).add(Aspect.EARTH, 25).add(Aspect.WATER, 50),
		new AspectList().add(Aspect.AIR, 2).add(Aspect.FIRE, 2).add(Aspect.ENTROPY, 2).add(Aspect.ORDER, 10).add(Aspect.EARTH, 10).add(Aspect.WATER, 10),
		new AspectList().add(Aspect.AIR, 10).add(Aspect.FIRE, 5).add(Aspect.ENTROPY, 5).add(Aspect.ORDER, 8).add(Aspect.EARTH, 8).add(Aspect.WATER, 10)
	};
	
	public static final int[] soundDelays = new int[]{
		25*20,
		4*20,
		20*20,
		8*20,
		9*20,
		10*20,
		16*20,
		8*20,
		10*20
	};
	
	public static final DecimalFormat formatForAspects = new DecimalFormat("###.##");
	
	@Override
	public EnumRarity getRarity(ItemStack itemstack)
	{
		return EnumRarity.RARE;
	}
	
	public AspectList getCostWithDiscount(ItemStack is, EntityPlayer p, float originalCost, AspectList al)
	{
		AspectList originalAL = al.copy();
		for(int i1 = 0; i1 < 6; ++i1)
		{
			Aspect a = originalAL.getAspects()[i1];
			int costi = originalAL.getAmount(a);
			originalAL.reduce(a, costi);
			float originalDiscount = originalCost;
			originalDiscount -= WandManager.getTotalVisDiscount(p, a);
			costi *= originalDiscount;
			
			originalAL.add(a, costi);
		}
		return originalAL;
	}

	
	@SuppressWarnings({ "unchecked" })
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
	{
		{
			Vec3 lookVec = player.getLookVec();
			AspectList cost = getCostWithDiscount(stack, player, 0.75F, costs[Math.min(stack.getItemDamage(), costs.length-1)]);
			player.worldObj.spawnParticle(EnumParticleTypes.NOTE, player.posX+lookVec.xCoord/5+MathUtils.randomDouble(itemRand)/2, player.posY+1+lookVec.yCoord/2+MathUtils.randomDouble(itemRand)/10+0.1D, player.posZ+lookVec.zCoord/2+MathUtils.randomDouble(itemRand)/5, itemRand.nextDouble(), itemRand.nextDouble(), itemRand.nextDouble());
			if(player.worldObj.isRemote && TBEventHandler.clientUkuleleSoundPlayDelay <= 0)
			{
				TBEventHandler.clientUkuleleSoundPlayDelay = soundDelays[Math.min(stack.getItemDamage(), soundDelays.length-1)];
				TBCore.proxy.playGuitarSound("thaumicbases:guitar."+types[Math.min(stack.getItemDamage(), types.length-1)]);
			}
			if(stack.getItemDamage() == 1 && (this.getVis(stack, Aspect.AIR) > 0 && this.getVis(stack, Aspect.FIRE) > 0 && this.getVis(stack, Aspect.WATER) > 0 && this.getVis(stack, Aspect.EARTH) > 0 && this.getVis(stack, Aspect.ORDER) > 0 && this.getVis(stack, Aspect.ENTROPY) > 0))
			{
				if(count % 80 == 0)
				{
					AxisAlignedBB aabb = AxisAlignedBB.fromBounds(player.posX-8, player.posY-8, player.posZ-8, player.posX+8, player.posY+8, player.posZ+8);
					List<EntityPlayer> players = player.worldObj.getEntitiesWithinAABB(EntityPlayer.class, aabb);
					for(EntityPlayer p : players)
					{
						boolean addAspect = p == player ? true : p.worldObj.rand.nextBoolean();
						
						if(!addAspect)
							continue;
						
						int amount = p.worldObj.rand.nextInt(3)+1;
						if(!player.worldObj.isRemote && this.consumeAllVis(stack, player, cost, true, false))
						{
							EntityXPOrb xp = new EntityXPOrb(player.worldObj,player.posX,player.posY,player.posZ,amount);
							player.worldObj.spawnEntityInWorld(xp);
						}
					}
				}
			}
			if(stack.getItemDamage() == 2 && (this.getVis(stack, Aspect.AIR) > 0 && this.getVis(stack, Aspect.FIRE) > 0 && this.getVis(stack, Aspect.WATER) > 0 && this.getVis(stack, Aspect.EARTH) > 0 && this.getVis(stack, Aspect.ORDER) > 0 && this.getVis(stack, Aspect.ENTROPY) > 0))
			{
				if(count % 20 == 0)
				{
					AxisAlignedBB aabb = AxisAlignedBB.fromBounds(player.posX-4, player.posY-4, player.posZ-4, player.posX+4, player.posY+4, player.posZ+4);
					List<EntityPlayer> players = player.worldObj.getEntitiesWithinAABB(EntityPlayer.class, aabb);
					for(EntityPlayer p : players)
					{
						if(this.consumeAllVis(stack, player, cost, !player.worldObj.isRemote, false))
						{
							boolean hasEffect = p.getActivePotionEffect(PotionWarpWard.instance) != null;
							if(!hasEffect)
							{
								if(!p.worldObj.isRemote)
									p.addPotionEffect(new PotionEffect(PotionWarpWard.instance.getId(),200,0,true,false));
							}else
							{
								PotionEffect effect = p.getActivePotionEffect(PotionWarpWard.instance);
								try
								{
									Field dur = PotionEffect.class.getDeclaredFields()[2];
									dur.setAccessible(true);
									dur.setInt(effect, dur.getInt(effect)+120);
									dur.setAccessible(false);
								}
								catch(Exception e)
								{
									e.printStackTrace();
									continue;
								}
							}
						}
					}
				}
			}
			if(stack.getItemDamage() == 3 && (this.getVis(stack, Aspect.AIR) > 0 && this.getVis(stack, Aspect.FIRE) > 0 && this.getVis(stack, Aspect.WATER) > 0 && this.getVis(stack, Aspect.EARTH) > 0 && this.getVis(stack, Aspect.ORDER) > 0 && this.getVis(stack, Aspect.ENTROPY) > 0))
			{
				double dx = player.posX + MathUtils.randomDouble(itemRand)*16;
				double dy = player.posY + MathUtils.randomDouble(itemRand)*16;
				double dz = player.posZ + MathUtils.randomDouble(itemRand)*16;
				if(player.worldObj.isRemote && player.worldObj.rand.nextDouble() <= 0.1D)
					Thaumcraft.proxy.getFX().arcLightning(player.posX, player.posY-1, player.posZ, dx, dy, dz, 0.2F, 0.5F, 1, 1);
				
				f:for(int i = 1; i <= 16; ++i)
				{
					double px = lookVec.xCoord*i+player.posX;
					double py = lookVec.yCoord*i+player.posY+player.getEyeHeight();
					double pz = lookVec.zCoord*i+player.posZ;
					AxisAlignedBB aabb = AxisAlignedBB.fromBounds(px-0.5D, py-0.5D, pz-0.5D, px+0.5D, py+0.5D, pz+0.5D);
					List<EntityLivingBase> mobs = player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
					for(EntityLivingBase e : mobs)
					{
						if(e == player)
							continue;
						
						if(e.isDead)
							continue;
						
						if(e.hurtTime > 0)
							continue;

						boolean attack = true;
						
						if(!this.consumeAllVis(stack, player, cost, !player.worldObj.isRemote, false))
						{
							attack = false;
						}
						
						if(attack)
						{
							e.attackEntityFrom(DamageSource.causePlayerDamage(player), 6);
							if(player.worldObj.isRemote)
								Thaumcraft.proxy.getFX().arcLightning(player.posX, player.posY-1, player.posZ, e.posX, e.posY, e.posZ, 0.2F, 0.5F, 1, 1);
							player.worldObj.playSound(player.posX, player.posY, player.posZ, "thaumcraft:jacobs", 1, player.worldObj.rand.nextFloat()*2, false);
							player.worldObj.playSound(e.posX, e.posY, e.posZ, "thaumcraft:jacobs", 1, player.worldObj.rand.nextFloat()*2, false);
						}
						
						break f;
					}
				}
			}
			if(stack.getItemDamage() == 4 && (this.getVis(stack, Aspect.AIR) > 0 && this.getVis(stack, Aspect.FIRE) > 0 && this.getVis(stack, Aspect.WATER) > 0 && this.getVis(stack, Aspect.EARTH) > 0 && this.getVis(stack, Aspect.ORDER) > 0 && this.getVis(stack, Aspect.ENTROPY) > 0))
			{
				if(count % 10 == 0)
				{
					for(int i = 0; i < cost.size(); ++i)
					{
						cost.add(cost.getAspects()[i], cost.getAmount(cost.getAspects()[i])*9);
					}
					if(stack.hasTagCompound() && stack.getTagCompound().hasKey("playerhealth"))
					{
						if(this.consumeAllVis(stack, player, cost, !player.worldObj.isRemote, false))
						{
							if(player.getHealth() < stack.getTagCompound().getDouble("playerhealth"))
							{
								player.setHealth((float) stack.getTagCompound().getDouble("playerhealth"));
							}else
							{
								stack.getTagCompound().setDouble("playerhealth", player.getHealth());
							}
						}
					}else
					{
						if(!stack.hasTagCompound())
							stack.setTagCompound(new NBTTagCompound());
						
						stack.getTagCompound().setDouble("playerhealth", player.getHealth());
					}
					
				}
			}
			if(stack.getItemDamage() == 5 && (this.getVis(stack, Aspect.AIR) > 0 && this.getVis(stack, Aspect.FIRE) > 0 && this.getVis(stack, Aspect.WATER) > 0 && this.getVis(stack, Aspect.EARTH) > 0 && this.getVis(stack, Aspect.ORDER) > 0 && this.getVis(stack, Aspect.ENTROPY) > 0))
			{
				if(count % 100 == 0)
				{
					AxisAlignedBB aabb = AxisAlignedBB.fromBounds(player.posX-4, player.posY-4, player.posZ-4, player.posX+4, player.posY+4, player.posZ+4);
					List<EntityPlayer> players = player.worldObj.getEntitiesWithinAABB(EntityPlayer.class, aabb);
					for(EntityPlayer p : players)
					{
						if(this.consumeAllVis(stack, player, cost, !player.worldObj.isRemote, false))
						{
							Potion[] pots = new Potion[]{Potion.damageBoost,Potion.moveSpeed,Potion.digSpeed,Potion.nightVision,Potion.waterBreathing,Potion.regeneration};
							for(Potion pp : pots)
							{
								boolean hasEffect = p.getActivePotionEffect(pp) != null;
								if(!hasEffect)
								{
									if(!p.worldObj.isRemote)
										p.addPotionEffect(new PotionEffect(pp.id,600,0,true,false));
								}else
								{
									PotionEffect effect = p.getActivePotionEffect(pp);
									try
									{
										Field dur = PotionEffect.class.getDeclaredFields()[2];
										dur.setAccessible(true);
										dur.setInt(effect, dur.getInt(effect)+600);
										dur.setAccessible(false);
									}
									catch(Exception e)
									{
										e.printStackTrace();
										continue;
									}
								}
							}
						}
					}
				}
			}
			if(stack.getItemDamage() == 6 && (this.getVis(stack, Aspect.AIR) > 0 && this.getVis(stack, Aspect.FIRE) > 0 && this.getVis(stack, Aspect.WATER) > 0 && this.getVis(stack, Aspect.EARTH) > 0 && this.getVis(stack, Aspect.ORDER) > 0 && this.getVis(stack, Aspect.ENTROPY) > 0))
			{
				if(count % 20 == 0)
					f:for(int i = 1; i <= 8; ++i)
					{
						double px = lookVec.xCoord*i+player.posX;
						double py = lookVec.yCoord*i+player.posY+player.getEyeHeight();
						double pz = lookVec.zCoord*i+player.posZ;
						AxisAlignedBB aabb = AxisAlignedBB.fromBounds(px-0.5D, py-0.5D, pz-0.5D, px+0.5D, py+0.5D, pz+0.5D);
						List<EntityLivingBase> mobs = player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
						for(EntityLivingBase e : mobs)
						{
							if(e == player)
								continue;
							
							if(e.isDead)
								continue;
							
							boolean attack = true;
							
							if(!this.consumeAllVis(stack, player, cost, !player.worldObj.isRemote, false))
							{
								attack = false;
							}
							
							if(attack)
							{
								if(e instanceof EntityAnimal)
								{
									EntityAnimal.class.cast(e).attackEntityFrom(DamageSource.causePlayerDamage(player), 0);
								}
								if(e instanceof IMob)
								{
									AxisAlignedBB nearbyMobs = AxisAlignedBB.fromBounds(e.posX-16, e.posY-6, e.posZ-16, e.posX+16, e.posY+6, e.posZ+16);
									List<EntityLivingBase> nMobs = player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, nearbyMobs);
									
									if(nMobs.contains(player))
										nMobs.remove(player);
									if(nMobs.contains(e))
										nMobs.remove(e);
									
									if(!nMobs.isEmpty())
									{
										EntityLivingBase base = nMobs.get(player.worldObj.rand.nextInt(nMobs.size()));
										e.setRevengeTarget(base);
										e.setLastAttacker(base);
									}
								}
								
								if(e instanceof EntityPlayer)
								{
									e.addPotionEffect(new PotionEffect(Potion.blindness.id,100,0,true,false));
									e.addPotionEffect(new PotionEffect(Potion.confusion.id,100,0,true,false));
								}
							}
							
							break f;
						}
					}
			}
			if(stack.getItemDamage() == 7 && (this.getVis(stack, Aspect.AIR) > 0 && this.getVis(stack, Aspect.FIRE) > 0 && this.getVis(stack, Aspect.WATER) > 0 && this.getVis(stack, Aspect.EARTH) > 0 && this.getVis(stack, Aspect.ORDER) > 0 && this.getVis(stack, Aspect.ENTROPY) > 0))
			{
				if(count % 10 == 0)
				{
					for(int i = 0; i < cost.size(); ++i)
					{
						cost.add(cost.getAspects()[i], cost.getAmount(cost.getAspects()[i])*9);
					}
					if(this.consumeAllVis(stack, player, cost, !player.worldObj.isRemote, false))
					{
						for(int i = 0; i < 64; ++i)
						{
							int dx = MathHelper.floor_double(player.posX+MathUtils.randomDouble(itemRand)*6);
							int dy = MathHelper.floor_double(player.posY);
							int dz = MathHelper.floor_double(player.posZ+MathUtils.randomDouble(itemRand)*6);
							Block b = player.worldObj.getBlockState(new BlockPos(dx, dy, dz)).getBlock();
							if(!b.isAir(player.worldObj, new BlockPos(dx, dy, dz)) && b instanceof IPlantable)
								b.randomTick(player.worldObj, new BlockPos(dx, dy, dz), player.worldObj.getBlockState(new BlockPos(dx, dy, dz)), player.worldObj.rand);
						}
					}
				}
			}
			if(stack.getItemDamage() == 8 && (this.getVis(stack, Aspect.AIR) > 0 && this.getVis(stack, Aspect.FIRE) > 0 && this.getVis(stack, Aspect.WATER) > 0 && this.getVis(stack, Aspect.EARTH) > 0 && this.getVis(stack, Aspect.ORDER) > 0 && this.getVis(stack, Aspect.ENTROPY) > 0))
			{
				if(count % 10 == 0)
				{
					for(int i = 0; i < cost.size(); ++i)
					{
						cost.add(cost.getAspects()[i], cost.getAmount(cost.getAspects()[i])*9);
					}
					if(this.consumeAllVis(stack, player, cost, !player.worldObj.isRemote, false))
					{
						AxisAlignedBB aabb = AxisAlignedBB.fromBounds(player.posX-16, player.posY-4, player.posZ-16, player.posX+16, player.posY+4, player.posZ+16);
						List<EntityAnimal> animals = player.worldObj.getEntitiesWithinAABB(EntityAnimal.class, aabb);
						List<EntityAnimal> toRemove = new ArrayList<EntityAnimal>();
						for(EntityAnimal a : animals)
						{
							if(a.isInLove())
								toRemove.add(a);
							if(a.isDead)
								toRemove.add(a);
						}
						animals.removeAll(toRemove);
						
						if(!animals.isEmpty())
							animals.get(player.worldObj.rand.nextInt(animals.size())).setInLove(player);
					}
				}
			}
		}
	}
	
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer player, int count)
	{
		if(!itemstack.hasTagCompound())
			itemstack.setTagCompound(new NBTTagCompound());
		itemstack.getTagCompound().removeTag("playerhealth");
	}
	
	public Aspect handleRecharge(World world, ItemStack itemstack, BlockPos pos, EntityPlayer player, int amount)
	{
		AspectList al = getAspectsWithRoom(itemstack);
		for (Aspect aspect : al.getAspectsSortedByAmount())
		if (aspect != null)
		{
			int amt = Math.min(amount, al.getAmount(aspect));
			int drained = AuraHandler.drainAuraAvailable(world, pos, aspect, amt);
			if (drained > 0) 
			{
				addVis(itemstack, aspect, drained*100, true);
				amount -= drained;
				if (amount <= 0) return aspect;
			}
		}
		return null;
	}

	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.NONE;
	}
	
	public int getMaxItemUseDuration(ItemStack itemstack)
	{
		return Integer.MAX_VALUE;
	}
	
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
	{
		player.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		return itemstack;
	}
	
	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
	{
		return false;
	}
	
	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
	{
		return false;
	}
	
	@Override
	public int getMaxVis(ItemStack stack)
	{
		return 30000;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		AspectList cost = getCostWithDiscount(stack, player, 0.75F, costs[Math.min(stack.getItemDamage(), costs.length-1)]);
		list.add(StatCollector.translateToLocal("tb.ukulele.type."+types[Math.min(stack.getItemDamage(), types.length-1)]));
		list.add("");
		list.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("item.capacity.text") + " " + getMaxVis(stack) / 100);
		if (stack.hasTagCompound())
			for (Aspect aspect : Aspect.getPrimalAspects())
				 if (stack.getTagCompound().hasKey(aspect.getTag())) 
				 {
					 String amount = formatForAspects.format(stack.getTagCompound().getInteger(aspect.getTag()) / 100.0F);
					 list.add(" §" + aspect.getChatcolor() + aspect.getName() + "§r x " + amount + " ("+formatForAspects.format((float)cost.getAmount(aspect)/100)+"/"+(isCostPerTick[stack.getItemDamage()] ? "tick" : "use")+")");
				 }
	
	}
	
	public void onUpdate(ItemStack is, World w, Entity e, int slot, boolean currentItem)
	{
		if (!w.isRemote && e instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)e;
			
			if (player.ticksExisted % 5 == 0) {
				int cr = WandManager.getBaseChargeRate(player, currentItem, slot);
				if (cr > 0) 
					handleRecharge(player.worldObj, is, new BlockPos(player), player, cr);
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(Item itm, CreativeTabs tabs, List lst)
	{
		for(int i = 0; i < types.length; ++i)
		{
			lst.add(new ItemStack(itm,1,i));
			ItemStack max = new ItemStack(itm,1,i);
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger(Aspect.AIR.getTag(), this.getMaxVis(max));
			tag.setInteger(Aspect.FIRE.getTag(), this.getMaxVis(max));
			tag.setInteger(Aspect.WATER.getTag(), this.getMaxVis(max));
			tag.setInteger(Aspect.EARTH.getTag(), this.getMaxVis(max));
			tag.setInteger(Aspect.ENTROPY.getTag(), this.getMaxVis(max));
			tag.setInteger(Aspect.ORDER.getTag(), this.getMaxVis(max));
			max.setTagCompound(tag);
			lst.add(max);
		}
	}
	
}

package tb.common.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Utils.IOldItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tb.core.TBCore;
import thaumcraft.api.items.IRepairable;
import thaumcraft.api.items.IWarpingGear;

public class ItemHerobrinesScythe extends ItemSword implements IRepairable,IWarpingGear,IOldItem{

	public ItemHerobrinesScythe()
	{
		super(ToolMaterial.EMERALD);
	}
	
	public EnumRarity getRarity(ItemStack itemstack)
	{
		return EnumRarity.UNCOMMON;
	}
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List lst, boolean held) 
    {
    	lst.add(EnumChatFormatting.ITALIC+"Well, they’re nothing..."); //Do not translate this line, it is a reference to TheAtlanticCraft's song - The Herobrine
    }
	
	public void onUpdate(ItemStack stk, World w, Entity entity, int slot, boolean held)
	{
		super.onUpdate(stk, w, entity, slot, held);
		if ((stk.isItemDamaged()) && (entity != null) && (entity.ticksExisted % 20 == 0) && ((entity instanceof EntityLivingBase)))
			stk.damageItem(-1, (EntityLivingBase)entity);
	}
	
	public static void attack(EntityPlayer attacker, List<EntityLivingBase> doNotAttack, EntityLivingBase attacked)
	{
		AxisAlignedBB aabb = AxisAlignedBB.fromBounds(attacked.posX-1, attacked.posY-1, attacked.posZ-1, attacked.posX+1, attacked.posY+1, attacked.posZ+1).expand(6, 6, 6);
		
		List<EntityLivingBase> mobs = attacked.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
		
		Random rnd = attacker.worldObj.rand;
		
		mobs.removeAll(doNotAttack);
		
		if(!mobs.isEmpty())
		{
			while(!mobs.isEmpty())
			{
				int index = rnd.nextInt(mobs.size());
				if(mobs.get(index) != null && mobs.get(index).isEntityAlive() && mobs.get(index) instanceof IMob && !(mobs.get(index) instanceof EntityPlayer))
				{
					performPlayerAttackAt(attacker,mobs.get(index));
					
					TBCore.proxy.lightning(attacker.worldObj, attacked.posX, attacked.posY+rnd.nextDouble()*attacked.getEyeHeight(), attacked.posZ, mobs.get(index).posX, mobs.get(index).posY+rnd.nextDouble()*mobs.get(index).getEyeHeight(), mobs.get(index).posZ, 20, 2F, 10, 0);
					attacker.worldObj.playSoundAtEntity(mobs.get(index), "thaumcraft:zap", 1F, 0.8F);
					
					doNotAttack.add(mobs.get(index));
					
					attack(attacker,doNotAttack,mobs.get(index));
					
					break;
					
				}
				mobs.remove(index);
				continue;
			}
		}
		
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stk, EntityPlayer attacker, Entity attacked)
	{
		if(attacked.isEntityAlive() && attacked instanceof IMob)
		{
			attack(attacker,new ArrayList<EntityLivingBase>(),(EntityLivingBase) attacked);
		}
		return super.onLeftClickEntity(stk, attacker, attacked);
	}
	
    
	/**
	 * Copied from {@link EntityPlayer#attackTargetEntityWithCurrentItem(Entity)} to not call the {@link #onLeftClickEntity(ItemStack, EntityPlayer, Entity)}
	 * @param p the player
	 * @param p_71059_1_ attacked entity
	 */
    public static void performPlayerAttackAt(EntityPlayer p, Entity p_71059_1_)
    {
        if (MinecraftForge.EVENT_BUS.post(new AttackEntityEvent(p, p_71059_1_)))
        {
            return;
        }
        
        if (p_71059_1_.canAttackWithItem())
        {
            if (!p_71059_1_.hitByEntity(p))
            {
                float f = (float)p.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
                int i = 0;
                float f1 = 0.0F;

                if (p_71059_1_ instanceof EntityLivingBase)
                {
                    f1 = EnchantmentHelper.func_152377_a(p.getHeldItem(), ((EntityLivingBase)p_71059_1_).getCreatureAttribute());
                }
                else
                {
                    f1 = EnchantmentHelper.func_152377_a(p.getHeldItem(), EnumCreatureAttribute.UNDEFINED);
                }

                if (p.isSprinting())
                {
                    ++i;
                }

                if (f > 0.0F || f1 > 0.0F)
                {
                    boolean flag = p.fallDistance > 0.0F && !p.onGround && !p.isOnLadder() && !p.isInWater() && !p.isPotionActive(Potion.blindness) && p.ridingEntity == null && p_71059_1_ instanceof EntityLivingBase;

                    if (flag && f > 0.0F)
                    {
                        f *= 1.5F;
                    }

                    f += f1;
                    boolean flag1 = false;
                    int j = EnchantmentHelper.getFireAspectModifier(p);

                    if (p_71059_1_ instanceof EntityLivingBase && j > 0 && !p_71059_1_.isBurning())
                    {
                        flag1 = true;
                        p_71059_1_.setFire(1);
                    }

                    boolean flag2 = p_71059_1_.attackEntityFrom(DamageSource.causePlayerDamage(p), f);

                    if (flag2)
                    {
                        if (i > 0)
                        {
                            p_71059_1_.addVelocity(-MathHelper.sin(p.rotationYaw * (float)Math.PI / 180.0F) * i * 0.5F, 0.1D, MathHelper.cos(p.rotationYaw * (float)Math.PI / 180.0F) * i * 0.5F);
                            p.motionX *= 0.6D;
                            p.motionZ *= 0.6D;
                            p.setSprinting(false);
                        }

                        if (flag)
                        {
                            p.onCriticalHit(p_71059_1_);
                        }

                        if (f1 > 0.0F)
                        {
                            p.onEnchantmentCritical(p_71059_1_);
                        }

                        if (f >= 18.0F)
                        {
                            p.triggerAchievement(AchievementList.overkill);
                        }

                        p.setLastAttacker(p_71059_1_);

                        if (p_71059_1_ instanceof EntityLivingBase)
                        {
                            EnchantmentHelper.applyThornEnchantments((EntityLivingBase)p_71059_1_, p);
                        }

                        EnchantmentHelper.applyArthropodEnchantments(p, p_71059_1_);
                        ItemStack itemstack = p.getCurrentEquippedItem();
                        Object object = p_71059_1_;

                        if (p_71059_1_ instanceof EntityDragonPart)
                        {
                            IEntityMultiPart ientitymultipart = ((EntityDragonPart)p_71059_1_).entityDragonObj;

                            if (ientitymultipart != null && ientitymultipart instanceof EntityLivingBase)
                            {
                                object = ientitymultipart;
                            }
                        }

                        if (itemstack != null && object instanceof EntityLivingBase)
                        {
                            itemstack.hitEntity((EntityLivingBase)object, p);

                            if (itemstack.stackSize <= 0)
                            {
                                p.destroyCurrentEquippedItem();
                            }
                        }

                        if (p_71059_1_ instanceof EntityLivingBase)
                        {
                            p.addStat(StatList.damageDealtStat, Math.round(f * 10.0F));

                            if (j > 0)
                            {
                                p_71059_1_.setFire(j * 4);
                            }
                        }

                        p.addExhaustion(0.3F);
                    }
                    else if (flag1)
                    {
                        p_71059_1_.extinguish();
                    }
                }
            }
        }
    }
	
	public int getWarp(ItemStack itemstack, EntityPlayer player)
	{
		return 3;
	}
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Multimap<String, AttributeModifier> getItemAttributeModifiers()
    {
    	Multimap attribs = HashMultimap.create();
    	
    	attribs.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Weapon modifier", 14.5F, 0));
    	attribs.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CE"), "Speed modifier", 0.5F, 2));
    	
    	return attribs;
    }
    
    Icon icon;
    String textureName;
    
    public ItemHerobrinesScythe setTextureName(String s)
    {
    	textureName = s;
    	return this;
    }
    
    public boolean isItemTool(ItemStack stk)
    {
    	return true;
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

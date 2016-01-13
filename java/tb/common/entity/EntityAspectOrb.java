package tb.common.entity;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tb.utils.TBUtils;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.items.wands.ItemWand;

//This is basically an EntityXPOrb with slightly different values
public class EntityAspectOrb extends Entity implements IEntityAdditionalSpawnData{

	public int orbAge = 0;
	public int orbMaxAge = 150;
	private Aspect aspect;
	private int aspectValue;
	private EntityPlayer closestPlayer;
	
	public EntityAspectOrb(World w, double x, double y, double z, Aspect aspect, int value)
	{
	    super(w);
	    setSize(0.125F, 0.125F);
	    setPosition(x, y, z);
        this.motionX = (float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F;
        this.motionY = (float)(Math.random() * 0.2D) * 2.0F;
        this.motionZ = (float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F;
	    this.aspectValue = value*10;
	    setAspect(aspect);
	}
	
    protected boolean canTriggerWalking()
    {
        return false;
    }
    
    public EntityAspectOrb(World worldIn)
    {
        super(worldIn);
        this.setSize(0.25F, 0.25F);
    }

    protected void entityInit() {}
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float p_70070_1_)
    {
        float f1 = 0.5F;
        f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
        int i = super.getBrightnessForRender(p_70070_1_);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(f1 * 15.0F * 16.0F);

        if (j > 240)
        {
            j = 240;
        }

        return j | k << 16;
    }
    
    @SuppressWarnings("unchecked")
	public void onUpdate()
    {
        super.onUpdate();

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.029999999329447746D;

        if (this.worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() == Material.lava)
        {
            this.motionY = 0.20000000298023224D;
            this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
            this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
            this.playSound("random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
        }

        this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0D, this.posZ);
        double d0 = 8.0D;

        EntityPlayer player = (EntityPlayer) MiscUtils.getClosestEntity(TBUtils.castLst(this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.fromBounds(this.posX, this.posY, this.posZ, this.posX, this.posY, this.posZ).expand(8, 8, 8))), posX, posY, posZ);
        if(isWandInHotbarWithRoom(getAspect(), this.aspectValue,player) >= 0)
        	closestPlayer = player;
        
        if (this.closestPlayer != null && this.closestPlayer.isSpectator())
            this.closestPlayer = null;

        if (this.closestPlayer != null)
        {
            double d1 = (this.closestPlayer.posX - this.posX) / d0;
            double d2 = (this.closestPlayer.posY + this.closestPlayer.getEyeHeight() - this.posY) / d0;
            double d3 = (this.closestPlayer.posZ - this.posZ) / d0;
            double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
            double d5 = 1.0D - d4;

            if (d5 > 0.0D)
            {
                d5 *= d5;
                this.motionX += d1 / d4 * d5 * 0.1D;
                this.motionY += d2 / d4 * d5 * 0.1D;
                this.motionZ += d3 / d4 * d5 * 0.1D;
            }
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        float f = 0.98F;

        if (this.onGround)
            f = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.98F;

        this.motionX *= f;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= f;

        if (this.onGround)
            this.motionY *= -0.8999999761581421D;

        ++this.orbAge;

        if (this.orbAge >= this.orbMaxAge)
            this.setDead();
    }
    
    public boolean handleWaterMovement()
    {
        return this.worldObj.handleMaterialAcceleration(this.getEntityBoundingBox(), Material.water, this);
    }
    
    protected void dealFireDamage(int amount)
    {
        this.attackEntityFrom(DamageSource.inFire, amount);
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        
		this.setBeenAttacked();
		this.setDead();
		return false;
    }
    
    public void onCollideWithPlayer(EntityPlayer entityIn)
    {
    	if (!worldObj.isRemote && !isDead)
    	{
    		int slot = isWandInHotbarWithRoom(getAspect(), this.aspectValue, entityIn);
    		if (entityIn.xpCooldown == 0 && getAspect().isPrimal() && slot >= 0)
    		{
    			ItemWand wand = ItemWand.class.cast(entityIn.inventory.getStackInSlot(slot).getItem());
    			wand.addVis(entityIn.inventory.getStackInSlot(slot), getAspect(), this.aspectValue, true);
    			entityIn.xpCooldown = 2;
    	        playSound("random.orb", 0.1F, 0.5F * MathUtils.randomFloat(rand) * 0.7F + 1.8F);
    	        setDead();
    		}
    	}
    }
	
    public int isWandInHotbarWithRoom(Aspect aspect, int amount, EntityPlayer player)
    {
    	if(player == null)
    		return -1;
    	
	    for (int i = 0; i < InventoryPlayer.getHotbarSize(); ++i)
		    if (player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() instanceof ItemWand)
			    if (ItemWand.class.cast(player.inventory.getStackInSlot(i).getItem()).addVis(player.inventory.getStackInSlot(i), aspect, amount, false) < amount)
			    	return i;
	    return -1;
    }
    
    public void writeEntityToNBT(NBTTagCompound tag)
    {
    	tag.setShort("Age", (short)this.orbAge);
    	tag.setShort("Value", (short)this.aspectValue);
    	tag.setString("Aspect", getAspect().getTag());
    }

    public void readEntityFromNBT(NBTTagCompound tag)
    {
    	this.orbAge = tag.getShort("Age");
    	this.aspectValue = tag.getShort("Value");
    	setAspect(Aspect.getAspect(tag.getString("Aspect")));
    }
    
    public void writeSpawnData(ByteBuf data)
    {
	    if (getAspect() != null){
		    data.writeShort(getAspect().getTag().length());
		    for (char c : getAspect().getTag().toCharArray())
		    	data.writeChar(c);
	    }
    }

    public void readSpawnData(ByteBuf data)
    {
	    try{
		    int l = data.readShort();
		    StringBuilder s = new StringBuilder();
		    for (int i = 0; i < l; ++i){
		    	s.append(data.readChar());
		    }
		    setAspect(Aspect.getAspect(s.toString()));
	    }
	    catch (Exception e){e.printStackTrace();}
    }
    
    public int getAspectValue()
    {
    	return this.aspectValue;
    }

    public boolean canAttackWithItem()
    {
    	return false;
    }

    public Aspect getAspect()
    {
    	return this.aspect;
    }

    public void setAspect(Aspect aspect) 
    {
    	this.aspect = aspect;
    }
}

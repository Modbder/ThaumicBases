package tb.common.block;

import tb.init.TBBlocks;
import thaumcraft.api.crafting.IInfusionStabiliser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TBBlock extends Block implements IInfusionStabiliser{
	
	boolean isGlass;
	boolean stabilise;
	
	public TBBlock(Material m,boolean b)
	{
		super(m);
		isGlass = b;
	}
	
    public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
    {
    	if(entity instanceof EntityDragon)
    		return this != TBBlocks.enderPlanks;
    	
    	return super.canEntityDestroy(world, x, y, z, entity);
    }

	public Block stabilise()
	{
		stabilise = true;
		return this;
	}
	
    public boolean isOpaqueCube()
    {
        return !isGlass;
    }
    
    public int getLightOpacity()
    {
        return isGlass ? 7 : 15;
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return isGlass ? 1 : 0;
    }

	@Override
	public boolean canStabaliseInfusion(World world, int x, int y, int z) {
		return stabilise;
	}
}

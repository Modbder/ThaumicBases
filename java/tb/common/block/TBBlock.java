package tb.common.block;

import thaumcraft.api.crafting.IInfusionStabiliser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class TBBlock extends Block implements IInfusionStabiliser{
	
	boolean isGlass;
	boolean stabilise;
	
	public TBBlock(Material m,boolean b)
	{
		super(m);
		isGlass = b;
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

package tb.common.block;

import java.util.Random;

import thaumcraft.api.crafting.IInfusionStabiliser;
import thaumcraft.common.Thaumcraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockVoid extends Block implements IInfusionStabiliser{

	public BlockVoid()
	{
		super(Material.iron);
		this.setTickRandomly(true);
		this.setHardness(5);
		this.setResistance(Float.MAX_VALUE);
		this.setHarvestLevel("pickaxe", 3);
		this.setBlockTextureName("thaumicbases:voidblock");
	}
	
    public void randomDisplayTick(World w, int x, int y, int z, Random r)
    {
    	Thaumcraft.proxy.sparkle(x+r.nextFloat(), y+r.nextFloat(), z+r.nextFloat(), 2, 5, -0.1F);
    }
    
    public void updateTick(World w, int x, int y, int z, Random rnd)
    {
    	
    }

	@Override
	public boolean canStabaliseInfusion(World w, int x,int y, int z) 
	{
		return true;
	}

}

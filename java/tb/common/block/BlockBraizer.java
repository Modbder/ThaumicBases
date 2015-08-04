package tb.common.block;

import java.util.Random;

import tb.common.tile.TileBraizer;
import DummyCore.Utils.MathUtils;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBraizer extends BlockContainer
{

	public BlockBraizer()
	{
		super(Material.rock);
		setHardness(1F);
		setResistance(1F);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) 
	{
		return new TileBraizer();
	}
	
    public boolean isOpaqueCube()
    {
        return false;
    }
	
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	public int getRenderType()
	{
		return 0x1242fd;
	}

    public void randomDisplayTick(World w, int x, int y, int z, Random r)
    {
    	if(w.getBlockMetadata(x, y, z) > 0)
    	{
    		w.spawnParticle("flame", x+0.5D+MathUtils.randomDouble(r)/4, y+0.6D, z+0.5D+MathUtils.randomDouble(r)/4, 0, 0.04D, 0);
    		for(int i = 0; i < 10; ++i)
    			w.spawnParticle("smoke", x+0.5D+MathUtils.randomDouble(r)/4, y+0.7D, z+0.5D+MathUtils.randomDouble(r)/4, 0, r.nextDouble()/20, 0);
    		
    		w.playSound(x+0.5D, y+0.5D, z+0.5D, "thaumicbases:fire.loop", 0.1F, 0.1F, false);
    	}
    }
}

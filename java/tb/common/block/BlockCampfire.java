package tb.common.block;

import java.util.Random;

import tb.common.tile.TileCampfire;
import DummyCore.Utils.MathUtils;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCampfire extends BlockContainer
{

	public BlockCampfire()
	{
		super(Material.wood);
		setHardness(1F);
		setResistance(1F);
		setStepSound(soundTypeWood);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) 
	{
		return new TileCampfire();
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
		return 0x1242fe;
	}

    public void randomDisplayTick(World w, int x, int y, int z, Random r)
    {
    	if(w.getBlockMetadata(x, y, z) > 1)
    	{
    		w.spawnParticle("flame", x+0.5D+MathUtils.randomDouble(r)/4, y, z+0.5D+MathUtils.randomDouble(r)/4, 0, 0.04D, 0);
    		for(int i = 0; i < 10; ++i)
    			w.spawnParticle("smoke", x+0.5D+MathUtils.randomDouble(r)/4, y+0.1D, z+0.5D+MathUtils.randomDouble(r)/4, 0, r.nextDouble()/20, 0);
    		
    		w.playSound(x+0.5D, y+0.5D, z+0.5D, "thaumicbases:fire.loop", 0.1F, 0.1F, false);
    	}
    }
    
    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float vecX, float vecY, float vecZ)
    {
    	if(w.isRemote)
    		return true;
    	
    	TileCampfire fire = (TileCampfire) w.getTileEntity(x, y, z);
    	ItemStack stk = p.getCurrentEquippedItem();
    	boolean added = fire.addLog(stk);
    	if(!added)
    		added = fire.addFuel(stk);
    	
    	if(added)
    	{
    		p.inventory.decrStackSize(p.inventory.currentItem, 1);
    		if(p.openContainer != null)
    			p.openContainer.detectAndSendChanges();
    		p.inventory.markDirty();
    		return true;
    	}
    	
    	return false;
    }
}

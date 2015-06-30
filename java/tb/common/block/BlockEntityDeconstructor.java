package tb.common.block;

import tb.common.tile.TileEntityDeconstructor;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockEntityDeconstructor extends BlockContainer{

	public BlockEntityDeconstructor()
	{
		super(Material.rock);
		float f = 0.1F;
		this.setBlockBounds(0, 0, 0, 1, f, 1);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileEntityDeconstructor();
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
        return -1;
    }
    
    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float vecX, float vecY, float vecZ)
    {
    	TileEntityDeconstructor.class.cast(w.getTileEntity(x, y, z)).placerName = p.getCommandSenderName();
    	return true;
    }

}

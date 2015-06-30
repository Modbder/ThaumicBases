package tb.common.block;

import tb.common.tile.TileOverchanter;
import tb.core.TBCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockOverchanter extends BlockContainer{
	
	public IIcon topIcon;
	public IIcon botIcon;
	public IIcon sideIcon;

	public BlockOverchanter() 
	{
		super(Material.rock);
		this.setHarvestLevel("pickaxe", 3);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
        this.setLightOpacity(0);
	}
	
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	return side == 0 ? botIcon : side == 1 ? topIcon : sideIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
    	topIcon = reg.registerIcon("thaumicbases:overchanter/top");
    	botIcon = reg.registerIcon("thaumicbases:overchanter/bottom");
    	sideIcon = reg.registerIcon("thaumicbases:overchanter/side");
    }

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileOverchanter();
	}
	
    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float vecX, float vecY, float vecZ)
    {
    	if(!p.isSneaking())
    	{
    		if(!w.isRemote)
    		{
    			p.openGui(TBCore.instance, 0x421922, w, x, y, z);
    			return true;
    		}else
    		{
    			return true;
    		}
    	}
    	return false;
    }
}

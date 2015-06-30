package tb.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class TBSidedBlock extends TBBlock {

	public IIcon sideIcon;
	
	public TBSidedBlock(Material m, boolean b) {
		super(m, b);
	}
	
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	return side == 0 || side == 1 ? super.getIcon(side, meta) : sideIcon;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
    	super.registerBlockIcons(reg);
    	sideIcon = reg.registerIcon(getTextureName()+"_side");
    }
}

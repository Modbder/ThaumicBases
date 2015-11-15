package tb.common.block;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import net.minecraft.block.material.Material;

public class TBSidedBlock extends TBBlock {

	public Icon sideIcon;
	
	public TBSidedBlock(Material m, boolean b) {
		super(m, b);
	}
	
    public Icon getIcon(int side, int meta)
    {
    	return side == 0 || side == 1 ? super.getIcon(side, meta) : sideIcon;
    }

    public void registerBlockIcons(IconRegister reg)
    {
    	super.registerBlockIcons(reg);
    	sideIcon = reg.registerBlockIcon(getTextureName()+"_side");
    }
}

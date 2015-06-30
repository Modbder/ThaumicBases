package tb.common.block;

import java.util.List;

import tb.core.TBCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockOldLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockTBLog extends BlockOldLog{
	
	public static final String[] names = new String[]{
		"richBirchLog"
	};
	
	public static final String[] textures = new String[]{
		"richBirch/log"
	};
	
	public BlockTBLog()
	{
		super();
		this.setHarvestLevel("axe", 0);
	}
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itm, CreativeTabs tab, List lst)
    {
    	for(int i = 0; i < names.length; ++i)
    		lst.add(new ItemStack(itm,1,i));
    }
	
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
        this.field_150167_a = new IIcon[textures.length];
        this.field_150166_b = new IIcon[textures.length];

        for (int i = 0; i < textures.length; ++i)
        {
            this.field_150167_a[i] = reg.registerIcon(TBCore.modid+":"+textures[i]);
            this.field_150166_b[i] = reg.registerIcon(TBCore.modid+":"+textures[i] + "_top");
        }
    }

}

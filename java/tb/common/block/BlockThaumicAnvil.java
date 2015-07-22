package tb.common.block;

import tb.core.TBCore;
import thaumcraft.api.crafting.IInfusionStabiliser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockAnvil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockThaumicAnvil extends BlockAnvil implements IInfusionStabiliser
{
	
    public static final String[] anvilDamageNames = new String[] {"intact", "slightlyDamaged", "veryDamaged"};
    private static final String[] anvilIconNames = new String[] {"top_damaged_0", "top_damaged_1", "top_damaged_2"};
    @SideOnly(Side.CLIENT)
    private IIcon[] anvilIcons;

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        if (this.anvilRenderSide == 3 && p_149691_1_ == 1)
        {
            int k = (p_149691_2_ >> 2) % this.anvilIcons.length;
            return this.anvilIcons[k];
        }
        else
        {
            return this.blockIcon;
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName()+"base");
        this.anvilIcons = new IIcon[anvilIconNames.length];

        for (int i = 0; i < this.anvilIcons.length; ++i)
        {
            this.anvilIcons[i] = p_149651_1_.registerIcon(this.getTextureName()+anvilIconNames[i]);
        }
    }
    
    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float vecX, float vecY, float vecZ)
    {
    	if(w.isRemote)
    		return true;
    	
    	p.openGui(TBCore.instance, 0x421921, w, x, y, z);
    	
    	return true;
    }
    
	@Override
	public boolean canStabaliseInfusion(World w, int x,int y, int z) 
	{
		return true;
	}
	
}

package tb.common.block;

import java.util.Random;

import tb.core.TBCore;
import thaumcraft.api.crafting.IInfusionStabiliser;
import thaumcraft.common.Thaumcraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockVoidAnvil extends BlockAnvil implements IInfusionStabiliser
{
	
    public static final String[] anvilDamageNames = new String[] {"intact", "slightlyDamaged", "veryDamaged"};
    private static final String[] anvilIconNames = new String[] {"top_damaged_0", "top_damaged_1", "top_damaged_2"};
    @SideOnly(Side.CLIENT)
    private IIcon[] anvilIcons;

    public BlockVoidAnvil()
    {
    	super();
    	this.setHardness(12);
    	this.setResistance(Float.MAX_VALUE);
    	this.setTickRandomly(true);
    	this.setStepSound(Block.soundTypeAnvil);
    	this.setHarvestLevel("pickaxe", 3);
    }
    
    public void updateTick(World w, int x, int y, int z, Random rnd)
    {
    	super.updateTick(w, x, y, z, rnd);
    	int meta = w.getBlockMetadata(x, y, z);
    	if(meta / 4 > 0)
    	{
    		w.setBlockMetadataWithNotify(x, y, z, w.getBlockMetadata(x, y, z)-4, 3);
    	}
    }
    
    public void randomDisplayTick(World w, int x, int y, int z, Random r)
    {
    	Thaumcraft.proxy.sparkle(x+r.nextFloat(), y+r.nextFloat(), z+r.nextFloat(), 2, 5, -0.1F);
    }
    
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
    	
    	p.openGui(TBCore.instance, 0x421920, w, x, y, z);
    	
    	return true;
    }
    
	@Override
	public boolean canStabaliseInfusion(World w, int x,int y, int z) 
	{
		return true;
	}
	
}

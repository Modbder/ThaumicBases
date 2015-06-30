package tb.common.block;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockRainbowCactus extends BlockCactus {

    @SideOnly(Side.CLIENT)
    private IIcon field_150041_a;
    @SideOnly(Side.CLIENT)
    private IIcon field_150040_b;
	
	public BlockRainbowCactus()
	{
		super();
	}
	
    public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
    {
        
    }
    
    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
    
        if(world.getBlock(x, y-1, z) != this)
        {
        	ret.add(new ItemStack(this,1,0));
        	return ret;
        }
        	
        for(int i = 0; i < 3+world.rand.nextInt(8); ++i)
        	ret.add(new ItemStack(Items.dye,1,allowedDyes[world.rand.nextInt(allowedDyes.length)]));
        
        return ret;
    }
    
    public static final int[] allowedDyes = new int[]{1,2,5,2,6,7,2,8,9,10,2,11,12,13,14,2};
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "side");
        this.field_150041_a = p_149651_1_.registerIcon(this.getTextureName() + "top");
        this.field_150040_b = p_149651_1_.registerIcon(this.getTextureName() + "bottom");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return p_149691_1_ == 1 ? this.field_150041_a : (p_149691_1_ == 0 ? this.field_150040_b : this.blockIcon);
    }
	
    @Override
    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable)
    {
        Block plant = plantable.getPlant(world, x, y + 1, z);
        
        if (plant == this)
        {
            return true;
        }
        
        return super.canSustainPlant(world, x, y, z, direction, plantable);
    }
}

package tb.common.block;

import java.util.List;

import tb.core.TBCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockOldLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockTBLog extends BlockOldLog{
	
	public static final String[] names = new String[]{
		"peacefullTreeLog",
		"netherTreeLog",
		"enderTreeLog"
	};
	
	public static final String[] textures = new String[]{
		"peacefullTree/log",
		"netherTree/log",
		"enderTree/log"
	};
	
    public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
    {
	    if(world.getBlockMetadata(x, y, z)%4==2)
	    	if(entity instanceof EntityDragon)
	    		return false;
    	
    	return super.canEntityDestroy(world, x, y, z, entity);
    }
    
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
    	if(world.getBlockMetadata(x, y, z)%4==1)
    		return true;
    	
    	return super.isFlammable(world, x, y, z, face);
    }
    
    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
    	if(world.getBlockMetadata(x, y, z)%4==1)
    		return 0;
    	
    	return super.getFlammability(world, x, y, z, face);
    }
    
    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
    	if(world.getBlockMetadata(x, y, z)%4==1)
    		return 0;
    	
    	return super.getFlammability(world, x, y, z, face);
    }
    
    public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side)
    {
    	if(world.getBlockMetadata(x, y, z)%4==1)
    		return true;
    	
    	return super.isFireSource(world, x, y, z, side);
    }
	
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

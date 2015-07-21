package tb.common.block;

import DummyCore.Utils.MiscUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tb.common.tile.TileAdvAlchemicalFurnace;
import thaumcraft.common.Thaumcraft;

public class BlockAdvAlchemicalFurnace extends BlockContainer{

	public IIcon[] icons = new IIcon[5];
	
	public BlockAdvAlchemicalFurnace()
	{
		super(Material.rock);
		this.setHardness(2);
		this.setResistance(3);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		icons[0] = ir.registerIcon("thaumicbases:tFurnace/side");
		icons[1] = ir.registerIcon("thaumicbases:tFurnace/top");
		icons[2] = ir.registerIcon("thaumicbases:tFurnace/top_filled");
		icons[3] = ir.registerIcon("thaumicbases:tFurnace/front_off");
		icons[4] = ir.registerIcon("thaumicbases:tFurnace/front_on");
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileAdvAlchemicalFurnace();
	}
	
	public IIcon getIcon(int side, int md)
	{
		return side == 0 || side == 1 ? icons[1] : side == 3 ? icons[3] : icons[0];
	}
	
	public IIcon getIcon(IBlockAccess w, int x, int y, int z, int side)
	{
		TileAdvAlchemicalFurnace tile = (TileAdvAlchemicalFurnace) w.getTileEntity(x, y, z);
		if(side == w.getBlockMetadata(x, y, z))
		{
			if(tile.isBurning())
			{
				return icons[4];
			}else
			{
				return icons[3];
			}
		}
		if(side == 1)
		{
			if(tile.vis > 0)
			{
				return icons[2];
			}else
			{
				return icons[1];
			}
		}
		return icons[0];
	}
	
	public int getLightValue(IBlockAccess w, int x, int y, int z)
	{
		TileAdvAlchemicalFurnace tile = (TileAdvAlchemicalFurnace) w.getTileEntity(x, y, z);
		return tile.isBurning() ? 12 : 0;
	}
	
	public boolean hasComparatorInputOverride()
	{
		return true;
	}
	
	public int getComparatorInputOverride(World w, int x, int y, int z, int rs)
	{
		return Container.calcRedstoneFromInventory((IInventory)w.getTileEntity(x, y, z));
	}
	
	public void breakBlock(World w, int x, int y, int z, Block b, int meta)
	{
		MiscUtils.dropItemsOnBlockBreak(w, x, y, z, b, meta);
	}
	
	public void onNeighborBlockChange(World w, int x, int y, int z, Block changed)
	{
		((TileAdvAlchemicalFurnace) w.getTileEntity(x, y, z)).getBellows();
	}
	
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9)
	{
		if (w.isRemote) return true;
		TileEntity tileEntity = w.getTileEntity(x, y, z);
	
		if(!player.isSneaking() && tileEntity instanceof TileAdvAlchemicalFurnace) 
		{
			player.openGui(Thaumcraft.instance, 9, w, x, y, z);
			return true;
		}
		
		return false;
	}
	
    public void onBlockAdded(World w, int x, int y, int z)
    {
        super.onBlockAdded(w, x, y, z);
        this.determineOrientation(w, x, y, z);
    }
    
    private void determineOrientation(World w, int x, int y, int z)
    {
        if (!w.isRemote)
        {
            Block block = w.getBlock(x, y, z - 1);
            Block block1 = w.getBlock(x, y, z + 1);
            Block block2 = w.getBlock(x - 1, y, z);
            Block block3 = w.getBlock(x + 1, y, z);
            byte b0 = 3;

            if (block.func_149730_j() && !block1.func_149730_j())
            {
                b0 = 3;
            }

            if (block1.func_149730_j() && !block.func_149730_j())
            {
                b0 = 2;
            }

            if (block2.func_149730_j() && !block3.func_149730_j())
            {
                b0 = 5;
            }

            if (block3.func_149730_j() && !block2.func_149730_j())
            {
                b0 = 4;
            }

            w.setBlockMetadataWithNotify(x, y, z, b0, 2);
        }
    }
    
    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
    {
        int l = MathHelper.floor_double((double)(p_149689_5_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0)
        {
            w.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }

        if (l == 1)
        {
            w.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }

        if (l == 2)
        {
            w.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }

        if (l == 3)
        {
            w.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }
    }
	
}

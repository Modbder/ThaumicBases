package tb.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.api.crafting.IInfusionStabiliser;
import thaumcraft.common.blocks.CustomStepSound;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockCrystalBlock extends Block implements IInfusionStabiliser{
	
	public static IIcon[] icons = new IIcon[8];
	public static final String[] names = new String[]{
		"air",
		"fire",
		"water",
		"earth",
		"order",
		"entropy",
		"mixed",
		"tainted"
	};
	
	public BlockCrystalBlock()
	{
		super(Material.glass);
		setHardness(0.7F);
		setResistance(1.0F);
		setLightLevel(0.5F);
		setStepSound(new CustomStepSound("crystal", 1.0F, 1.0F));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int i = 0; i < 8; ++i)
			par3List.add(new ItemStack(par1,1,i));
	}
	
	
	public void registerBlockIcons(IIconRegister ir)
	{
		for(int i = 0; i < 8; ++i)
			icons[i] = ir.registerIcon("thaumicbases:crystal/"+names[i]);
	}
	
	public int damageDropped(int par1)
	{
		return par1;
	}

	@Override
	public boolean canStabaliseInfusion(World world, int x, int y, int z) 
	{
		return true;
	}
	
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	if(meta >= 8)
    		meta = 7;
    	return icons[meta];
    }
}

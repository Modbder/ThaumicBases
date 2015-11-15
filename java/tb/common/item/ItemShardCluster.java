package tb.common.item;

import java.util.List;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Utils.IOldItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aura.AuraHelper;

public class ItemShardCluster extends Item implements IOldItem {

	public static final String names[] = new String[]{
		"fire",
		"water",
		"earth",
		"air",
		"order",
		"entropy",
		"mixed",
		"tainted"
	};
	
	public static final int colors[] = new int[]{
		Aspect.FIRE.getColor(),
		Aspect.WATER.getColor(),
		Aspect.EARTH.getColor(),
		Aspect.AIR.getColor(),
		Aspect.ORDER.getColor(),
		Aspect.ENTROPY.getColor(),
		0xffffff,
		Aspect.FLUX.getColor()
	};
	
	public ItemShardCluster()
	{
		super();
		this.setHasSubtypes(true);
		this.setUnlocalizedName("tb.shardCluster");
	}
	
	Icon icon;
	
	@Override
	public Icon getIconFromDamage(int meta) {
		return icon;
	}

	@Override
	public Icon getIconFromItemStack(ItemStack stk) {
		return icon;
	}

	@Override
	public void registerIcons(IconRegister reg) {
		icon = reg.registerItemIcon("thaumicbases:shardCluster");

	}

	@Override
	public int getRenderPasses(ItemStack stk) {
		return 0;
	}

	@Override
	public Icon getIconFromItemStackAndRenderPass(ItemStack stk, int pass) {
		return icon;
	}

	@Override
	public boolean recreateIcon(ItemStack stk) {
		return false;
	}

	@Override
	public boolean render3D(ItemStack stk) {
		return false;
	}
	
    public int getMetadata(int meta)
    {
    	return meta;
    }

    public String getUnlocalizedName(ItemStack is)
    {
    	return super.getUnlocalizedName(is)+"."+names[is.getItemDamage()];
    }
   
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item itm, CreativeTabs tab, List lst)
    {
    	for(int i = 0; i < names.length; ++i)
    		lst.add(new ItemStack(itm,1,i));
    }
    
    public ItemStack onItemRightClick(ItemStack s, World w, EntityPlayer p)
    {
    	AspectList al = new AspectList();
    	boolean taint = false;
    	switch(s.getMetadata())
    	{
	    	case 0:
	    	{
	    		al.add(Aspect.FIRE, 8);
	    		break;
	    	}
	    	case 1:
	    	{
	    		al.add(Aspect.WATER, 8);
	    		break;
	    	}
	    	case 2:
	    	{
	    		al.add(Aspect.EARTH, 8);
	    		break;
	    	}
	    	case 3:
	    	{
	    		al.add(Aspect.AIR, 8);
	    		break;
	    	}
	    	case 4:
	    	{
	    		al.add(Aspect.ORDER, 8);
	    		break;
	    	}
	    	case 5:
	    	{
	    		al.add(Aspect.ENTROPY, 8);
	    		break;
	    	}
	    	case 6:
	    	{
	    		al.add(Aspect.FIRE,2).add(Aspect.WATER, 2).add(Aspect.EARTH,2).add(Aspect.AIR,2).add(Aspect.ORDER, 2).add(Aspect.ENTROPY, 2);
	    		break;
	    	}
	    	case 7:
	    	{
	    		al.add(Aspect.FIRE,w.rand.nextInt(10)).add(Aspect.WATER, w.rand.nextInt(10)).add(Aspect.EARTH,w.rand.nextInt(10)).add(Aspect.AIR,w.rand.nextInt(10)).add(Aspect.ORDER, w.rand.nextInt(10)).add(Aspect.ENTROPY, w.rand.nextInt(10));
	    		taint = true;
	    		break;
	    	}
    		default:
    			break;
    	}
    	
    	for(int i = 0; i < al.getAspects().length; ++i)
    	{
    		Aspect a = al.getAspects()[i];
    		int am = al.getAmount(a);
    		if(am <= 0)
    			continue;
    		
    		AuraHelper.addAura(w, p.getPosition(), a, am);
    		if(taint)
    			AuraHelper.pollute(w, p.getPosition(), 1+w.rand.nextInt(3), true);
    	}
    	
    	--s.stackSize;
    	
        return s;
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int renderPass)
    {
    	if(stack.getMetadata() == 6)
    	{
    		int index = (int) (System.currentTimeMillis() / 1000 % 7);
    		int prevIndex = index == 0 ? 6 : index - 1;
    		if(index == 6)
    			index = 7;
    		if(prevIndex == 6)
    			prevIndex = 7;
    		
    		int colorCurrent = colors[index];
    		int colorPrev = colors[prevIndex];
    		
    		double percentage = System.currentTimeMillis() / 10 % 100 / 100D;
    		
    	    double cR = (double)((colorCurrent & 0xFF0000) >> 16) / 0xff;
    		double cG = (double)((colorCurrent & 0xFF00) >> 8) / 0xff;
    		double cB = (double)((colorCurrent & 0xFF)) / 0xff;
    		
    	    double pR = (double)((colorPrev & 0xFF0000) >> 16) / 0xff;
    		double pG = (double)((colorPrev & 0xFF00) >> 8) / 0xff;
    		double pB = (double)((colorPrev & 0xFF)) / 0xff;
    		
    		double aR = (pR * (1-percentage)) + (cR * percentage);
    		double aG = (pG * (1-percentage)) + (cG * percentage);
    		double aB = (pB * (1-percentage)) + (cB * percentage);
    		int aColor = ((int)(aR * 0xff) << 16) + ((int)(aG * 0xff) << 8) + ((int)(aB * 0xff));
    		return aColor;
    	}
    	return colors[stack.getMetadata()];
    }
}

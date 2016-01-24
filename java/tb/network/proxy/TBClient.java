package tb.network.proxy;

import java.lang.reflect.Field;

import DummyCore.Client.GuiCommon;
import DummyCore.Client.RenderAccessLibrary;
import DummyCore.Utils.ASMManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import tb.client.RevolverEvents;
import tb.client.gui.GuiRevolver;
import tb.client.gui.GuiThaumicAnvil;
import tb.client.gui.GuiVoidAnvil;
import tb.client.render.block.BraizerRenderer;
import tb.client.render.block.CampfireRenderer;
import tb.client.render.entity.RenderAspectOrb;
import tb.client.render.entity.RenderBullet;
import tb.client.render.item.AuraLinkerItemRenderer;
import tb.client.render.item.CastingBraceletRenderer;
import tb.client.render.item.HerobrinesScytheMH;
import tb.client.render.item.NodeFociRenderer;
import tb.client.render.item.NodeManipulatorItemRenderer;
import tb.client.render.item.RenderRevolver;
import tb.client.render.item.SpawnerCompassRenderer;
import tb.client.render.item.UkuleleRenderer;
import tb.client.render.tile.RenderAuraLinker;
import tb.client.render.tile.RenderNodeManipulator;
import tb.client.render.tile.RenderOverchanter;
import tb.common.entity.EntityAspectOrb;
import tb.common.entity.EntityRevolverBullet;
import tb.common.event.TBEventHandler;
import tb.common.inventory.ContainerOverchanter;
import tb.common.item.ItemUkulele;
import tb.common.tile.TileAuraLinker;
import tb.common.tile.TileNodeManipulator;
import tb.common.tile.TileOverchanter;
import tb.init.TBBlocks;
import tb.init.TBItems;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.beams.FXArc;
import thaumcraft.client.fx.particles.FXSparkle;

public class TBClient extends TBServer {
	
	public static class GuitarSound extends MovingSound
	{
		public int notUsingTicks = 0;
		protected GuitarSound(ResourceLocation snd) 
		{
			super(snd);
		}
		
		@Override
		public void update() 
		{
			if(Minecraft.getMinecraft().thePlayer == null || !Minecraft.getMinecraft().thePlayer.isUsingItem() || Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() == null || Minecraft.getMinecraft().thePlayer.isDead || Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() == null || !(Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemUkulele))
			{
				++notUsingTicks;
				if(notUsingTicks >= 8)
				{
					TBEventHandler.clientUkuleleSoundPlayDelay = 0;
					this.donePlaying = true;
					notUsingTicks = 0;
				}
			}
			else
			{
				notUsingTicks = 0;
				this.xPosF = (float) Minecraft.getMinecraft().thePlayer.posX;
				this.yPosF = (float) Minecraft.getMinecraft().thePlayer.posY;
				this.zPosF = (float) Minecraft.getMinecraft().thePlayer.posZ;
			}
		}
		
	}
	
	public void playGuitarSound(String sound)
	{
		MovingSound snd = new GuitarSound(new ResourceLocation(sound)); 
		Minecraft.getMinecraft().getSoundHandler().playSound(snd);
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		if(ID == 0x421922)
		{
			TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
			
			if(tile != null)
			{
				if(tile instanceof TileOverchanter)
				{
					return new GuiCommon(new ContainerOverchanter(player.inventory,tile), tile);
				}
			}
		}else
		{
			if(ID == 0x421921)
				return new GuiThaumicAnvil(player.inventory, world, x, y, z);
			
			if(ID == 0x421920)
				return new GuiVoidAnvil(player.inventory, world, x, y, z);
			
			if(ID == 0x421919)
				return new GuiRevolver(player.inventory, world, x, y, z);
		}
		
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public void registerRenderInformation()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileOverchanter.class, new RenderOverchanter());
		ClientRegistry.bindTileEntitySpecialRenderer(TileAuraLinker.class, new RenderAuraLinker());
		ClientRegistry.bindTileEntitySpecialRenderer(TileNodeManipulator.class, new RenderNodeManipulator());
		MinecraftForge.EVENT_BUS.register(new RevolverEvents());
		
		RenderAccessLibrary.registerItemRenderingHandler(TBItems.ukulele, new UkuleleRenderer());
		RenderAccessLibrary.registerItemRenderingHandler(TBItems.revolver, new RenderRevolver());
		RenderAccessLibrary.registerItemRenderingHandler(Item.getItemFromBlock(TBBlocks.nodeManipulator), new NodeManipulatorItemRenderer());
		RenderAccessLibrary.registerItemRenderingHandler(Item.getItemFromBlock(TBBlocks.auraLinker), new AuraLinkerItemRenderer());
		RenderAccessLibrary.registerRenderingHandler(new BraizerRenderer());
		RenderAccessLibrary.registerRenderingHandler(new CampfireRenderer());
		RenderAccessLibrary.registerItemRenderingHandler(TBItems.nodeFoci, new NodeFociRenderer());
		RenderAccessLibrary.registerItemRenderingHandler(TBItems.spawnerCompass, new SpawnerCompassRenderer());
		RenderAccessLibrary.registerItemRenderingHandler(TBItems.castingBracelet, new CastingBraceletRenderer());
		RenderAccessLibrary.registerItemMatrixHandler(TBItems.herobrinesScythe, new HerobrinesScytheMH());
		RenderingRegistry.registerEntityRenderingHandler(EntityAspectOrb.class, new RenderAspectOrb());
		RenderingRegistry.registerEntityRenderingHandler(EntityRevolverBullet.class, new RenderBullet());
	}
	
	@Override
	public void lightning(World world, double sx, double sy, double sz, double ex, double ey, double ez, int dur, float curve, int speed, int type)
	{
		FXArc efa = new FXArc(world, sx, sy, sz, ex, ey, ez, 0.1F, 0.0F, 0.1F, 0.1F);
		try
		{
			Field f = EntityFX.class.getDeclaredField(ASMManager.chooseByEnvironment("particleMaxAge", "field_70547_e"));
			f.setAccessible(true);
			f.setInt(efa, dur);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		FMLClientHandler.instance().getClient().effectRenderer.addEffect(efa);
	}
	
	public void sparkle(World w, double x, double y, double z, double dx, double dy, double dz, int color, float scale)
	{
		FXSparkle fx = new FXSparkle(w,x,y,z,dx,dy,dz,scale,color,1);
		fx.noClip = true;
		ParticleEngine.instance.addEffect(w, fx);
	}
	
	public World clientWorld()
	{
		return Minecraft.getMinecraft().theWorld;
	}

}

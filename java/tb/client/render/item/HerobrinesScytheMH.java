package tb.client.render.item;

import javax.vecmath.Matrix4f;

import org.lwjgl.util.vector.Vector3f;

import DummyCore.Client.IModelMatrixHandler;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;

@SuppressWarnings("deprecation")
public class HerobrinesScytheMH implements IModelMatrixHandler {

	@Override
	public Matrix4f handlePerspective(TransformType cameraTransformType, ItemStack is) {
		if(cameraTransformType == TransformType.GUI)
		{
			return ForgeHooksClient.getMatrix(new ItemTransformVec3f(
				new Vector3f(0,0,0.75F),
				new Vector3f(0,0,0),
				new Vector3f(1,1,1)
			));
		}
		if(cameraTransformType == TransformType.FIRST_PERSON)
		{
			return ForgeHooksClient.getMatrix(new ItemTransformVec3f(
				new Vector3f(0,0.8F,0),
				new Vector3f(0,0.4F,0),
				new Vector3f(3,3,1F)
			));
		}
		if(cameraTransformType == TransformType.THIRD_PERSON)
		{
			return ForgeHooksClient.getMatrix(new ItemTransformVec3f(
				new Vector3f(0,4.8F,2.2F),
				new Vector3f(0,0,-0.1F),
				new Vector3f(2,2,1)
			));
		}
		return null;
	}

}

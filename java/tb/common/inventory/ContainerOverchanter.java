package tb.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerOverchanter extends Container{

	public ContainerOverchanter(InventoryPlayer playerInv, TileEntity blockInv)
	{
		this.addSlotToContainer(new Slot((IInventory) blockInv,0,80,20));
        int i;

        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
        }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
    public ItemStack transferStackInSlot(EntityPlayer p, int slot)
    {
    	return null;
    }

}

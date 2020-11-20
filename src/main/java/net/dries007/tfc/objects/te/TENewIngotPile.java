/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.te;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

import mcp.MethodsReturnNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TENewIngotPile extends TEBase
{
    private final Deque<ItemStack> items;

    public TENewIngotPile()
    {
        items = new LinkedList<>();
    }

    public boolean isFull()
    {
        return items.size() > 64;
    }

    public void addIngot(ItemStack stack)
    {
        if (isFull())
        {
            return;
        }
        ItemStack placedStack = stack.copy();
        placedStack.setCount(1);
        items.add(placedStack);
    }

    public void removeIngot(EntityPlayer player)
    {
        if (!items.isEmpty())
        {
            ItemHandlerHelper.giveItemToPlayer(player, items.removeLast());
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        NBTTagList stacks = tag.getTagList("items", 9);
        for (int i = 0; i < stacks.tagCount(); i++)
        {
            items.add(new ItemStack(stacks.getCompoundTagAt(i)));
        }
        super.readFromNBT(tag);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        NBTTagList stacks = new NBTTagList();
        for (ItemStack item : items)
        {
            stacks.appendTag(item.serializeNBT());
        }
        tag.setTag("items", stacks);
        return super.writeToNBT(tag);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared()
    {
        return 1024.0D;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return INFINITE_EXTENT_AABB;
    }

    public void onBreakBlock()
    {
        for (ItemStack item : items)
        {
            InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), item);
        }
    }

    public Collection<ItemStack> getContents()
    {
        return items;
    }
}

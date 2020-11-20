/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.client.render;

import java.util.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import net.dries007.tfc.client.model.ModelIngotPile;
import net.dries007.tfc.objects.te.TENewIngotPile;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@SideOnly(Side.CLIENT)
public class TESRNewIngotPile extends TileEntitySpecialRenderer<TENewIngotPile>
{
    private final ModelIngotPile model = new ModelIngotPile();

    private final Object2ObjectMap<ItemStack, ResourceLocation> cache = new Object2ObjectOpenCustomHashMap<>(new Hash.Strategy<ItemStack>()
    {
        @Override
        public int hashCode(ItemStack stack)
        {
            return Objects.hash(stack.getItem(), stack.getMetadata());
        }
        @Override
        public boolean equals(ItemStack a, ItemStack b)
        {
            return a.getItem() == b.getItem() && a.getMetadata() == b.getMetadata();
        }
    });

    @Override
    public void render(TENewIngotPile te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        model.renderIngots(te.getContents());
        GlStateManager.popMatrix();
    }
}

/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.client.model;

import java.util.Collection;
import java.util.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@SideOnly(Side.CLIENT)
public class ModelIngotPile extends ModelBase
{
    public ModelRendererTFC[] renderer = new ModelRendererTFC[64];

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

    public ModelIngotPile()
    {

        for (int n = 0; n < 64; n++)
        {
            this.renderer[n] = new ModelRendererTFC(this, 0, 0);
            int m = (n + 8) / 8;
            float x = (n % 4) * 0.25f;
            float y = (m - 1) * 0.125f;
            float z = 0;

            if (n % 8 >= 4) z = .5F;

            renderer[n].cubeList.add(new ModelIngot(renderer[n], renderer[n].textureOffsetX, renderer[n].textureOffsetY));
            renderer[n].offsetY = y;
            if (m % 2 == 1)
            {
                renderer[n].rotateAngleY = 1.56F;
                renderer[n].offsetX = x;
                renderer[n].offsetZ = z + .5F;
            }
            else
            {
                renderer[n].offsetX = z;
                renderer[n].offsetZ = x;
            }
        }
    }

    public void renderIngots(int i)
    {
        for (int n = 0; n < i; n++)
        {
            renderer[n].render(0.0625F / 2F);
        }
    }

    public void renderIngots(Collection<ItemStack> stacks)
    {
        int n = 0;
        for (ItemStack stack : stacks)
        {
            ResourceLocation location;
            if ((location = cache.get(stack)) == null)
            {
                cache.put(stack, location = new ResourceLocation(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack).getQuads(null, null, 0L).get(0).getSprite().getIconName().concat(".png")));
            }
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(MOD_ID, "textures/blocks/metal/bismuth" + ".png"));
            renderer[n++].render(0.0625F / 2F);
        }
    }

}

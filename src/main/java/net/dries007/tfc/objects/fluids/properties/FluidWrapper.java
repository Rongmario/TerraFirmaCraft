/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.fluids.properties;

import java.util.Map;
import javax.annotation.Nonnull;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

/**
 * This is a separate class from {@link Fluid} to avoid subclassing.
 * From LexManos:
 * > yes thats [sic] the point, that if you share a name you use whatever is registered
 * > you are not special and do not need your special functionality
 * > IF you do NOT want to work well with others and you have to  be a special special snowflake, namespace your shit.
 * So in order to keep TFC working well with other mods, we shall use whatever fluids are registered, but we still need to map them to properties
 */
public class FluidWrapper
{
    private final String fluidName;
    private final Map<Class<? extends FluidProperty>, FluidProperty> properties;

    public FluidWrapper(@Nonnull Fluid fluid)
    {
        this.fluidName = fluid.getName();
        this.properties = new Object2ObjectOpenHashMap<>();
    }

    public FluidWrapper(@Nonnull String fluidName)
    {
        this.fluidName = fluidName;
        this.properties = new Object2ObjectOpenHashMap<>();
    }

    @Nonnull
    public Fluid get()
    {
        return FluidRegistry.getFluid(fluidName);
    }

    public <T extends FluidProperty> FluidWrapper with(Class<T> key, T value)
    {
        properties.put(key, key.cast(value));
        return this;
    }

    /**
     * Used to add properties to TFC fluids, such as making them drinkable, or giving them a metal.
     */
    public <T> T get(Class<T> key)
    {
        return key.cast(properties.get(key));
    }

    /**
     * Used externally to remove a specific property from a fluid.
     */
    public <T> T remove(Class<T> key)
    {
        return key.cast(properties.remove(key));
    }
}

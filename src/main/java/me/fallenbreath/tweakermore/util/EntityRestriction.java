/*
 * This file is part of the TweakerMore project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2026  Fallen_Breath and contributors
 *
 * TweakerMore is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TweakerMore is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TweakerMore.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.fallenbreath.tweakermore.util;

import fi.dy.masa.malilib.util.restrictions.UsageRestriction;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import me.fallenbreath.tweakermore.TweakerMoreMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

//#if MC >= 11903
//$$ import net.minecraft.core.registries.BuiltInRegistries;
//#else
import net.minecraft.core.Registry;
//#endif

public class EntityRestriction extends UsageRestriction<EntityType<?>> {
    protected void setValuesForList(Set<EntityType<?>> set, List<String> names) {
        for(String name : names) {
            try {
                Optional<EntityType<?>> entityType =
                        //#if MC >= 11903
                        //$$ BuiltInRegistries
                        //#else
                        Registry
                                //#endif
                                .ENTITY_TYPE.getOptional(ResourceLocation.tryParse(name));
                if (entityType.isPresent()) {
                    set.add(entityType.get());
                    continue;
                }
            } catch (Exception ignored) {
            }

            TweakerMoreMod.LOGGER.warn("Invalid entity name in a black- or whitelist: '{}'", name);
        }

    }
}

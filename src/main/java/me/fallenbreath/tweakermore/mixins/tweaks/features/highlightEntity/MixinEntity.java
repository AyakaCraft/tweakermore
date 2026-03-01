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

package me.fallenbreath.tweakermore.mixins.tweaks.features.highlightEntity;

import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinEntity {
    @Shadow
    public abstract EntityType<?> getType();

    @Shadow
    //#if MC > 11904
    //$$ private
    //#else
    public
    //#endif
    Level level;

    @Inject(
            method =
                    //#if MC >= 11700
                    //$$ "isCurrentlyGlowing"
                    //#else
                    "isGlowing"
            //#endif
            ,
            at = @At("RETURN"),
            cancellable = true
    )
    private void checkHighlightEntity(CallbackInfoReturnable<Boolean> cir) {
        if (!TweakerMoreConfigs.HIGHLIGHT_ENTITY.getBooleanValue() || cir.getReturnValue() || !this.level.isClientSide()) {
            return;
        }

        cir.setReturnValue(TweakerMoreConfigs.HIGHLIGHT_ENTITY_RESTRICTION.isAllowed(this.getType()));
    }
}
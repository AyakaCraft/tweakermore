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

package me.fallenbreath.tweakermore.mixins.tweaks.porting.handRestockFixPorting;

import fi.dy.masa.tweakeroo.config.FeatureToggle;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import me.fallenbreath.tweakermore.impl.porting.handRestockFixPorting.HandRestockUtils;
import me.fallenbreath.tweakermore.util.ModIds;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Restriction(require = {
        @Condition(value = ModIds.tweakeroo),
        @Condition(value = ModIds.minecraft, versionPredicates = "<1.21")
})
@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    @Shadow public abstract InteractionHand getUsedItemHand();
    private MixinLivingEntity(EntityType<?> type, Level worldIn)
    {
        super(type, worldIn);
    }

    @Inject(method = "completeUsingItem", at = @At("RETURN"))
    private void onItemConsumed(CallbackInfo ci)
    {
        if (TweakerMoreConfigs.HAND_RESTOCK_FIX_PORTING.getBooleanValue() && FeatureToggle.TWEAK_HAND_RESTOCK.getBooleanValue())
        {
            if ((Object) this instanceof Player)
            {
                HandRestockUtils.onProcessRightClickPost((Player) (Object) this, this.getUsedItemHand());
            }
        }
    }
}

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

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import me.fallenbreath.tweakermore.impl.porting.handRestockFixPorting.HandRestockUtils;
import me.fallenbreath.tweakermore.util.ModIds;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Restriction(require = {
        @Condition(value = ModIds.tweakeroo),
        @Condition(value = ModIds.minecraft, versionPredicates = "<1.21")
})
@Mixin({MultiPlayerGameMode.class})
public abstract class MixinClientPlayerInteractionManager {
    @Inject(method = "useItem",
            at = @At("TAIL"))
    private void onProcessRightClickPost(Player player,
                                         //#if MC >= 11900
                                         //#else
                                         Level level,
                                         //#endif
                                         InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir)
    {
        //#if MC >= 11500
        if (TweakerMoreConfigs.HAND_RESTOCK_FIX_PORTING.getBooleanValue() && cir.getReturnValue().consumesAction())
        {
            HandRestockUtils.onProcessRightClickPost(player, hand);
        }
        //#endif
    }

}

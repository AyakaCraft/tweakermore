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

package me.fallenbreath.tweakermore.mixins.tweaks.mc_tweaks.flatDigger;

import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class MixinClientPlayerInteractionManager {
    @Inject(
            method = "startDestroyBlock",
            at = @At("HEAD"),
            cancellable = true
    )
    private void flatDigger(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (this.shouldFlatDigger(pos)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(
            method = "continueDestroyBlock",
            at = @At("HEAD"),
            cancellable = true
    )
    private void flatDigger1(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (this.shouldFlatDigger(pos)) {
            cir.setReturnValue(false);
        }
    }

    @Unique
    private boolean shouldFlatDigger(BlockPos pos) {
        Level level = Minecraft.getInstance().level;
        Player player = Minecraft.getInstance().player;

        if (TweakerMoreConfigs.FLAT_DIGGER.getBooleanValue() && level != null && player != null) {
            return !player.isShiftKeyDown() && pos.getY() < (
                    //#if MC >= 11600
                    //$$ player.blockPosition()
                    //#else
                    new BlockPos(player)
                    //#endif
            ).getY();
        }

        return false;
    }
}

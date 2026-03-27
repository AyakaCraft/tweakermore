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

package me.fallenbreath.tweakermore.mixins.tweaks.mc_tweaks.daytimeOverride;

import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import net.minecraft.client.ClientClockManager;
import net.minecraft.world.clock.ClockNetworkState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Modify daytime here too,
 * so the logic used when the client received a time update packet can be reused by us (gamerule changing etc.)
 */
@Mixin(ClientClockManager.class)
public abstract class ClientClockManagerMixin
{
	// currently `handleUpdates` is only called in `ClientPacketListener#handleSetTime`,
	// which means that only the time update packet can trigger this
	@ModifyVariable(
			method = "lambda$handleUpdates$0",
			at = @At("HEAD"),
			argsOnly = true
	)
	private ClockNetworkState overwriteDayTime_modifySetter(ClockNetworkState state)
	{
		if (TweakerMoreConfigs.DAYTIME_OVERRIDE.getBooleanValue())
		{
			long dayTime = TweakerMoreConfigs.DAYTIME_OVERRIDE_VALUE.getIntegerValue();
			state = new ClockNetworkState(dayTime, 0, 0);
		}
		return state;
	}
}

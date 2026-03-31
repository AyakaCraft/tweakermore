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

package me.fallenbreath.tweakermore.mixins.tweaks.mc_tweaks.disableCameraFrustumCulling;

import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import me.fallenbreath.tweakermore.impl.mc_tweaks.disableFrustumChunkCulling.CouldBeAlwaysVisibleFrustum;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.culling.Frustum;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * <  mc1.15.2: subproject 1.14.4 (dummy impl)
 * <= mc1.15.2: subproject 1.15.2 (main project)
 * >= mc26.1  : subproject 26.1       <--------
 */
@Mixin(Camera.class)
public abstract class WorldRendererMixin
{
	@Shadow
	private Frustum cullFrustum;

	@Inject(
			method = "prepareCullFrustum",
			at = @At("TAIL")
	)
	private void disableCameraFrustumCulling_impl(CallbackInfo ci)
	{
		Frustum frustum = this.cullFrustum;
		if (frustum instanceof CouldBeAlwaysVisibleFrustum)
		{
			boolean alwaysVisible = TweakerMoreConfigs.DISABLE_CAMERA_FRUSTUM_CULLING.getBooleanValue();
			((CouldBeAlwaysVisibleFrustum)frustum).setAlwaysVisible$TKM(alwaysVisible);
		}
	}
}

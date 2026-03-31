/*
 * This file is part of the TweakerMore project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2023  Fallen_Breath and contributors
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

package me.fallenbreath.tweakermore.mixins.tweaks.features.villagerOfferUsesDisplay;

import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC >= 12000
//$$ import net.minecraft.client.gui.GuiGraphics;
//#endif

//#if MC >= 11600
//$$ import com.mojang.blaze3d.vertex.PoseStack;
//$$ import net.minecraft.network.chat.Component;
//$$ import me.fallenbreath.tweakermore.util.Messenger;
//#endif

@Mixin(targets = "net.minecraft.client.gui.screens.inventory.MerchantScreen$TradeOfferButton")
public abstract class MerchantScreenWidgetButtonPageMixin extends Button
{
	@SuppressWarnings("target")
	@Shadow @Final
	//#if MC >= 26.1
	//$$ MerchantScreen this$0;
	//#else
	MerchantScreen field_19166;
	//#endif

	@Shadow @Final int index;

	public MerchantScreenWidgetButtonPageMixin(
			int x, int y, int width, int height,
			//#if MC >= 11600
			//$$ Component message,
			//#else
			String message,
			//#endif
			OnPress onPress

			//#if MC >= 11903
			//$$ , CreateNarration createNarration
			//#endif
	)
	{
		super(
				x, y, width, height, message, onPress
				//#if MC >= 11903
				//$$ , createNarration
				//#endif
		);
	}

	@Inject(
			//#if MC >= 26.1
			//$$ method = "extractToolTip",
			//#else
			method = "renderToolTip",
			//#endif
			at = @At(
					//#if MC >= 11903
					//$$ value = "INVOKE",
					//$$ target = "Lnet/minecraft/client/gui/screens/inventory/MerchantScreen$TradeOfferButton;getX()I",
					//#else
					value = "FIELD",
					target = "Lnet/minecraft/client/gui/screens/inventory/MerchantScreen$TradeOfferButton;x:I",
					//#endif
					ordinal = 0
			)
	)
	private void renderMaxUsesAmount(
			//#if MC >= 12000
			//$$ GuiGraphics context,
			//#elseif MC >= 11600
			//$$ PoseStack matrices,
			//#endif
			int mouseX, int mouseY, CallbackInfo ci
	)
	{
		if (TweakerMoreConfigs.VILLAGER_OFFER_USES_DISPLAY.getBooleanValue())
		{
			int x =
					//#if MC >= 11903
					//$$ this.getX();
					//#else
					this.x;
					//#endif
			if (x + 50 <= mouseX && mouseX <= x + 65)
			{
				//#if MC >= 26.1
				//$$ MerchantScreen merchantScreen = this.this$0;
				//#else
				MerchantScreen merchantScreen = this.field_19166;
				//#endif

				MerchantOffer offer = merchantScreen.getMenu().getOffers().get(this.index + ((MerchantScreenAccessor)merchantScreen).getIndexStartOffset());

				String text = String.format("%d / %d", offer.getUses(), offer.getMaxUses());

				//#if MC >= 12000
				//$$ context.renderTooltip(((ScreenAccessor)merchantScreen).getTextRenderer(), Messenger.s(text), mouseX, mouseY);
				//#else
				merchantScreen.renderTooltip(
						//#if MC >= 11600
						//$$ matrices, Messenger.s(text),
						//#else
						text,
						//#endif
						mouseX, mouseY
				);
				//#endif
			}
		}
	}
}

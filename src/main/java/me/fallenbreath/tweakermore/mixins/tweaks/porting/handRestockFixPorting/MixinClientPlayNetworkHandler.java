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
import fi.dy.masa.tweakeroo.tweaks.PlacementTweaks;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import me.fallenbreath.tweakermore.impl.porting.handRestockFixPorting.HandRestockUtils;
import me.fallenbreath.tweakermore.util.ModIds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Restriction(require = {
        @Condition(value = ModIds.tweakeroo),
        @Condition(value = ModIds.minecraft, versionPredicates = "<1.21")
})
@Mixin({ClientPacketListener.class})
public abstract class MixinClientPlayNetworkHandler {

    @Inject(method = "handleEntityEvent",
            at = @At(value = "INVOKE", ordinal = 0,
                    target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;findTotem(Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/item/ItemStack;"))
    private void onPlayerUseTotemOfUndying(ClientboundEntityEventPacket packet, CallbackInfo ci)
    {
        if(!TweakerMoreConfigs.HAND_RESTOCK_FIX_PORTING.getBooleanValue()) return;
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null)
        {
            return;
        }
        if (FeatureToggle.TWEAK_HAND_RESTOCK.getBooleanValue())
        {
            for (InteractionHand hand : InteractionHand.values())
            {
                if (minecraft.player.getItemInHand(hand).getItem() == Items.TOTEM_OF_UNDYING)
                {
                    PlacementTweaks.cacheStackInHand(hand);
                    // the slot update packet goes after this packet, let's set it to empty and restock
                    minecraft.player.setItemInHand(hand, ItemStack.EMPTY);
                    HandRestockUtils.onProcessRightClickPost(minecraft.player, hand);
                }
            }
        }
    }
}

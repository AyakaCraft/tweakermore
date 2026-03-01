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

package me.fallenbreath.tweakermore.mixins.tweaks.mod_tweaks.handRestockReverseOrder;

import fi.dy.masa.tweakeroo.util.InventoryUtils;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import me.fallenbreath.tweakermore.util.ModIds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Restriction(require = @Condition(ModIds.tweakeroo))
@Mixin(InventoryUtils.class)
public abstract class InventoryUtilsMixin {
    @ModifyArg(method = "restockNewStackToHand",
            at = @At(value = "INVOKE", remap = false, target = "Lfi/dy/masa/tweakeroo/util/InventoryUtils;findSlotWithItem(Lnet/minecraft/world/inventory/AbstractContainerMenu;Lnet/minecraft/world/item/ItemStack;ZZ)I"),
            index = 3)
    private static boolean reverse(boolean reverse){
        return !TweakerMoreConfigs.HAND_RESTOCK_REVERSE_ORDER.getBooleanValue() && reverse;
    }
}

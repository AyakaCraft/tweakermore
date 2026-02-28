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

package me.fallenbreath.tweakermore.mixins.tweaks.mc_tweaks.englishSearch;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.fallenbreath.tweakermore.TweakerMoreMod;
import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.SessionSearchTrees;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import me.fallenbreath.tweakermore.util.TranslatableUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.stream.Stream;

@Mixin(SessionSearchTrees.class)
public abstract class SessionSearchTreesMixin {
    @Unique
    private static final Language language = LanguageInvoker.invokeCreate();

    @WrapMethod(method = "getTooltipLines")
    private static Stream<String> getTooltipLines(Stream<ItemStack> stacks, Item.TooltipContext context, TooltipFlag type, Operation<Stream<String>> original) {
        if(TweakerMoreConfigs.ENGLISH_SEARCH.getBooleanValue()) {
                return stacks.flatMap(stack -> stack.getTooltipLines(context, null, type).stream())
                        .flatMap(text -> Stream.of(TranslatableUtils.getFormattedTextAsString(text, language), text.getString()))
                        .map(str -> ChatFormatting.stripFormatting(str).trim())
                        .filter(string -> !string.isEmpty());
        } else {
            return original.call(stacks, context, type);
        }
    }
}

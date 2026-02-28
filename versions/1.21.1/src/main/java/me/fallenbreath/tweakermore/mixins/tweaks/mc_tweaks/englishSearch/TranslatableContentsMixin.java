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

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.fallenbreath.tweakermore.util.TranslatableUtils;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.network.chat.contents.TranslatableFormatException;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@Mixin(TranslatableContents.class)
public abstract class TranslatableContentsMixin {
    @Shadow
    public abstract String getKey();

    @Shadow
    public abstract String getFallback();

    @Shadow
    @Nullable
    private Language decomposedWith;

    @Shadow
    private List<FormattedText> decomposedParts;

    @Invoker("decomposeTemplate")
    public abstract void invokeDecomposeTemplate(String formatTemplate, Consumer<FormattedText> consumer);

    @WrapMethod(method = "visit(Lnet/minecraft/network/chat/FormattedText$ContentConsumer;)Ljava/util/Optional;")
    private <T> Optional<T> visitContentConsumer(FormattedText.ContentConsumer<T> contentConsumer, Operation<Optional<T>> original) {
        if (contentConsumer instanceof TranslatableUtils.ContentConsumerWithLanguage<T> withLanguage) {
            Language language = withLanguage.language;
            if (language != this.decomposedWith) {
                this.decomposedWith = language;
                String string = this.getFallback() != null ? language.getOrDefault(this.getKey(), this.getFallback()) : language.getOrDefault(this.getKey());
                try {
                    ImmutableList.Builder<FormattedText> builder = ImmutableList.builder();
                    Objects.requireNonNull(builder);
                    this.invokeDecomposeTemplate(string, builder::add);
                    this.decomposedParts = builder.build();
                } catch (TranslatableFormatException var4) {
                    this.decomposedParts = ImmutableList.of(FormattedText.of(string));
                }
            }
            for (FormattedText formattedText : this.decomposedParts) {
                Optional<T> optional = formattedText.visit(contentConsumer);
                if (optional.isPresent()) {
                    return optional;
                }
            }
            return Optional.empty();
        } else {
            return original.call(contentConsumer);
        }
    }

}

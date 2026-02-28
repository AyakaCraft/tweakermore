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

package me.fallenbreath.tweakermore.util;

import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import me.fallenbreath.tweakermore.mixins.tweaks.mc_tweaks.englishSearch.LanguageInvoker;
import net.minecraft.ChatFormatting;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class TranslatableUtils {
    private static final Language language = LanguageInvoker.invokeCreate();

    public static Stream<String> getString(Stream<Component> components){
        if (TweakerMoreConfigs.ENGLISH_SEARCH.getBooleanValue())
            return components.flatMap(text -> Stream.of(getFormattedTextAsString(text, language), text.getString()))
                .map(str -> ChatFormatting.stripFormatting(str).trim())
                .filter(string -> !string.isEmpty());
        else return components.map((component) -> ChatFormatting.stripFormatting(component.getString()).trim()).filter((string) -> !string.isEmpty());
    }

    public static String getFormattedTextAsString(FormattedText formattedText, Language language) {
        StringBuilder sb = new StringBuilder();
        formattedText.visit(new ContentConsumerWithLanguage<>((Consumer<String>) sb::append, language));
        return sb.toString();
    }

    public static final class ContentConsumerWithLanguage<T> implements FormattedText.ContentConsumer<T> {

        public final Language                         language;
        public final FormattedText.ContentConsumer<T> contentConsumer;

        public ContentConsumerWithLanguage(Consumer<String> consumer, Language language) {
            this.contentConsumer = s -> {
                consumer.accept(s);
                return Optional.empty();
            };
            this.language = language;
        }

        public ContentConsumerWithLanguage(FormattedText.ContentConsumer<T> contentConsumer, Language language) {
            this.contentConsumer = contentConsumer;
            this.language = language;
        }

        @Override
        public @NotNull Optional<T> accept(String string) {
            return contentConsumer.accept(string);
        }

    }

}
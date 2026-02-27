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
//        TweakerMoreMod.LOGGER.info("englishSearch");
                return stacks.flatMap(stack -> stack.getTooltipLines(context, null, type)
                                .stream()
                                .map(text -> {
                                    Language orig = Language.getInstance();
                                    Language.inject(language);
                                    TweakerMoreMod.LOGGER.info(orig.toString());
                                    String res = text.getString();
                                    Language.inject(orig);
//                                  TweakerMoreMod.LOGGER.info(res);
                                    return ChatFormatting.stripFormatting(res).trim();
                                })).map(String::trim)
                        .filter(string -> !string.isEmpty());
        } else {
            return original.call(stacks, context, type);
        }
    }
}

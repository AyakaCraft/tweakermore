package me.fallenbreath.tweakermore.mixins.tweaks.mc_tweaks.englishSearch;

import net.minecraft.locale.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Language.class)
public interface LanguageInvoker {
    @Invoker("loadDefault")
    static Language invokeCreate() {
        throw new AssertionError();
    }
}
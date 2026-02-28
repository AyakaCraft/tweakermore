package me.fallenbreath.tweakermore.mixins.tweaks.mc_tweaks.englishSearch;

import me.fallenbreath.tweakermore.util.mixin.DummyClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.searchtree.SearchRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

//#if MC >= 12100
//$$ @Mixin(DummyClass.class)
//$$ public interface MinecraftAccessor {
//$$ }
//#else
@Mixin(Minecraft.class)
public interface MinecraftAccessor {
    @Accessor("searchRegistry")
    SearchRegistry getSearchRegistry();
}
//#endif

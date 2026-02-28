package me.fallenbreath.tweakermore.impl.mc_tweaks.englishSearch;

import me.fallenbreath.tweakermore.mixins.tweaks.mc_tweaks.englishSearch.MinecraftAccessor;
import net.minecraft.client.Minecraft;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import net.minecraft.server.packs.resources.ResourceManager;

public class EnglishSearchHelper
{
    public static void onConfigValueChanged(ConfigBoolean configBoolean) {
        ((MinecraftAccessor) Minecraft.getInstance()).getSearchRegistry().onResourceManagerReload(ResourceManager.Empty.INSTANCE);
    }
}

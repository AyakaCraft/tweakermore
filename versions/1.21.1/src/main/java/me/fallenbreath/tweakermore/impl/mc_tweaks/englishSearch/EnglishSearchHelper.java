package me.fallenbreath.tweakermore.impl.mc_tweaks.englishSearch;

import net.minecraft.client.Minecraft;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import net.minecraft.client.multiplayer.ClientPacketListener;

public class EnglishSearchHelper
{
    public static void onConfigValueChanged(ConfigBoolean configBoolean) {
        ClientPacketListener clientPacketListener = Minecraft.getInstance().getConnection();
        if (clientPacketListener != null) {
            clientPacketListener.updateSearchTrees();
        }
    }
}

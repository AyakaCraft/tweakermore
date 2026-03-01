package me.fallenbreath.tweakermore.impl.porting.handRestockFixPorting;

import me.fallenbreath.tweakermore.mixins.tweaks.porting.handRestockFixPorting.PlacementTweaksAccessor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

public class HandRestockUtils
{
    public static void onProcessRightClickPost(Player player, InteractionHand hand)
    {
        PlacementTweaksAccessor.invokeTryRestockHand(player, hand, PlacementTweaksAccessor.getStackBeforeUse()[hand.ordinal()]);
    }
}

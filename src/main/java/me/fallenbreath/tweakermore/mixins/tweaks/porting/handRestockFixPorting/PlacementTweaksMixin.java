package me.fallenbreath.tweakermore.mixins.tweaks.porting.handRestockFixPorting;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import fi.dy.masa.tweakeroo.tweaks.PlacementTweaks;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import me.fallenbreath.tweakermore.util.ModIds;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;

@Restriction(require = {
        @Condition(value = ModIds.tweakeroo),
        @Condition(value = ModIds.minecraft, versionPredicates = "<1.21")
})
@Mixin({PlacementTweaks.class})
public class PlacementTweaksMixin {
    @WrapMethod(method = "onProcessRightClickPost")
    private static void onProcessRightClickPostDisabled(Player player, InteractionHand hand, Operation<Void> original){
        if(!TweakerMoreConfigs.HAND_RESTOCK_FIX_PORTING.getBooleanValue()) original.call(player, hand);
    }
}

package me.fallenbreath.tweakermore.mixins.tweaks.porting.handRestockFixPorting;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import me.fallenbreath.tweakermore.impl.porting.handRestockFixPorting.HandRestockUtils;
import me.fallenbreath.tweakermore.util.ModIds;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Restriction(require = {
        @Condition(value = ModIds.tweakeroo),
        @Condition(value = ModIds.minecraft, versionPredicates = "<1.21")
})
@Mixin({MultiPlayerGameMode.class})
public abstract class MixinClientPlayerInteractionManager {
    @Inject(method = "useItem",
            at = @At("TAIL"))
    private void onProcessRightClickPost(Player player,
                                         //#if MC >= 11900
                                         //#else
                                         Level level,
                                         //#endif
                                         InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir)
    {
        if (TweakerMoreConfigs.HAND_RESTOCK_FIX_PORTING.getBooleanValue() && cir.getReturnValue().consumesAction())
        {
            HandRestockUtils.onProcessRightClickPost(player, hand);
        }
    }

}

package me.fallenbreath.tweakermore.mixins.tweaks.mc_tweaks.flatDigger;

import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class MixinClientPlayerInteractionManager {
    @Inject(
            method = "startDestroyBlock",
            at = @At("HEAD"),
            cancellable = true
    )
    private void flatDigger(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (this.shouldFlatDigger(pos)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(
            method = "continueDestroyBlock",
            at = @At("HEAD"),
            cancellable = true
    )
    private void flatDigger1(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (this.shouldFlatDigger(pos)) {
            cir.setReturnValue(false);
        }
    }

    @Unique
    private boolean shouldFlatDigger(BlockPos pos) {
        Level level = Minecraft.getInstance().level;
        Player player = Minecraft.getInstance().player;

        if (TweakerMoreConfigs.FLAT_DIGGER.getBooleanValue() && level != null && player != null) {
            return !player.isShiftKeyDown() && pos.getY() < (
                    //#if MC >= 11600
                    //$$ player.blockPosition()
                    //#else
                    new BlockPos(player)
                    //#endif
            ).getY();
        }

        return false;
    }
}

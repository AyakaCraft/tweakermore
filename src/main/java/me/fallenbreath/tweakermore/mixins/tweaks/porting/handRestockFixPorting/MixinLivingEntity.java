package me.fallenbreath.tweakermore.mixins.tweaks.porting.handRestockFixPorting;

import fi.dy.masa.tweakeroo.config.FeatureToggle;
import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import me.fallenbreath.tweakermore.impl.porting.handRestockFixPorting.HandRestockUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    @Shadow public abstract InteractionHand getUsedItemHand();
    private MixinLivingEntity(EntityType<?> type, Level worldIn)
    {
        super(type, worldIn);
    }

    @Inject(method = "completeUsingItem", at = @At("RETURN"))
    private void onItemConsumed(CallbackInfo ci)
    {
        if (TweakerMoreConfigs.HAND_RESTOCK_FIX_PORTING.getBooleanValue() && FeatureToggle.TWEAK_HAND_RESTOCK.getBooleanValue())
        {
            if ((Object) this instanceof Player)
            {
                HandRestockUtils.onProcessRightClickPost((Player) (Object) this, this.getUsedItemHand());
            }
        }
    }
}

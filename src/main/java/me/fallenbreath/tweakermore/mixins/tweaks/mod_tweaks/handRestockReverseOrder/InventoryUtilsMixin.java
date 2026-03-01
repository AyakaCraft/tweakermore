package me.fallenbreath.tweakermore.mixins.tweaks.mod_tweaks.handRestockReverseOrder;

import fi.dy.masa.tweakeroo.util.InventoryUtils;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import me.fallenbreath.tweakermore.util.ModIds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Restriction(require = @Condition(ModIds.tweakeroo))
@Mixin(InventoryUtils.class)
public abstract class InventoryUtilsMixin {
    @ModifyArg(method = "restockNewStackToHand",
            at = @At(value = "INVOKE", remap = false, target = "Lfi/dy/masa/tweakeroo/util/InventoryUtils;findSlotWithItem(Lnet/minecraft/world/inventory/AbstractContainerMenu;Lnet/minecraft/world/item/ItemStack;ZZ)I"),
            index = 3)
    private static boolean reverse(boolean reverse){
        return !TweakerMoreConfigs.HAND_RESTOCK_REVERSE_ORDER.getBooleanValue() && reverse;
    }
}

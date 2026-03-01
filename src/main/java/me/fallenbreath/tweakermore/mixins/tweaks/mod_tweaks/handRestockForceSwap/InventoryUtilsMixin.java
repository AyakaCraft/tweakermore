package me.fallenbreath.tweakermore.mixins.tweaks.mod_tweaks.handRestockForceSwap;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import fi.dy.masa.tweakeroo.util.InventoryUtils;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import me.fallenbreath.tweakermore.util.ModIds;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Restriction(require = @Condition(ModIds.tweakeroo))
@Mixin(InventoryUtils.class)
public abstract class InventoryUtilsMixin {
    @WrapOperation(method = "swapItemToHand", remap = false, at = @At(value = "INVOKE", target =
            //#if MC >= 11700
            //$$ "Lfi/dy/masa/tweakeroo/util/InventoryUtils;isHotbarSlot(I)Z"
            //#else
            "Lfi/dy/masa/tweakeroo/util/InventoryUtils;isHotbarSlot(Lnet/minecraft/world/inventory/Slot;)Z"
            //#endif
    ))
    private static boolean isHotbarSlot(
            //#if MC >= 11700
            //$$ int
            //#else
            Slot
                    //#endif
                    slot, Operation<Boolean> original) {
        return !TweakerMoreConfigs.HAND_RESTOCK_FORCE_SWAP.getBooleanValue() && original.call(slot);
    }

}

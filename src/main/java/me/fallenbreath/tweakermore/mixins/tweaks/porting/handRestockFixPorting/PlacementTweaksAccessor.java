package me.fallenbreath.tweakermore.mixins.tweaks.porting.handRestockFixPorting;

import fi.dy.masa.tweakeroo.tweaks.PlacementTweaks;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.fallenbreath.tweakermore.util.ModIds;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Restriction(require = {
        @Condition(value = ModIds.tweakeroo),
        @Condition(value = ModIds.minecraft, versionPredicates = "<1.21")
})
@Mixin({PlacementTweaks.class})
public interface PlacementTweaksAccessor {
    @Invoker("tryRestockHand")
    static void invokeTryRestockHand(Player player, InteractionHand hand, ItemStack stackOriginal){
        throw new AssertionError();
    }

    @Accessor("stackBeforeUse")
    static ItemStack[] getStackBeforeUse(){
        throw new AssertionError();
    }
}

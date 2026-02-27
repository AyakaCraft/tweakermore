package me.fallenbreath.tweakermore.mixins.tweaks.mc_tweaks.englishSearch;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.fallenbreath.tweakermore.util.ModIds;
import me.fallenbreath.tweakermore.util.mixin.DummyClass;
import org.spongepowered.asm.mixin.Mixin;

@Restriction(require = @Condition(value = ModIds.minecraft, versionPredicates = ">=1.16"))
@Mixin(DummyClass.class)
public abstract class SessionSearchTreesMixin {
}

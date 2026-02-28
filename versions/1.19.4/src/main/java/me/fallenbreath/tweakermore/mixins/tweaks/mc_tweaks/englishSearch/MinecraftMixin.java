package me.fallenbreath.tweakermore.mixins.tweaks.mc_tweaks.englishSearch;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.fallenbreath.tweakermore.util.TranslatableUtils;
import me.fallenbreath.tweakermore.util.mixin.DummyClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.searchtree.FullTextSearchTree;
import net.minecraft.client.searchtree.IdSearchTree;
import net.minecraft.client.searchtree.SearchRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.stream.Stream;

//#if MC >= 12100
//$$ @Mixin(DummyClass.class)
//$$ public class MinecraftMixin {
//$$ }
//#else
@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    public <T> void populateSearchTree(SearchRegistry.Key<T> key, List<T> values){
        throw new AssertionError();
    }

    @WrapMethod(method = "createSearchTrees")
    private void createSearchTrees(Operation<Void> original){
        MinecraftAccessor self = (MinecraftAccessor) this;
        self.getSearchRegistry().register(SearchRegistry.CREATIVE_NAMES, (list) -> new FullTextSearchTree<>((itemStack) -> TranslatableUtils.getString(itemStack.getTooltipLines(null, TooltipFlag.Default.NORMAL.asCreative()).stream()), (itemStack) -> Stream.of(BuiltInRegistries.ITEM.getKey(itemStack.getItem())), list));
        self.getSearchRegistry().register(SearchRegistry.CREATIVE_TAGS, (list) -> new IdSearchTree<>((itemStack) -> itemStack.getTags().map(TagKey::location), list));
        self.getSearchRegistry().register(SearchRegistry.RECIPE_COLLECTIONS, (list) -> new FullTextSearchTree<>((recipeCollection) -> recipeCollection.getRecipes().stream().flatMap((recipe) -> TranslatableUtils.getString(recipe.getResultItem(recipeCollection.registryAccess()).getTooltipLines(null, TooltipFlag.Default.NORMAL).stream())), (recipeCollection) -> recipeCollection.getRecipes().stream().map((recipe) -> BuiltInRegistries.ITEM.getKey(recipe.getResultItem(recipeCollection.registryAccess()).getItem())), list));
        CreativeModeTabs.searchTab().setSearchTreeBuilder((list) -> {
            this.populateSearchTree(SearchRegistry.CREATIVE_NAMES, list);
            this.populateSearchTree(SearchRegistry.CREATIVE_TAGS, list);
        });
    }
}
//#endif

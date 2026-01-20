package com.dwinovo.chiikawa.data;

import com.dwinovo.chiikawa.Chiikawa;
import com.dwinovo.chiikawa.init.InitItems;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public final class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        Holder<Enchantment> fireAspect = registries.lookupOrThrow(Registries.ENCHANTMENT)
            .get(Enchantments.FIRE_ASPECT)
            .orElseThrow();
        Holder<Enchantment> knockback = registries.lookupOrThrow(Registries.ENCHANTMENT)
            .get(Enchantments.KNOCKBACK)
            .orElseThrow();
        ItemStack result = new ItemStack(InitItems.USAGI_WEAPON.get());
        result.enchant(fireAspect, 1);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
            .define('Y', Items.YELLOW_WOOL)
            .define('F', Items.FLINT)
            .define('S', Items.STICK)
            .pattern("  Y")
            .pattern("FSF")
            .pattern("Y  ")
            .unlockedBy(getHasName(Items.YELLOW_WOOL), has(Items.YELLOW_WOOL))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Chiikawa.MODID, "usagi_weapon"));

        ItemStack hachiwareResult = new ItemStack(InitItems.HACHIWARE_WEAPON.get());
        hachiwareResult.enchant(knockback, 1);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, hachiwareResult)
            .define('B', Items.BLUE_WOOL)
            .define('S', Items.STICK)
            .pattern(" B ")
            .pattern(" SB")
            .pattern("S  ")
            .unlockedBy(getHasName(Items.BLUE_WOOL), has(Items.BLUE_WOOL))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Chiikawa.MODID, "hachiware_weapon"));

        ItemStack chiikawaResult = new ItemStack(InitItems.CHIIKAWA_WEAPON.get());
        chiikawaResult.enchant(knockback, 1);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, chiikawaResult)
            .define('P', Items.PINK_WOOL)
            .define('S', Items.STICK)
            .pattern(" P ")
            .pattern(" SP")
            .pattern("S  ")
            .unlockedBy(getHasName(Items.PINK_WOOL), has(Items.PINK_WOOL))
            .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Chiikawa.MODID, "chiikawa_weapon"));
    }
}

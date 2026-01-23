package com.dwinovo.chiikawa.data;

import com.dwinovo.chiikawa.Constants;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public final class ModItemModelProvider extends ItemModelProvider {
    private static final String[] SPAWN_EGGS = {
        "usagi_spawn_egg",
        "hachiware_spawn_egg",
        "chiikawa_spawn_egg",
        "shisa_spawn_egg",
        "momonga_spawn_egg",
        "kurimanju_spawn_egg",
        "rakko_spawn_egg"
    };

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (String name : SPAWN_EGGS) {
            withExistingParent(
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name).toString(),
                mcLoc("item/template_spawn_egg")
            );
        }

    }
}

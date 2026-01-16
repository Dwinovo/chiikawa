package com.dwinovo.popularbiology.data;

import java.util.concurrent.CompletableFuture;

import com.dwinovo.popularbiology.PopularBiology;
import com.dwinovo.popularbiology.init.InitTag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockTagsProvider extends BlockTagsProvider{
    
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,ExistingFileHelper existingFileHelper)
    {
        super(output, lookupProvider, PopularBiology.MODID, existingFileHelper);
    }
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(InitTag.ENTITY_HARVEST_CROPS)
            .add(Blocks.WHEAT, Blocks.POTATOES, Blocks.CARROTS, Blocks.BEETROOTS, Blocks.PUMPKIN, Blocks.MELON);
        tag(InitTag.ENTITY_DELEVER_CONTAINER)
            .add(Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.BARREL, Blocks.HOPPER);
    }
}
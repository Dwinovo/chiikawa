package com.dwinovo.popularbiology.data;

import com.dwinovo.popularbiology.PopularBiology;
import com.dwinovo.popularbiology.init.InitSounds;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public final class ModSoundDefinitionsProvider extends SoundDefinitionsProvider {
    public ModSoundDefinitionsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PopularBiology.MODID, existingFileHelper);
    }

    @Override
    public void registerSounds() {
        for (InitSounds.SoundEntry entry : InitSounds.entries()) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, entry.path());
            add(entry.holder(), SoundDefinition.definition().with(sound(id)));
        }
    }
}

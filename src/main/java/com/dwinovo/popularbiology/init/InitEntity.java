package com.dwinovo.popularbiology.init;

import com.dwinovo.popularbiology.PopularBiology;
import com.dwinovo.popularbiology.entity.impl.UsagiPet;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class InitEntity {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, PopularBiology.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<UsagiPet>> USAGI_PET = ENTITY_TYPES.register("usagi",
            () -> EntityType.Builder.of(UsagiPet::new, MobCategory.CREATURE)
                    .sized(0.6F, 0.8F)
                    .build(ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, "usagi").toString()));

   
    public static void register(IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(USAGI_PET.get(), UsagiPet.createAttributes().build());
    }
}

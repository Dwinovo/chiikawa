package com.dwinovo.popularbiology.client.model;

import com.dwinovo.popularbiology.PopularBiology;
import com.dwinovo.popularbiology.entity.impl.UsagiPet;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class UsagiModel extends GeoModel<UsagiPet> {

    @Override
    public ResourceLocation getModelResource(UsagiPet animatable) {
        return ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, "geo/usagi.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(UsagiPet animatable) {
        return ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, "textures/entities/usagi.png");
    }

    @Override
    public ResourceLocation getAnimationResource(UsagiPet animatable) {
        return ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, "animations/usagi.animation.json");
    }
    
}

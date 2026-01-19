package com.dwinovo.chiikawa.client.render;

import com.dwinovo.chiikawa.client.render.layer.PetHeldItemLayer;
import com.dwinovo.chiikawa.entity.AbstractPet;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public abstract class AbstractPetRender<T extends AbstractPet> extends GeoEntityRenderer<T> {
    protected AbstractPetRender(Context renderManager, GeoModel<T> model) {
        super(renderManager, model);
        addRenderLayer(new PetHeldItemLayer<>(this));
    }
}


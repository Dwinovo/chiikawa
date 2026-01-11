package com.dwinovo.popularbiology.client.render;

import com.dwinovo.popularbiology.entity.AbstractPet;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AbstractRender<T extends AbstractPet> extends GeoEntityRenderer<T> {

    public AbstractRender(Context renderManager, GeoModel<T> model) {
        super(renderManager, model);
        
    }
   
    

}

package com.dwinovo.popularbiology.client.render.impl;

import com.dwinovo.popularbiology.client.model.impl.RakkoModel;
import com.dwinovo.popularbiology.client.render.AbstractPetRender;
import com.dwinovo.popularbiology.entity.impl.RakkoPet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class RakkoRender extends AbstractPetRender<RakkoPet> {

    public RakkoRender(Context renderManager) {
        super(renderManager, new RakkoModel());
    }
}

package com.dwinovo.popularbiology.client.render.impl;

import com.dwinovo.popularbiology.client.model.impl.ShisaModel;
import com.dwinovo.popularbiology.client.render.AbstractPetRender;
import com.dwinovo.popularbiology.entity.impl.ShisaPet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class ShisaRender extends AbstractPetRender<ShisaPet> {

    public ShisaRender(Context renderManager) {
        super(renderManager, new ShisaModel());
    }
}

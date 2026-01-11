package com.dwinovo.popularbiology.client.render.impl;

import com.dwinovo.popularbiology.client.model.impl.HachiwareModel;
import com.dwinovo.popularbiology.client.render.AbstractPetRender;
import com.dwinovo.popularbiology.entity.impl.HachiwarePet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class HachiwareRender extends AbstractPetRender<HachiwarePet> {

    public HachiwareRender(Context renderManager) {
        super(renderManager, new HachiwareModel());
    }
}

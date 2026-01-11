package com.dwinovo.popularbiology.client.render.impl;

import com.dwinovo.popularbiology.client.model.impl.KurimanjuModel;
import com.dwinovo.popularbiology.client.render.AbstractPetRender;
import com.dwinovo.popularbiology.entity.impl.KurimanjuPet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class KurimanjuRender extends AbstractPetRender<KurimanjuPet> {

    public KurimanjuRender(Context renderManager) {
        super(renderManager, new KurimanjuModel());
    }
}

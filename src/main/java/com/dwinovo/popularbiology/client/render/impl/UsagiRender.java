package com.dwinovo.popularbiology.client.render.impl;

import com.dwinovo.popularbiology.client.model.impl.UsagiModel;
import com.dwinovo.popularbiology.client.render.AbstractPetRender;
import com.dwinovo.popularbiology.entity.impl.UsagiPet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class UsagiRender extends AbstractPetRender<UsagiPet> {

    public UsagiRender(Context renderManager) {
        super(renderManager, new UsagiModel());
    }
}

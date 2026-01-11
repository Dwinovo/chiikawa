package com.dwinovo.popularbiology.client.render.impl;

import com.dwinovo.popularbiology.client.model.UsagiModel;
import com.dwinovo.popularbiology.client.render.AbstractRender;
import com.dwinovo.popularbiology.entity.impl.UsagiPet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;


public class UsagiRender extends AbstractRender<UsagiPet>{

    public UsagiRender(Context renderManager) {
        super(renderManager, new UsagiModel());
    }
    
}

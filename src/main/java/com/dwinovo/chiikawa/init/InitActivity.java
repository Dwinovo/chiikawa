package com.dwinovo.chiikawa.init;

import com.dwinovo.chiikawa.Chiikawa;

import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.world.entity.schedule.Activity;

public final class InitActivity {
    public static final DeferredRegister<Activity> ACTIVITY_TYPES =
        DeferredRegister.create(Registries.ACTIVITY, Chiikawa.MODID);

    //注册FARM_HARVEST活动
    public static final DeferredHolder<Activity, Activity> FARMER_HARVEST =
        ACTIVITY_TYPES.register("farmer_harvest", () -> new Activity("farmer_harvest"));
    //注册FARM_PLANT活动
    public static final DeferredHolder<Activity, Activity> FARMER_PLANT =
        ACTIVITY_TYPES.register("farmer_plant", () -> new Activity("farmer_plant"));
    //注册DELEVER活动
    public static final DeferredHolder<Activity, Activity> DELEVER =
        ACTIVITY_TYPES.register("delever", () -> new Activity("delever"));

    private InitActivity() {
    }

    //注册Activity
    public static void register(IEventBus eventBus) {
        ACTIVITY_TYPES.register(eventBus);
    }
}



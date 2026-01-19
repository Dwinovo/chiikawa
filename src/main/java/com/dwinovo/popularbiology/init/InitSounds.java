package com.dwinovo.popularbiology.init;

import java.util.ArrayList;
import java.util.List;

import com.dwinovo.popularbiology.PopularBiology;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class InitSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, PopularBiology.MODID);
    private static final List<SoundEntry> ENTRIES = new ArrayList<>();

    public static final DeferredHolder<SoundEvent, SoundEvent> CHIIKAWA_YADAN = register("chiikawa/yadan");
    public static final DeferredHolder<SoundEvent, SoundEvent> CHIIKAWA_YANGPAPA = register("chiikawa/yangpapa");

    public static final DeferredHolder<SoundEvent, SoundEvent> HACHIWARE_JIANJIANWAGANAI = register("hachiware/jianjianwaganai");
    public static final DeferredHolder<SoundEvent, SoundEvent> HACHIWARE_NANINANI = register("hachiware/naninani");
    public static final DeferredHolder<SoundEvent, SoundEvent> HACHIWARE_OI = register("hachiware/oi");

    public static final DeferredHolder<SoundEvent, SoundEvent> KURIMANJU_DEATH = register("kurimanju/lizimantou_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> KURIMANJU_HURT = register("kurimanju/lizimantou_gethurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> KURIMANJU_TAME = register("kurimanju/lizimantou_tame");

    public static final DeferredHolder<SoundEvent, SoundEvent> MOMONGA_AQIGE = register("momonga/aqige");
    public static final DeferredHolder<SoundEvent, SoundEvent> MOMONGA_MIDIE = register("momonga/midie");
    public static final DeferredHolder<SoundEvent, SoundEvent> MOMONGA_YIYA = register("momonga/yiya");

    public static final DeferredHolder<SoundEvent, SoundEvent> RAKKO_TAME = register("rakko/tame_sound");

    public static final DeferredHolder<SoundEvent, SoundEvent> SHISA_DEATH = register("shisa/shisa_death_sound");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHISA_HURT = register("shisa/shisa_gethurt_sound");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHISA_TAME = register("shisa/shisa_tame_sound");

    public static final DeferredHolder<SoundEvent, SoundEvent> USAGI_HA1 = register("usagi/ha1");
    public static final DeferredHolder<SoundEvent, SoundEvent> USAGI_HA2 = register("usagi/ha2");
    public static final DeferredHolder<SoundEvent, SoundEvent> USAGI_WAOOO = register("usagi/waooo");
    public static final DeferredHolder<SoundEvent, SoundEvent> USAGI_WULA1 = register("usagi/wula1");
    public static final DeferredHolder<SoundEvent, SoundEvent> USAGI_ATTACK = register("usagi/wusaqi_attack_sound");
    public static final DeferredHolder<SoundEvent, SoundEvent> USAGI_BOOT_1 = register("usagi/wusaqi_boot_1");
    public static final DeferredHolder<SoundEvent, SoundEvent> USAGI_BOOT_2 = register("usagi/wusaqi_boot_2");
    public static final DeferredHolder<SoundEvent, SoundEvent> USAGI_BOOT_3 = register("usagi/wusaqi_boot_3");
    public static final DeferredHolder<SoundEvent, SoundEvent> USAGI_BOOT_4 = register("usagi/wusaqi_boot_4");
    public static final DeferredHolder<SoundEvent, SoundEvent> USAGI_HENG = register("usagi/wusaqi_heng");

    private InitSounds() {
    }

    public static void register(IEventBus modEventBus) {
        SOUND_EVENTS.register(modEventBus);
    }

    public static List<SoundEntry> entries() {
        return List.copyOf(ENTRIES);
    }

    private static DeferredHolder<SoundEvent, SoundEvent> register(String path) {
        DeferredHolder<SoundEvent, SoundEvent> holder = SOUND_EVENTS.register(path,
                () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, path)));
        ENTRIES.add(new SoundEntry(path, holder));
        return holder;
    }

    public record SoundEntry(String path, DeferredHolder<SoundEvent, SoundEvent> holder) {
    }
}

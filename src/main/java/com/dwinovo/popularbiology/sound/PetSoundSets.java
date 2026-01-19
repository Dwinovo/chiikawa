package com.dwinovo.popularbiology.sound;

import java.util.List;

import com.dwinovo.popularbiology.init.InitSounds;

public final class PetSoundSets {
    public static final PetSoundSet CHIIKAWA = new PetSoundSet(
            null,
            null,
            null,
            null,
            List.of(
                    InitSounds.CHIIKAWA_YADAN,
                    InitSounds.CHIIKAWA_YANGPAPA
            )
    );

    public static final PetSoundSet HACHIWARE = new PetSoundSet(
            null,
            null,
            null,
            null,
            List.of(
                    InitSounds.HACHIWARE_JIANJIANWAGANAI,
                    InitSounds.HACHIWARE_NANINANI,
                    InitSounds.HACHIWARE_OI
            )
    );

    public static final PetSoundSet KURIMANJU = new PetSoundSet(
            null,
            InitSounds.KURIMANJU_HURT,
            InitSounds.KURIMANJU_DEATH,
            InitSounds.KURIMANJU_TAME,
            List.of()
    );

    public static final PetSoundSet MOMONGA = new PetSoundSet(
            null,
            null,
            null,
            null,
            List.of(
                    InitSounds.MOMONGA_AQIGE,
                    InitSounds.MOMONGA_MIDIE,
                    InitSounds.MOMONGA_YIYA
            )
    );

    public static final PetSoundSet RAKKO = new PetSoundSet(
            null,
            null,
            null,
            InitSounds.RAKKO_TAME,
            List.of()
    );

    public static final PetSoundSet SHISA = new PetSoundSet(
            null,
            InitSounds.SHISA_HURT,
            InitSounds.SHISA_DEATH,
            InitSounds.SHISA_TAME,
            List.of()
    );

    public static final PetSoundSet USAGI = new PetSoundSet(
            InitSounds.USAGI_ATTACK,
            null,
            null,
            null,
            List.of(
                    InitSounds.USAGI_HA1,
                    InitSounds.USAGI_HA2,
                    InitSounds.USAGI_WAOOO,
                    InitSounds.USAGI_WULA1,
                    InitSounds.USAGI_BOOT_1,
                    InitSounds.USAGI_BOOT_2,
                    InitSounds.USAGI_BOOT_3,
                    InitSounds.USAGI_BOOT_4,
                    InitSounds.USAGI_HENG
            )
    );

    private PetSoundSets() {
    }
}

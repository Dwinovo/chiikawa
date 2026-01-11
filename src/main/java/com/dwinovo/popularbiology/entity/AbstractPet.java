package com.dwinovo.popularbiology.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AbstractPet extends TamableAnimal implements GeoEntity{
    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);

    protected AbstractPet(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }
    
    @Override
    public boolean canMate(Animal other) {
        return false; // 禁止繁殖
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return null; // 保险：不会生成幼体
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<AbstractPet> main = new AnimationController<>(this, "main", 5, state -> {
            RawAnimation builder = RawAnimation.begin();
            if (state.isMoving()) {
                builder.thenLoop("run");
            }
            else {
                builder.thenLoop("idle");
            }
            state.setAndContinue(builder);
            return PlayState.CONTINUE;
        });

        AnimationController<AbstractPet> sub = new AnimationController<>(this, "sub", 1, state -> {
            return PlayState.STOP;
        });

        controllers.add(main, sub);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.animatableInstanceCache;
    }

    @Override
    public boolean isFood(ItemStack arg0) {
        return false;
    }

    
}

package com.dwinovo.popularbiology.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import com.dwinovo.popularbiology.menu.PetBackpackMenu;
import com.dwinovo.popularbiology.entity.interact.PetInteractHandler;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AbstractPet extends TamableAnimal implements GeoEntity, MenuProvider {
    public static final int BACKPACK_SIZE = 16;
    private static final EntityDataAccessor<Byte> PET_MODE = SynchedEntityData.defineId(AbstractPet.class, EntityDataSerializers.BYTE);
    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);
    private final SimpleContainer backpack = new SimpleContainer(BACKPACK_SIZE);

    protected AbstractPet(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    public SimpleContainer getBackpack() {
        return backpack;
    }

    public PetMode getPetMode() {
        return PetMode.fromId(this.entityData.get(PET_MODE));
    }

    public void setPetMode(PetMode mode) {
        this.entityData.set(PET_MODE, (byte) mode.ordinal());
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return backpack.getItem(0);
        }
        return super.getItemBySlot(slot);
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            backpack.setItem(0, stack);
            return;
        }
        super.setItemSlot(slot, stack);
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
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(PET_MODE, (byte) PetMode.FOLLOW.ordinal());
    }

    @Override
    public boolean isFood(ItemStack arg0) {
        return false;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("menu.popularbiology.pet_backpack");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new PetBackpackMenu(containerId, inventory, this);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("Backpack", ContainerHelper.saveAllItems(new CompoundTag(), backpack.getItems(), level().registryAccess()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Backpack")) {
            ContainerHelper.loadAllItems(tag.getCompound("Backpack"), backpack.getItems(), level().registryAccess());
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        InteractionResult result = PetInteractHandler.handle(this, player, hand);
        if (result != InteractionResult.PASS) {
            return result;
        }
        return super.mobInteract(player, hand);
    }
    
}

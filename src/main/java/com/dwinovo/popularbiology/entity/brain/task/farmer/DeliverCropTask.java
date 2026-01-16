package com.dwinovo.popularbiology.entity.brain.task.farmer;

import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.entity.PetMode;
import com.dwinovo.popularbiology.init.InitMemory;
import com.dwinovo.popularbiology.init.InitRegistry;
import com.dwinovo.popularbiology.init.InitTag;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 这个任务用于将作物传输到容器中
public class DeliverCropTask extends Behavior<AbstractPet> {
    // 日志
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(DeliverCropTask.class);
    // 需要记忆的模块
    private static final Map<MemoryModuleType<?>, MemoryStatus> REQUIRED_MEMORIES = ImmutableMap.of(
        InitMemory.CONTAINER_POS.get(), MemoryStatus.VALUE_PRESENT
    );

    /**
     * 这个构造函数用于初始化任务
     */
    public DeliverCropTask() {
        // 超时时间3000tick
        super(REQUIRED_MEMORIES, 3000);
    }

    /**
     * 这个函数用于检查是否可以开始任务
     * @param world: 世界
     * @param pet: 生物
     * @return: 是否可以开始任务
     */
    @SuppressWarnings("null")
    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, AbstractPet pet) {
        // 只有当处于工作状态，职业为农民，背包里面有符合InitTag的物品，没有Harvest或Plant任务，Container位置存在，才进行任务
        if (pet.getPetMode() != PetMode.WORK || pet.getPetJobId() != InitRegistry.FARMER_ID) {
            return false;
        }
        if (pet.getBrain().getMemory(InitMemory.PLANT_POS.get()).isPresent()
            || pet.getBrain().getMemory(InitMemory.HARVEST_POS.get()).isPresent()) {
            return false;
        }
        Optional<BlockPos> containerPosOpt = pet.getBrain().getMemory(InitMemory.CONTAINER_POS.get());
        if (containerPosOpt.isEmpty()) {
            return false;
        }
        BlockPos containerPos = containerPosOpt.get();
        if (!world.getBlockState(containerPos).is(InitTag.ENTITY_DELEVER_CONTAINER)) {
            return false;
        }
        if (!hasTaggedItem(pet.getBackpack(), InitTag.ENTITY_DELIVER_ITEMS)) {
            return false;
        }
        if (!canInsertContainer(world, containerPos, pet.getBackpack(), InitTag.ENTITY_DELIVER_ITEMS)) {
            return false;
        }
        return pet.distanceToSqr(Vec3.atCenterOf(containerPos)) <= 9.0D;
    }

    /**
     * 这个函数用于开始任务
     * @param world: 世界
     * @param pet: 生物
     * @param time: 时间
     */
    @SuppressWarnings("null")
    @Override
    protected void start(ServerLevel world, AbstractPet pet, long time) {
        try {
            // 获取容器位置并触发箱子打开
            Optional<BlockPos> containerPosOpt = pet.getBrain().getMemory(InitMemory.CONTAINER_POS.get());
            if (containerPosOpt.isPresent()) {
                BlockPos containerPos = containerPosOpt.get();
                BlockEntity blockEntity = world.getBlockEntity(containerPos);
                if (blockEntity instanceof ChestBlockEntity) {
                    world.playSound(null, containerPos.getX(), containerPos.getY(), containerPos.getZ(),
                        SoundEvents.CHEST_OPEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                    world.blockEvent(containerPos, world.getBlockState(containerPos).getBlock(), 1, 1);
                }
            }
        } catch (Exception e) {
            LOGGER.error("DeliverCropTask start error", e);
        }
    }

    /**
     * 这个函数用于检查是否可以继续使用任务
     * @param world: 世界
     * @param pet: 生物
     * @param time: 时间
     * @return: 是否可以继续使用任务
     */
    @SuppressWarnings("null")
    @Override
    protected boolean canStillUse(ServerLevel world, AbstractPet pet, long time) {
        if (pet.getPetMode() != PetMode.WORK || pet.getPetJobId() != InitRegistry.FARMER_ID) {
            return false;
        }
        if (pet.getBrain().getMemory(InitMemory.PLANT_POS.get()).isPresent()
            || pet.getBrain().getMemory(InitMemory.HARVEST_POS.get()).isPresent()) {
            return false;
        }
        Optional<BlockPos> containerPosOpt = pet.getBrain().getMemory(InitMemory.CONTAINER_POS.get());
        if (containerPosOpt.isEmpty()) {
            return false;
        }
        BlockPos containerPos = containerPosOpt.get();
        if (!world.getBlockState(containerPos).is(InitTag.ENTITY_DELEVER_CONTAINER)) {
            return false;
        }
        if (!hasTaggedItem(pet.getBackpack(), InitTag.ENTITY_DELIVER_ITEMS)) {
            return false;
        }
        return canInsertContainer(world, containerPos, pet.getBackpack(), InitTag.ENTITY_DELIVER_ITEMS);
    }

    /**
     * 这个函数用于每tick执行的任务
     * @param world: 世界
     * @param pet: 生物
     * @param time: 时间
     */
    @SuppressWarnings("null")
    @Override
    protected void tick(ServerLevel world, AbstractPet pet, long time) {
        // 停止导航
        pet.getNavigation().stop();
        // 获得容器位置
        Optional<BlockPos> containerPosOpt = pet.getBrain().getMemory(InitMemory.CONTAINER_POS.get());
        if (containerPosOpt.isEmpty()) {
            return;
        }
        BlockPos containerPos = containerPosOpt.get();
        // 获得容器
        BlockEntity blockEntity = world.getBlockEntity(containerPos);
        if (!(blockEntity instanceof Container container)) {
            return;
        }

        ItemStack sourceStack = findFirstTaggedItem(pet.getBackpack(), InitTag.ENTITY_DELIVER_ITEMS);
        if (sourceStack.isEmpty()) {
            return;
        }

        ItemStack remaining = sourceStack.copy();
        remaining.setCount(1);
        for (int slot = 0; slot < container.getContainerSize() && !remaining.isEmpty(); slot++) {
            ItemStack slotStack = container.getItem(slot);
            if (slotStack.isEmpty()) {
                int maxSize = Math.min(remaining.getCount(), remaining.getMaxStackSize());
                container.setItem(slot, remaining.split(maxSize));
            } else if (ItemStack.isSameItemSameComponents(slotStack, remaining)) {
                int space = slotStack.getMaxStackSize() - slotStack.getCount();
                int transfer = Math.min(remaining.getCount(), space);
                slotStack.grow(transfer);
                remaining.shrink(transfer);
                container.setItem(slot, slotStack);
            }
        }

        if (remaining.getCount() < 1) {
            sourceStack.shrink(1);
            container.setChanged();
        }
    }

    /**
     * 这个函数用于停止任务的时候调用
     * @param world: 世界
     * @param pet: 生物
     * @param time: 时间
     */
    @SuppressWarnings("null")
    @Override
    protected void stop(ServerLevel world, AbstractPet pet, long time) {
        // 调用父类方法
        super.stop(world, pet, time);
        // 安全获取容器位置 
        Optional<BlockPos> containerPosOpt = pet.getBrain().getMemory(InitMemory.CONTAINER_POS.get());
        // 如果容器位置不为空
        if (containerPosOpt.isPresent()) {
            BlockPos containerPos = containerPosOpt.get();
            BlockEntity blockEntity = world.getBlockEntity(containerPos);
            if (blockEntity instanceof ChestBlockEntity) {
                world.playSound(null, containerPos.getX(), containerPos.getY(), containerPos.getZ(),
                    SoundEvents.CHEST_CLOSE, SoundSource.BLOCKS, 1.0F, 1.0F);
                world.blockEvent(containerPos, world.getBlockState(containerPos).getBlock(), 1, 0);
            }
        }
    }

    private static boolean hasTaggedItem(Container container, TagKey<Item> tag) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty() && stack.is(tag)) {
                return true;
            }
        }
        return false;
    }

    private static ItemStack findFirstTaggedItem(Container container, TagKey<Item> tag) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty() && stack.is(tag)) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    private static boolean canInsertContainer(ServerLevel level, BlockPos pos, Container source, TagKey<Item> tag) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof Container container)) {
            return false;
        }
        for (int i = 0; i < source.getContainerSize(); i++) {
            ItemStack stack = source.getItem(i);
            if (stack.isEmpty() || !stack.is(tag)) {
                continue;
            }
            for (int slot = 0; slot < container.getContainerSize(); slot++) {
                ItemStack slotStack = container.getItem(slot);
                if (slotStack.isEmpty()) {
                    return true;
                }
                if (ItemStack.isSameItemSameComponents(slotStack, stack)
                    && slotStack.getCount() < slotStack.getMaxStackSize()) {
                    return true;
                }
            }
        }
        return false;
    }
}

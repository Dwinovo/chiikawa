package com.dwinovo.popularbiology.entity.brain.task.farmer;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.entity.PetMode;
import com.dwinovo.popularbiology.init.InitMemory;
import com.dwinovo.popularbiology.init.InitRegistry;
import com.dwinovo.popularbiology.init.InitTag;

import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
// 这个任务用于走到容器旁边
public class WalkToContainerTask extends Behavior<AbstractPet>{
    // 需要检测的记忆类型
    private static final Map<MemoryModuleType<?>, MemoryStatus> REQUIRED_MEMORIES = ImmutableMap.of(
        InitMemory.CONTAINER_POS.get(), MemoryStatus.VALUE_PRESENT
    );
    // 速度
    private final float speed;
    /**
     * 这个函数用于初始化任务
     * @param speed: 速度
     */
    public WalkToContainerTask(float speed) {
        // 初始化任务，超时时间15
        super(REQUIRED_MEMORIES, 15);
        // 设置速度
        this.speed = speed;
    }
    /**
     * 这个函数用于检查是否可以开始任务
     * @param world: 当前世界
     * @param pet: 当前生物
     * @return: 是否可以开始任务
     */
    @SuppressWarnings("null")
    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, AbstractPet pet) {
        // HarvestPos和PlantPos不存在，处于工作模式，并且背包里面符合Tag
        if (pet.getPetMode() != PetMode.WORK || pet.getPetJobId() != InitRegistry.FARMER_ID) {
            return false;
        }
        if (pet.getBrain().getMemory(InitMemory.PLANT_POS.get()).isPresent()
            || pet.getBrain().getMemory(InitMemory.HARVEST_POS.get()).isPresent()) {
            return false;
        }
        if (!pet.getBrain().getMemory(InitMemory.CONTAINER_POS.get()).isPresent()) {
            return false;
        }
        return hasTaggedItem(pet.getBackpack(), InitTag.ENTITY_DELIVER_ITEMS);
    }
    /**
     * 这个函数用于开始任务
     * @param world: 当前世界
     * @param pet: 当前生物
     * @param time: 当前时间
     */
    @SuppressWarnings("null")
    @Override
    protected void start(ServerLevel world, AbstractPet pet, long time) {
        BehaviorUtils.setWalkAndLookTargetMemories(pet, pet.getBrain().getMemory(InitMemory.CONTAINER_POS.get()).get(), speed, 3);
    }
    

    private static boolean hasTaggedItem(net.minecraft.world.Container container, net.minecraft.tags.TagKey<net.minecraft.world.item.Item> tag) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            net.minecraft.world.item.ItemStack stack = container.getItem(i);
            if (!stack.isEmpty() && stack.is(tag)) {
                return true;
            }
        }
        return false;
    }
}

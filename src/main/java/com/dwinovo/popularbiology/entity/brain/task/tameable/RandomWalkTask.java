package com.dwinovo.popularbiology.entity.brain.task.tameable;

import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.entity.PetMode;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
// 这个任务用于随机漫步
public class RandomWalkTask extends Behavior<AbstractPet> {
    // 需要检测的记忆类型
    private static final Map<MemoryModuleType<?>, MemoryStatus> REQUIRED_MEMORIES = ImmutableMap.of(
        MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT
    );

    public RandomWalkTask() {
        // 初始化任务，超时时间100tick
        super(REQUIRED_MEMORIES, 100);
    }
    /**
     * 这个函数用于检查是否可以开始任务
     * @param level: 当前世界
     * @param entity: 当前生物
     * @return: 是否可以开始任务
     */
    @SuppressWarnings("null")
    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, AbstractPet entity) {
        // 如果生物不处于坐下状态，并且Action为0，则可以开始任务
        return entity.getPetMode() != PetMode.SIT;
    }
    /**
     * 这个函数用于开始任务
     */
    @SuppressWarnings("null")
    @Override
    protected void start(ServerLevel level, AbstractPet entity, long gameTime) {
        // 设置新的目标位置
        BlockPos newTarget = getRandomTarget(entity);
        BehaviorUtils.setWalkAndLookTargetMemories(entity, newTarget, 0.6F, 0);
    }
    /**
     * 这个函数用于获取随机目标
     * @param entity: 当前生物
     * @return: 随机目标
     */
    private BlockPos getRandomTarget(AbstractPet entity) {
        // 获取生物当前的位置
        BlockPos currentPosition = entity.blockPosition();
        // 尝试生成随机位置
        for (int i = 0; i < 10; i++) {
            // 随机生成一个位置，范围在当前生物位置的±4格内
            int randomX = currentPosition.getX() + entity.getRandom().nextInt(9) - 4;
            int randomZ = currentPosition.getZ() + entity.getRandom().nextInt(9) - 4;
            // 生成一个随机位置
            BlockPos randomBlockPos = new BlockPos(randomX, currentPosition.getY(), randomZ);
            // 检查生成的位置是否可达
            if (entity.getNavigation().isStableDestination(randomBlockPos)) {
                // 返回可达的随机位置
                return randomBlockPos;
            }
        }
        // 如果没有找到可达位置，返回当前生物的位置作为BlockPos
        return currentPosition;
    }

    
}

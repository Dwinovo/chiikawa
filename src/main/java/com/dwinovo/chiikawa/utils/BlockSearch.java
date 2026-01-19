package com.dwinovo.chiikawa.utils;

import com.dwinovo.chiikawa.entity.AbstractPet;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

// 螺旋式搜索相关工具
public final class BlockSearch {
    /**这个是一个函数式接口，用于检测一个位置是否符合传入的函数
     * @param level: 世界
     * @param pos: 位置
     * @param pet: 生物
     * @return: 是否符合条件
     */
    @FunctionalInterface
    public interface BlockCheckPredicate {
        boolean test(ServerLevel level, BlockPos pos, AbstractPet pet);
    }

    private BlockSearch() {
    }

    /**
     * 这个函数用于螺旋检测一个位置是否符合某个条件
     * @param level: 世界
     * @param pet: 生物
     * @param maxRadius: 最大半径
     * @param verticalRange: 垂直范围
     * @param predicate: 条件
     * @return: 符合条件的BlockPos
     */
    public static Optional<BlockPos> spiralBlockSearch(ServerLevel level, AbstractPet pet,
                                                       int maxRadius, int verticalRange,
                                                       BlockCheckPredicate predicate) {
        // 获得中心位置
        BlockPos center = pet.blockPosition();
        // 遍历半径
        for (int radius = 0; radius <= maxRadius; radius++) {
            // 遍历象限
            for (int quadrant = 0; quadrant < 4; quadrant++) {
                // 遍历行
                for (int i = -radius; i <= radius; i++) {
                    // 遍历列
                    for (int y = -verticalRange; y <= verticalRange; y++) {
                        // 计算位置
                        BlockPos pos = calculateSpiralPos(center, radius, quadrant, i, y);
                        // 判断是否符合条件
                        if (predicate.test(level, pos, pet)) {
                            // 返回位置
                            return Optional.of(pos);
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }

    /**
     * 这个函数用于计算一个位置
     * @param center: 中心位置
     * @param radius: 半径
     * @param quadrant: 象限
     * @param i: 行
     * @param y: 列
     * @return: 位置
     */
    private static BlockPos calculateSpiralPos(BlockPos center, int radius, int quadrant, int i, int y) {
        // 根据象限计算位置
        return switch (quadrant) {
            // 第一象限 右上
            case 0 -> center.offset(radius, y, i);
            // 第二象限 左上
            case 1 -> center.offset(-radius, y, i);
            // 第三象限 左下
            case 2 -> center.offset(i, y, radius);
            // 第四象限 右下
            case 3 -> center.offset(i, y, -radius);
            // 默认返回中心位置
            default -> center;
        };
    }
}


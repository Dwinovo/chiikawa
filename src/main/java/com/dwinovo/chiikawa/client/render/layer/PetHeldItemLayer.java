package com.dwinovo.chiikawa.client.render.layer;

import com.dwinovo.chiikawa.entity.AbstractPet;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemDisplayContext;
import com.mojang.math.Axis;
import software.bernie.geckolib.cache.object.GeoBone;
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.blaze3d.vertex.PoseStack;
import software.bernie.geckolib.renderer.GeoRenderer;
// 这个类用于渲染宠物持有的物品
public class PetHeldItemLayer<T extends AbstractPet> extends BlockAndItemGeoLayer<T> {
    private static final String RIGHT_HAND_BONE = "RightHandLocator";
    // 这个构造函数用于初始化宠物持有的物品
    public PetHeldItemLayer(GeoRenderer<T> renderer) {
        super(renderer);
    }
    /**
     * 这个函数用于获取宠物持有的物品
     * @param bone: 骨骼
     * @param animatable: 动画
     * @return: 宠物持有的物品
     */
    @Override
    public ItemStack getStackForBone(GeoBone bone, T animatable) {
        //当Geckolib遍历到RightHandLocator时，返回mainHandItem绑定
        return RIGHT_HAND_BONE.equals(bone.getName()) ? animatable.getMainHandItem() : ItemStack.EMPTY;
    }
    /**
     * 这个函数用于获取宠物持有的物品的显示类型
     * @param bone: 骨骼
     * @param stack: 物品
     * @param animatable: 动画
     * @return: 宠物持有的物品的显示类型
     */
    @Override
    protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, T animatable) {
        //当Geckolib遍历到RightHandLocator时，返回THIRD_PERSON_RIGHT_HAND，第三人称渲染
        return RIGHT_HAND_BONE.equals(bone.getName()) ? 
            ItemDisplayContext.THIRD_PERSON_RIGHT_HAND : 
            ItemDisplayContext.NONE;
    }
    /**
     * 这个函数用于渲染宠物持有的物品
     * @param poseStack: 渲染堆栈
     * @param bone: 骨骼
     * @param stack: 物品
     * @param animatable: 动画
     */
    @Override
    protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, T animatable,
                                     MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
        //设置缩放
        poseStack.scale(0.80f, 0.80f, 0.80f);
        if (stack.getItem() instanceof SwordItem || stack.getItem() instanceof HoeItem) {
            //旋转90度
            poseStack.mulPose(Axis.XP.rotationDegrees(-90f));
        }
        if (stack.getItem() instanceof BowItem) {
            //位置变化,向z轴偏移-1
            poseStack.translate(
                0.10F,  
                -0.20F, 
                -0.10F
            );
            //绕x轴转90度
            poseStack.mulPose(Axis.XP.rotationDegrees(-90f));
        }
        //渲染物品
        super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
    }
}


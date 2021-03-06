package net.rudahee.metallics_arts.modules.powers.helpers;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BendalloyAndCadmiunHelpers {

    public static void BendalloyEffects(PlayerEntity player, World world, AxisAlignedBB axisAlignedBB, BlockPos negative, BlockPos positive) {
        world.getEntitiesOfClass(LivingEntity.class, axisAlignedBB).forEach(entity -> {
            entity.aiStep();
        });

        BlockPos.betweenClosedStream(negative, positive).forEach(blockPos -> {
            BlockState block = world.getBlockState(blockPos);
            TileEntity tileEntity = world.getBlockEntity(blockPos);

            for (int i = 0; i < 12 * 4 / (tileEntity == null ? 10 : 1); i++) {
                if (tileEntity instanceof ITickableTileEntity) {
                    if (Math.random() > 0.70) {
                        ((ITickableTileEntity) tileEntity).tick();
                    }
                } else if (block.isRandomlyTicking()) {
                    if (Math.random() > 0.70) {
                        block.randomTick((ServerWorld) world, blockPos, world.random);

                    }
                }
            }
        });
    }

    public static void BendalloyEffectsEnhanced(PlayerEntity player, World world, AxisAlignedBB axisAlignedBB, BlockPos negative, BlockPos positive) {
        world.getEntitiesOfClass(LivingEntity.class, axisAlignedBB).forEach(entity -> {
            entity.aiStep();
            entity.aiStep();
            entity.aiStep();
            entity.aiStep();
        });

        BlockPos.betweenClosedStream(negative, positive).forEach(blockPos -> {
            BlockState block = world.getBlockState(blockPos);
            TileEntity tileEntity = world.getBlockEntity(blockPos);

            for (int i = 0; i < 12 * 4 / (tileEntity == null ? 10 : 1); i++) {
                if (tileEntity instanceof ITickableTileEntity) {
                    if (Math.random() > 0.20) {
                        ((ITickableTileEntity) tileEntity).tick();
                        ((ITickableTileEntity) tileEntity).tick();
                        ((ITickableTileEntity) tileEntity).tick();
                    }
                } else if (block.isRandomlyTicking()) {
                    if (Math.random() > 0.20) {
                        block.randomTick((ServerWorld) world, blockPos, world.random);
                        block.randomTick((ServerWorld) world, blockPos, world.random);
                        block.randomTick((ServerWorld) world, blockPos, world.random);
                    }
                }
            }
        });
    }



    public static void CadmiumEffectSelfPlayer(PlayerEntity player) {
        player.addEffect(new EffectInstance(Effects.SLOW_FALLING, 10, 4, true, false));
    }

    public static void CadmiumEffectSelfPlayerEnhanced(PlayerEntity player) {
        player.addEffect(new EffectInstance(Effects.SLOW_FALLING, 20, 100, true, false));
    }

    public static void CadmiumEffectsOtherPlayers(LivingEntity player, int duration, int amplifier) {

        if (player instanceof PlayerEntity) {
            player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, duration, amplifier, true, false));
        }
        player.addEffect(new EffectInstance(Effects.SLOW_FALLING, duration, amplifier, true, false));
    }

    public static void CadmiumEffectsOtherPlayersEnhanced(LivingEntity player, int duration, int amplifier) {

        player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, duration, amplifier, true, false));
        player.addEffect(new EffectInstance(Effects.SLOW_FALLING, duration, amplifier, true, false));
    }

    public static void AddAiSteeps(PlayerEntity player) {
        player.addEffect(new EffectInstance(Effects.DIG_SPEED, 3, 2, true, false));
        player.aiStep();
        player.aiStep();
    }

    public static void AddAiSteepsEnhanced(PlayerEntity player) {
        player.addEffect(new EffectInstance(Effects.DIG_SPEED, 10, 10, true, false));
        player.aiStep();
        player.aiStep();
        player.aiStep();
        player.aiStep();
        player.aiStep();
    }

    //

    public static void addFoodLevel(PlayerEntity player, int qty){

        if (player.getFoodData().getFoodLevel()<20){
            player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel()+qty);
        }

    }

    public static void removeFoodLevel(PlayerEntity player, int qty){

        if (!player.isCreative()){
            if (player.getFoodData().getFoodLevel()>0){
                player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel()-qty);
            }
        }
    }

    public static void drowningEffect(PlayerEntity player,int actualtick) {
        if (!player.isCreative()){
            if (!player.isEyeInFluid(FluidTags.WATER)) {
                if (player.getAirSupply()<=-10) {
                    player.setAirSupply(-10);
                    if (actualtick % 10 == 0) {
                        player.hurt(DamageSource.DROWN,1);
                    }
                } else {
                    player.setAirSupply(player.getAirSupply()-6);
                }
            } else {
                if (player.getAirSupply() <= 0) {
                    player.setAirSupply(0);
                }else {
                    player.setAirSupply(player.getAirSupply()-1);
                }
            }
        }
    }

    public static void throwBreathEffect(PlayerEntity player, int effectLevel) {

        if (!player.isEyeInFluid(FluidTags.WATER) && !player.isEyeInFluid(FluidTags.LAVA)) {
            player.hurt(DamageSource.DROWN, 2);
        }

        if (player.isEyeInFluid(FluidTags.WATER)) {
            player.addEffect(new EffectInstance(Effects.WATER_BREATHING, 40, effectLevel, true, false));
        }
    }
}

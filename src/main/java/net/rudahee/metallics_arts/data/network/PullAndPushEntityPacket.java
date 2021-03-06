package net.rudahee.metallics_arts.data.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.rudahee.metallics_arts.modules.powers.helpers.IronAndSteelHelpers;

import java.util.function.Supplier;

public class PullAndPushEntityPacket {
    private final int entityIDOther;
    private final int direction;

    /**
     * Send a request to the server to use iron or steel on an entity
     *
     * @param entityIDOther the entity you are requesting the data of
     * @param direction     the direction (1 for push, -1 for pull)
     */
    public PullAndPushEntityPacket(int entityIDOther, int direction) {
        this.entityIDOther = entityIDOther;
        this.direction = direction;

    }

    public static PullAndPushEntityPacket decode(PacketBuffer buf) {
        return new PullAndPushEntityPacket(buf.readInt(), buf.readInt());
    }

    public void encode(PacketBuffer buf) {
        buf.writeInt(this.entityIDOther);
        buf.writeInt(this.direction);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            Entity target = player.level.getEntity(this.entityIDOther);
            if (target != null) {

                if (IronAndSteelHelpers.isEntityMetal(target)){
                    // The player moves
                    if (target instanceof IronGolemEntity || target instanceof ItemFrameEntity) {
                        IronAndSteelHelpers.move(this.direction, player, target.blockPosition());

                    } else if (target instanceof ItemEntity || target instanceof FallingBlockEntity || target instanceof ArmorStandEntity ||
                            (target instanceof AbstractMinecartEntity && !target.isVehicle())) {
                        IronAndSteelHelpers.move(this.direction / 2.0, target, player.blockPosition());

                        // Split the difference
                    } else if (!(target instanceof ProjectileItemEntity)) {
                        IronAndSteelHelpers.move(this.direction / 2.0, target, player.blockPosition());

                        IronAndSteelHelpers.move(this.direction / 2.0, player, target.blockPosition());
                    }
                }
            }

        });
        ctx.get().setPacketHandled(true);
    }
}

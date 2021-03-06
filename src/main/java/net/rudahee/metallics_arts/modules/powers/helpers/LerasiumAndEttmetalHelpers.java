package net.rudahee.metallics_arts.modules.powers.helpers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.rudahee.metallics_arts.modules.data_player.InvestedCapability;
import net.rudahee.metallics_arts.setup.enums.extras.MetalsNBTData;

public class LerasiumAndEttmetalHelpers {

    public static void ettmetalExplotion(World world, PlayerEntity player) {
        player.getCapability(InvestedCapability.PLAYER_CAP).ifPresent(playerCapability ->{
            if (playerCapability.isBurning(MetalsNBTData.DURALUMIN)) {
                world.explode(player,player.position().x,player.position().y,player.position().z,((playerCapability.getAllomanticAmount(MetalsNBTData.ETTMETAL)+1)/1600),Explosion.Mode.BREAK);
                //player.setHealth(player.getHealth()-(((playerCapability.getAllomanticAmount(MetalsNBTData.ETTMETAL)+1)/1600)*2));
            }else {
                world.explode(player,player.position().x,player.position().y,player.position().z,((playerCapability.getAllomanticAmount(MetalsNBTData.ETTMETAL)+1)/1600),Explosion.Mode.NONE);
                //player.setHealth(player.getHealth()-(((playerCapability.getAllomanticAmount(MetalsNBTData.ETTMETAL)+1)/1600)*2));
            }
            playerCapability.drainMetals(MetalsNBTData.ETTMETAL);
        });
    }
}

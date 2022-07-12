package net.rudahee.metallics_arts.modules.items.metalminds.rings;

import net.minecraft.nbt.CompoundNBT;
import net.rudahee.metallics_arts.MetallicsArts;
import net.rudahee.metallics_arts.setup.enums.extras.MetalsNBTData;

public class RingElectrumGold extends RingsMindAbstract{
    public RingElectrumGold (Properties properties){
        super(properties, MetalsNBTData.GOLD, MetalsNBTData.ELECTRUM,MetalsNBTData.GOLD.getMaxReserveRing(),MetalsNBTData.ELECTRUM.getMaxReserveRing());
    }
}
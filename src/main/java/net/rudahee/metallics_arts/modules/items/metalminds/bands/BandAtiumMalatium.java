package net.rudahee.metallics_arts.modules.items.metalminds.bands;

import net.minecraft.item.Item;
import net.rudahee.metallics_arts.setup.enums.extras.MetalsNBTData;

public class BandAtiumMalatium extends BandMindAbstract {

    public BandAtiumMalatium (Item.Properties properties){
        super(properties, MetalsNBTData.ATIUM,MetalsNBTData.MALATIUM,MetalsNBTData.ATIUM.getMaxReserveBand(),MetalsNBTData.MALATIUM.getMaxReserveBand());

    }




}
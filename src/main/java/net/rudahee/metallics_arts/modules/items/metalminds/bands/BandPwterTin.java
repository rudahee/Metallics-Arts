package net.rudahee.metallics_arts.modules.items.metalminds.bands;

import net.minecraft.item.Item;
import net.rudahee.metallics_arts.setup.enums.extras.MetalsNBTData;

public class BandPwterTin extends BandMindAbstract {

    public BandPwterTin (Item.Properties properties){
        super(properties, MetalsNBTData.TIN,MetalsNBTData.PEWTER,MetalsNBTData.TIN.getMaxReserveBand(),MetalsNBTData.PEWTER.getMaxReserveBand());
    }

}

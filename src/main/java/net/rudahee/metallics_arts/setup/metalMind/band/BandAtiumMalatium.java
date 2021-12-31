package net.rudahee.metallics_arts.setup.metalMind.band;

import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.rudahee.metallics_arts.MetallicsArts;

public class BandAtiumMalatium extends BandMindAbstract {
    CompoundNBT nbt = new CompoundNBT();
    public BandAtiumMalatium (Item.Properties properties){
        super(properties);
        nbt.putInt(MetallicsArts.MOD_ID+".BandAtiumMalatium.atium",0);
        nbt.putInt(MetallicsArts.MOD_ID+".BandAtiumMalatium.malatium",0);
        nbt.putInt(MetallicsArts.MOD_ID+".BandAtiumMalatium.capacityAtium",100);
        nbt.putInt(MetallicsArts.MOD_ID+".BandAtiumMalatium.capacityMalatium",100);
        setNbt(nbt);
    }
}
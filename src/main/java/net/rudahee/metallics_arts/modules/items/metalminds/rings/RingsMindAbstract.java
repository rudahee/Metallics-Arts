package net.rudahee.metallics_arts.modules.items.metalminds.rings;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.rudahee.metallics_arts.modules.data_player.IDefaultInvestedPlayerData;
import net.rudahee.metallics_arts.modules.data_player.InvestedCapability;
import net.rudahee.metallics_arts.setup.enums.extras.MetalsNBTData;
import net.rudahee.metallics_arts.setup.network.ModNetwork;
import net.rudahee.metallics_arts.setup.registries.ModItems;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;


public abstract class RingsMindAbstract extends Item implements ICurioItem {


    private MetalsNBTData[] metals = new MetalsNBTData[2];
    private int[] metalsMaxReserve = new int[2];
    public String unkeyedString = "Nobody";

    public RingsMindAbstract(Properties properties,MetalsNBTData metal1, MetalsNBTData metal2,int maxReserve1,int maxReserve2) {
            super(properties);
            metals[0]=metal1;
            metals[1]=metal2;
            metalsMaxReserve[0]=maxReserve1;
            metalsMaxReserve[1]=maxReserve2;
        }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        PlayerEntity player = (PlayerEntity) slotContext.getWearer();

        player.getCapability(InvestedCapability.PLAYER_CAP).ifPresent(data ->{
            data.setMetalMindEquiped(this.metals[0].getGroup(),true);
            data.setMetalMindEquiped(this.metals[1].getGroup(),true);
            ModNetwork.sync(data,player);
        });
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        PlayerEntity player = (PlayerEntity) slotContext.getWearer();

        if (stack.getItem() != newStack.getItem()){
            player.getCapability(InvestedCapability.PLAYER_CAP).ifPresent(data ->{
                data.setMetalMindEquiped(this.metals[0].getGroup(),false);
                data.setMetalMindEquiped(this.metals[1].getGroup(),false);
                data.setStoring(this.metals[0],false);
                data.setStoring(this.metals[1],false);
                data.setDecanting(this.metals[0],false);
                data.setDecanting(this.metals[1],false);
                ModNetwork.sync(data,player);
            });
        }
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    private static IDefaultInvestedPlayerData cap = null;

    @Override
    public boolean canEquip(String identifier, LivingEntity livingEntity, ItemStack stack) {
        PlayerEntity player = (PlayerEntity) livingEntity;
        player.getCapability(InvestedCapability.PLAYER_CAP).ifPresent(data ->{
            cap = data;
        });
        if (cap != null){
            return (!(cap.getMetalMindEquiped(this.metals[0].getGroup()) && cap.getMetalMindEquiped(this.metals[1].getGroup())));
        }
        return false;
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        ItemStack resultItem;

        switch (this.metals[0].getGroup()){
            case 0:
                resultItem = new ItemStack(ModItems.RING_STEEL_IRON.get(),1);
                break;
            case 1:
                resultItem = new ItemStack(ModItems.RING_PEWTER_TIN.get(),1);
                break;
            case 2:
                resultItem = new ItemStack(ModItems.RING_COPPER_BRONZE.get(),1);
                break;
            case 3:
                resultItem = new ItemStack(ModItems.RING_ZINC_BRASS.get(),1);
                break;
            case 4:
                resultItem = new ItemStack(ModItems.RING_CHROMIUM_NICROSIL.get(),1);
                break;
            case 5:
                resultItem = new ItemStack(ModItems.RING_ALUMINUM_DURALUMIN.get(),1);
                break;
            case 6:
                resultItem = new ItemStack(ModItems.RING_CADMIUM_BENDALLOY.get(),1);
                break;
            case 7:
                resultItem = new ItemStack(ModItems.RING_ELECTRUM_GOLD.get(),1);
                break;
            case 8:
                resultItem = new ItemStack(ModItems.RING_ATIUM_MALATIUM.get(),1);
                break;
            case 9:
                resultItem = new ItemStack(ModItems.RING_LERASIUM_ETTMETAL.get(),1);
                break;
            default:
                resultItem = null;
        }

        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt(this.metals[0].getNameLower()+"_feruchemic_reserve",0);
        nbt.putInt(this.metals[1].getNameLower()+"_feruchemic_reserve",0);
        nbt.putInt(this.metals[0].getNameLower()+"_feruchemic_max_capacity",this.metalsMaxReserve[0]);
        nbt.putInt(this.metals[1].getNameLower()+"_feruchemic_max_capacity",this.metalsMaxReserve[1]);
        nbt.putString("key",unkeyedString);
        resultItem.setTag(nbt);
        if (this.allowdedIn(group)) {
            items.add(resultItem);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> toolTips, ITooltipFlag flagIn) {
        if(!stack.hasTag()){
            stack.setTag(addRingTags());
        }
        if (stack.hasTag()){
            toolTips.add(new StringTextComponent(metals[0].getNameLower().substring(0,1).toUpperCase()+metals[0].getNameLower().substring(1)+": "+ stack.getTag().getInt(metals[0].getNameLower()+"_feruchemic_reserve") / 40 + "s"));
            toolTips.add(new StringTextComponent(metals[1].getNameLower().substring(0,1).toUpperCase()+metals[1].getNameLower().substring(1)+": "+ stack.getTag().getInt(metals[1].getNameLower()+"_feruchemic_reserve") / 40 + "s"));
            toolTips.add(new StringTextComponent("Owner: "+ (stack.getTag().getString("key"))));
        }
        super.appendHoverText(stack, world, toolTips, flagIn);
    }

    private CompoundNBT addRingTags() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt(this.metals[0].getNameLower()+"_feruchemic_reserve",0);
        nbt.putInt(this.metals[1].getNameLower()+"_feruchemic_reserve",0);
        nbt.putInt(this.metals[0].getNameLower()+"_feruchemic_max_capacity",this.metalsMaxReserve[0]);
        nbt.putInt(this.metals[1].getNameLower()+"_feruchemic_max_capacity",this.metalsMaxReserve[1]);
        nbt.putString("key",this.unkeyedString);
        return nbt;
    }

    private static boolean needUpdate = false;

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if(!stack.hasTag()){
            stack.setTag(addRingTags());
        }
        CompoundNBT nbtLocal = stack.getTag();
        if (livingEntity.level instanceof ServerWorld) {
            needUpdate = false;
            if (livingEntity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) livingEntity;
                player.getCapability(InvestedCapability.PLAYER_CAP).ifPresent(data -> {

                    if (data.isDecanting(this.metals[0])) {
                        if (stack.getTag().getInt(this.metals[0].getNameLower()+"_feruchemic_reserve")>0) {
                            nbtLocal.putInt(this.metals[0].getNameLower()+"_feruchemic_reserve",(stack.getTag().getInt(this.metals[0].getNameLower()+"_feruchemic_reserve")-1));
                            stack.setTag(nbtLocal);
                        } else {
                            data.setDecanting(this.metals[0],false);
                            stack.getTag().putString("key",changeOwner(player,stack.getTag(),true));
                        }
                        needUpdate = true;
                    } else if (data.isStoring(this.metals[0])){

                        if (stack.getTag().getInt(this.metals[0].getNameLower()+"_feruchemic_reserve") < stack.getTag().getInt(this.metals[0].getNameLower()+"_feruchemic_max_capacity")) {
                            stack.getTag().putString("key",changeOwner(player,stack.getTag(),false));
                            nbtLocal.putInt(this.metals[0].getNameLower()+"_feruchemic_reserve",(stack.getTag().getInt(this.metals[0].getNameLower()+"_feruchemic_reserve")+1));
                            stack.setTag(nbtLocal);
                        } else {
                            data.setStoring(this.metals[0],false);
                        }
                        needUpdate = true;

                    }

                    if (data.isDecanting(this.metals[1])) {
                        if (stack.getTag().getInt(this.metals[1].getNameLower()+"_feruchemic_reserve")>0) {
                            nbtLocal.putInt(this.metals[1].getNameLower()+"_feruchemic_reserve",(stack.getTag().getInt(this.metals[1].getNameLower()+"_feruchemic_reserve")-1));
                            stack.setTag(nbtLocal);
                        } else {
                            data.setDecanting(this.metals[1],false);
                            stack.getTag().putString("key",changeOwner(player,stack.getTag(),true));
                        }
                        needUpdate = true;

                    } else if (data.isStoring(this.metals[1])){
                        if (stack.getTag().getInt(this.metals[1].getNameLower()+"_feruchemic_reserve") < stack.getTag().getInt(this.metals[1].getNameLower()+"_feruchemic_max_capacity")) {
                            stack.getTag().putString("key",changeOwner(player,stack.getTag(),false));
                            nbtLocal.putInt(this.metals[1].getNameLower()+"_feruchemic_reserve",(stack.getTag().getInt(this.metals[1].getNameLower()+"_feruchemic_reserve")+1));
                            stack.setTag(nbtLocal);
                        } else {
                            data.setStoring(this.metals[1],false);
                        }
                        needUpdate = true;

                    }
                });
            }
        }
        ICurioItem.super.curioTick(identifier, index, livingEntity, stack);
    }


    private static String dato;

    public String changeOwner(PlayerEntity player, CompoundNBT compoundNBT,boolean downToZero) {

        boolean isFirstReserveZero = compoundNBT.getInt(this.metals[0].getNameLower()+"_feruchemic_reserve") == 0;
        boolean isSecondReserveZero = compoundNBT.getInt(this.metals[1].getNameLower()+"_feruchemic_reserve") == 0;
        boolean isUnkey = compoundNBT.getString("key").equals(unkeyedString);
        boolean isMetalMindOfAluminum = this.metals[0].getNameLower() == MetalsNBTData.ALUMINUM.getNameLower() || this.metals[0].getNameLower() == MetalsNBTData.ALUMINUM.getNameLower();

        dato = compoundNBT.getString("key");

        if (isMetalMindOfAluminum) {
            if (isFirstReserveZero && isSecondReserveZero && downToZero)  {
                dato =  unkeyedString;
            }else  if (isFirstReserveZero && isSecondReserveZero && !downToZero){
                dato = player.getStringUUID();
            }
        } else if (isFirstReserveZero && isSecondReserveZero && downToZero)  {
            dato =  unkeyedString;
        } else if (isFirstReserveZero && isSecondReserveZero && !downToZero) {
            player.getCapability(InvestedCapability.PLAYER_CAP).ifPresent(data -> {
                if (!data.isStoring(MetalsNBTData.ALUMINUM)) {
                    dato = player.getStringUUID();
                } else {
                    dato = unkeyedString;
                }
            });
        } else if ((!isFirstReserveZero || !isSecondReserveZero) && isUnkey) {
            player.getCapability(InvestedCapability.PLAYER_CAP).ifPresent(data -> {
                if (data.isDecanting(MetalsNBTData.ALUMINUM)) {
                    dato = player.getStringUUID();
                }
            });
        }
        return dato;
    }


    public MetalsNBTData getMetals(int pos) {
        return this.metals[pos];
    }

}


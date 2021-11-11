package net.rudahee.metallics_arts.setup;

import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.rudahee.metallics_arts.MetallicsArts;
import net.rudahee.metallics_arts.world.OreGeneration;

public class Registration {

    // In this class we register each TYPE OF OBJECT we add in our mod.

    public static final DeferredRegister<Block> BLOCKS = create(ForgeRegistries.BLOCKS);
    public static final DeferredRegister<Item> ITEMS = create(ForgeRegistries.ITEMS);

    public static final DeferredRegister <ContainerType<?>> CONTAINERS = create(ForgeRegistries.CONTAINERS);
    public static final DeferredRegister <IRecipeSerializer<?>> RECIPE_SERIALIZERS = create(ForgeRegistries.RECIPE_SERIALIZERS);
    public static final DeferredRegister <TileEntityType<?>> TILE_ENTITIES = create(ForgeRegistries.TILE_ENTITIES);


    public static void register() {
        // We inject our objects to "Minecraft Bus" to load all of this? these? i dont know?

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);

        //for Alloy Furnace
        CONTAINERS.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
        TILE_ENTITIES.register(modEventBus);

        ModItems.register();
        ModBlock.register();

        //for Alloy Furnace
        ModTileEntityTypes.register();
        ModContainerTypes.register();
        ModRecipeSerializers.register();

    }

    private static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> create (IForgeRegistry<T> registry){
        return DeferredRegister.create(registry, MetallicsArts.MOD_ID);
    }

}

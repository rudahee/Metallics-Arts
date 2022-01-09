package net.rudahee.metallics_arts.data.recipes.vials;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.rudahee.metallics_arts.setup.registries.ModItems;
import net.rudahee.metallics_arts.setup.registries.ModRecipeTypes;

import java.util.ArrayList;
import java.util.List;

public class VialItemRecipe extends SpecialRecipe {

    private ItemStack final_result = ItemStack.EMPTY;

    private static final Ingredient INGREDIENT_VIAL = Ingredient.of(ModItems.VIAL.get());

    private static final List<Ingredient> INGREDIENT_NUGGET = new ArrayList<Ingredient>() {{
        for (Item metal: ModItems.ITEM_GEMS_NUGGET.values()) {
            add(Ingredient.of(metal.getItem()));
        }
        for (Item metal: ModItems.ITEM_METAL_NUGGET.values()) {
            if (!ModItems.ITEM_METAL_INGOT.get("lead").getDescriptionId().equals(metal.getDescriptionId())
                && !ModItems.ITEM_METAL_INGOT.get("silver").getDescriptionId().equals(metal.getDescriptionId())
                && !ModItems.ITEM_METAL_INGOT.get("nickel").getDescriptionId().equals(metal.getDescriptionId())) {

                add(Ingredient.of(metal.getItem()));
            }
            add(Ingredient.of(Items.IRON_NUGGET.getItem()));
            add(Ingredient.of(Items.GOLD_NUGGET.getItem()));
        }
    }};

    public VialItemRecipe(ResourceLocation location) {
        super(location);
    }

    @Override
    public boolean matches(CraftingInventory inv, World world) {
        ItemStack result = ItemStack.EMPTY;
        ItemStack actualIngredient = null;

        for(int i = 0; i < inv.getContainerSize(); i++) {
            actualIngredient = inv.getItem(i);
            if (actualIngredient != null && !actualIngredient.isEmpty()) {
                if (INGREDIENT_VIAL.test(inv.getItem(i))) {
                    //Obtener NBT
                } else if (INGREDIENT_NUGGET.contains(Ingredient.of(actualIngredient))) {

                }
            }
        }

        return true;
    }

    @Override
    public ItemStack assemble(CraftingInventory inv) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int nose, int nose2) {
        return false;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.ALLOY_FURNACE_SERIALIZER.get();
    }



    public static class Serializer extends SpecialRecipeSerializer<VialItemRecipe> {

        public Serializer() {
            super(VialItemRecipe::new);
        }
    }
}
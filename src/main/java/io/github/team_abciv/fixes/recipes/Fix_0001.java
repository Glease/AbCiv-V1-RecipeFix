package io.github.team_abciv.fixes.recipes;

import betterwithmods.BWMBlocks;
import betterwithmods.BWMItems;
import io.github.team_abciv.fixes.Utils;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import toughasnails.api.item.TANItems;

import java.util.Map;
import java.util.Optional;

/**
 * 1. Remove railcraft vanilla steel smelting recipe.
 * 2. Change
 *
 * @author glease
 * @since 1.0
 */
@Mod(modid = "team_civ.fixes.fix_0001",
     version = "1.0.0",
     acceptedMinecraftVersions = "1.10.2",
     name = "AbCiv-Fix0001",
     dependencies = "required-after:railcraft@[10.1.2]")
public class Fix_0001 {

	public static final String TARGET_CLASS_NAME = "com.oitsjustjose.persistent_bits.block.BlockChunkLoader";
	private static Logger log = Utils.getLogger("0001");

	@Mod.EventHandler
	public void onPostInit(FMLPostInitializationEvent e) {
		FurnaceRecipes recipes = FurnaceRecipes.instance();
		Map<ItemStack, ItemStack> list = recipes.getSmeltingList();
		for (ItemStack stack : OreDictionary.getOres("nuggetIron")) {
			if (list.remove(stack) != null)
				log.info("Removed one nuggetIron furnace recipe!");
		}


		Optional<IRecipe> found = Optional.empty();
		for (IRecipe r : CraftingManager.getInstance().getRecipeList()) {
			Item output;
			ItemStack outputStack;
			if (r instanceof ShapedOreRecipe
					    && (outputStack = r.getRecipeOutput()) != null
					    && (output = outputStack.getItem()) instanceof ItemBlock
					    && ((ItemBlock) output).getBlock().getClass().getName().equals(TARGET_CLASS_NAME)) {
				found = Optional.of(r);
				break;
			}
		}
		ShapedOreRecipe recipe = (ShapedOreRecipe) found.orElseThrow(AssertionError::new);
		recipe.getInput()[7] = new ItemStack(Items.END_CRYSTAL, 1); // replace enchant table with end crystal
		log.info("Updated chunk loader recipe!");

		GameRegistry.addRecipe(new ItemStack(TANItems.lifeblood_crystal, 1),
				" U ", "UCU", " U ",
				'U', new ItemStack(BWMBlocks.URN, 1, 8),
				'C', new ItemStack(Items.END_CRYSTAL, 1));
	}
}

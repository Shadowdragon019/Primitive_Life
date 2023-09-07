package taxxon.primitive_life.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.BlockTagProvider
import net.minecraft.block.Blocks
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.BlockTags
import taxxon.primitive_life.blocks.ModBlocks
import taxxon.primitive_life.JavaBlockTags
import taxxon.primitive_life.tags.ModBlockTags
import java.util.concurrent.CompletableFuture

class BlockTagGen(output: FabricDataOutput?, registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>?): BlockTagProvider(output, registriesFuture) {

    override fun configure(arg: RegistryWrapper.WrapperLookup?) {
        for (wedge in ModBlocks.wedges) {
            getOrCreateTagBuilder(ModBlockTags.wedges).add(wedge)
            getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(wedge)
        }
        for (plank in ModBlocks.planks) {
            getOrCreateTagBuilder(ModBlockTags.planks).add(plank)
            getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(plank)
        }
        // This whole "Timber" thing is excessive, but I like it, make it a library later
        getOrCreateTagBuilder(ModBlockTags.timber_barked_logs).add(Blocks.OAK_LOG, Blocks.SPRUCE_LOG, Blocks.BIRCH_LOG, Blocks.JUNGLE_LOG, Blocks.ACACIA_LOG, Blocks.DARK_OAK_LOG, Blocks.MANGROVE_LOG, Blocks.CHERRY_LOG, Blocks.CRIMSON_STEM, Blocks.WARPED_STEM)
        getOrCreateTagBuilder(ModBlockTags.timber_barked_woods).add(Blocks.OAK_WOOD, Blocks.SPRUCE_WOOD, Blocks.BIRCH_WOOD, Blocks.JUNGLE_WOOD, Blocks.ACACIA_WOOD, Blocks.DARK_OAK_WOOD, Blocks.MANGROVE_WOOD, Blocks.CHERRY_WOOD, Blocks.CRIMSON_HYPHAE, Blocks.WARPED_HYPHAE)
        getOrCreateTagBuilder(ModBlockTags.timber_stripped_logs).add(Blocks.STRIPPED_OAK_LOG, Blocks.STRIPPED_SPRUCE_LOG, Blocks.STRIPPED_BIRCH_LOG, Blocks.STRIPPED_JUNGLE_LOG, Blocks.STRIPPED_ACACIA_LOG, Blocks.STRIPPED_DARK_OAK_LOG, Blocks.STRIPPED_MANGROVE_LOG, Blocks.STRIPPED_CHERRY_LOG, Blocks.STRIPPED_CRIMSON_STEM, Blocks.STRIPPED_WARPED_STEM)
        getOrCreateTagBuilder(ModBlockTags.timber_stripped_woods).add(Blocks.STRIPPED_OAK_WOOD, Blocks.STRIPPED_SPRUCE_WOOD, Blocks.STRIPPED_BIRCH_WOOD, Blocks.STRIPPED_JUNGLE_WOOD, Blocks.STRIPPED_ACACIA_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD, Blocks.STRIPPED_MANGROVE_WOOD, Blocks.STRIPPED_CHERRY_WOOD, Blocks.STRIPPED_CRIMSON_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE)
        getOrCreateTagBuilder(ModBlockTags.timber_barked).forceAddTag(ModBlockTags.timber_barked_logs).forceAddTag(ModBlockTags.timber_barked_woods)
        getOrCreateTagBuilder(ModBlockTags.timber_stripped).forceAddTag(ModBlockTags.timber_stripped_logs).forceAddTag(ModBlockTags.timber_stripped_woods)
        getOrCreateTagBuilder(ModBlockTags.timber_logs).forceAddTag(ModBlockTags.timber_barked_logs).forceAddTag(ModBlockTags.timber_stripped_logs)
        getOrCreateTagBuilder(ModBlockTags.timber_woods).forceAddTag(ModBlockTags.timber_barked_woods).forceAddTag(ModBlockTags.timber_stripped_woods)
        getOrCreateTagBuilder(ModBlockTags.timber).forceAddTag(ModBlockTags.timber_woods).forceAddTag(ModBlockTags.timber_logs).forceAddTag(BlockTags.LOGS)

        getOrCreateTagBuilder(JavaBlockTags.requires_stone_tool_for_drops).forceAddTag(ModBlockTags.timber).forceAddTag(ModBlockTags.wedges).forceAddTag(ModBlockTags.planks).forceAddTag(BlockTags.PLANKS).forceAddTag(BlockTags.WOODEN_SLABS).forceAddTag(BlockTags.WOODEN_STAIRS).forceAddTag(BlockTags.WOODEN_FENCES).forceAddTag(BlockTags.FENCE_GATES).forceAddTag(BlockTags.WOODEN_DOORS).forceAddTag(BlockTags.WOODEN_TRAPDOORS).forceAddTag(BlockTags.WOODEN_PRESSURE_PLATES).forceAddTag(BlockTags.WOODEN_BUTTONS).forceAddTag(BlockTags.ALL_SIGNS)
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).forceAddTag(JavaBlockTags.requires_stone_tool_for_drops)
    }
}

package taxxon.primitive_life.blocks

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.MapColor
import net.minecraft.block.enums.Instrument
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.BlockSoundGroup
import taxxon.primitive_life.ModIdentifier
import taxxon.primitive_life.PrimitiveLife

object ModBlocks {
    var wedges = mutableListOf<WedgeBlock>()
    val oak_wedge = wedge("oak", MapColor.OAK_TAN)
    val spruce_wedge = wedge("spruce", MapColor.SPRUCE_BROWN)
    val birch_wedge = wedge("birch", MapColor.PALE_YELLOW)
    val jungle_wedge = wedge("jungle", MapColor.DIRT_BROWN)
    val acacia_wedge = wedge("acacia", MapColor.ORANGE)
    val dark_oak_wedge = wedge("dark_oak", MapColor.BROWN)
    val mangrove_wedge = wedge("mangrove", MapColor.RED)
    val cherry_wedge = wedge("cherry", MapColor.TERRACOTTA_WHITE)
    val crimson_wedge = wedge("crimson", MapColor.DULL_PINK)
    val warped_wedge = wedge("warped", MapColor.DARK_AQUA)

    var planks = mutableListOf<PlankBlock>()
    val oak_plank = plank("oak", MapColor.OAK_TAN)
    val spruce_plank = plank("spruce", MapColor.SPRUCE_BROWN)
    val birch_plank = plank("birch", MapColor.PALE_YELLOW)
    val jungle_plank = plank("jungle", MapColor.DIRT_BROWN)
    val acacia_plank = plank("acacia", MapColor.ORANGE)
    val dark_oak_plank = plank("dark_oak", MapColor.BROWN)
    val mangrove_plank = plank("mangrove", MapColor.RED)
    val cherry_plank = plank("cherry", MapColor.TERRACOTTA_WHITE)
    val crimson_plank = plank("crimson", MapColor.DULL_PINK)
    val warped_plank = plank("warped", MapColor.DARK_AQUA)

    private fun block(path: String, block: Block): Block {
        block_item(path, block)
        return Registry.register(Registries.BLOCK, ModIdentifier(path), block)
    }

    private fun wedge(path: String, color: MapColor): WedgeBlock {
        return (block("${path}_wedge", WedgeBlock(path,
            FabricBlockSettings.create().mapColor(color).instrument(Instrument.BASS).strength(2.0f, 3.0f).sounds(
                BlockSoundGroup.WOOD).nonOpaque().requiresTool().also {
                if (!(path == "crimson" || path == "warped")) {
                    it.burnable()
                }
            }
        )) as WedgeBlock).also { wedges.add(it) }
    }

    private fun plank(path: String, color: MapColor): PlankBlock {
        return (block("${path}_plank", PlankBlock(path,
            FabricBlockSettings.create().mapColor(color).instrument(Instrument.BASS).strength(2.0f, 3.0f).sounds(
                BlockSoundGroup.WOOD).nonOpaque().requiresTool().also {
                if (!(path == "crimson" || path == "warped")) {
                    it.burnable()
                }
            }
        )) as PlankBlock).also { planks.add(it) }
    }

    private fun block_item(path: String, block: Block): Item {
        return Registry.register(Registries.ITEM, ModIdentifier(path), BlockItem(block, FabricItemSettings()))
    }

    fun register_blocks() {
        PrimitiveLife.logger.info("Register blocks for ${PrimitiveLife.name}")
    }
}

package taxxon.primitive_life.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.BlockStatePropertyLootCondition
import net.minecraft.loot.condition.TableBonusLootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.entry.LeafEntry
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.predicate.StatePredicate
import net.minecraft.state.property.IntProperty
import taxxon.primitive_life.blocks.ModBlocks
import taxxon.primitive_life.blocks.ModProperties

class LootGen(dataOutput: FabricDataOutput?) : FabricBlockLootTableProvider(dataOutput) {
    override fun generate() {
        stackable(ModBlocks.wedges, ModProperties.wedges)
        stackable(ModBlocks.planks, ModProperties.planks)
    }

    private fun <T: Block> stackable(blocks: MutableList<T>, property: IntProperty) {
        for (block_1 in blocks) { // I have no clue what the difference between block_1 & block_2 is
            addDrop(block_1) { block_2: Block? ->
                LootTable.builder().pool(
                    LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f)).with(
                        applyExplosionDecay(
                            block_1, ItemEntry.builder(block_2).apply(
                                2..property.values.size
                            ) { planks: Int ->
                                SetCountLootFunction.builder(
                                    ConstantLootNumberProvider.create(planks.toFloat())
                                ).conditionally(
                                    BlockStatePropertyLootCondition.builder(block_2).properties(
                                        StatePredicate.Builder.create()
                                            .exactMatch(property, planks)
                                    )
                                )
                            }
                        )
                    )
                )
            }
        }

        addDrop(Blocks.GRAVEL) { block: Block? -> dropsWithSilkTouch(
            block, addSurvivesExplosionCondition(
                block, ItemEntry.builder(Items.FLINT).conditionally(
                    TableBonusLootCondition.builder(
                        Enchantments.FORTUNE,
                        0.33333f,
                        0.55555f,
                        0.77777f,
                        1.0f
                    )
                ).alternatively(ItemEntry.builder(block))
            )
        )}
    }
}

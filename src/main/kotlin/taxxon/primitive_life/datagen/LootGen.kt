package taxxon.primitive_life.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.block.Block
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.BlockStatePropertyLootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.predicate.StatePredicate
import taxxon.primitive_life.blocks.ModBlocks
import taxxon.primitive_life.blocks.ModProperties

class LootGen(dataOutput: FabricDataOutput?) : FabricBlockLootTableProvider(dataOutput) {
    override fun generate() {
        for (wedge in ModBlocks.wedges) { // This is mostly copy-pasted, I'm too intimidated to try to make it look nicer
            addDrop(wedge) { block: Block? ->
                LootTable.builder().pool(
                        LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f)).with(
                                applyExplosionDecay(
                                        wedge, ItemEntry.builder(block).apply(
                                        listOf(2, 3, 4)
                                ) { wedges: Int ->
                                    SetCountLootFunction.builder(
                                            ConstantLootNumberProvider.create(wedges.toFloat())
                                    ).conditionally(
                                            BlockStatePropertyLootCondition.builder(block).properties(
                                                    StatePredicate.Builder.create()
                                                            .exactMatch(ModProperties.wedges, wedges)
                                            )
                                    )
                                }
                                )
                        )
                )
            }
        }
    }
}

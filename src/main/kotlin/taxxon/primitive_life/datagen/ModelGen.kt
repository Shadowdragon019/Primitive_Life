package taxxon.primitive_life.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.*
import net.minecraft.util.Identifier
import taxxon.primitive_life.ModIdentifier
import taxxon.primitive_life.ModModels
import taxxon.primitive_life.ModTextureKey
import taxxon.primitive_life.PrimitiveLife.id
import taxxon.primitive_life.blocks.ModBlocks
import taxxon.primitive_life.blocks.ModProperties

class ModelGen(generator: FabricDataOutput) : FabricModelProvider(generator) {
    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator?) {
        for (wedge in ModBlocks.wedges) {
            val log_type = if (wedge.type == "crimson" || wedge.type == "warped") "stem" else "log"
            blockStateModelGenerator?.blockStateCollector?.accept(
                    VariantsBlockStateSupplier.create(wedge).coordinate(BlockStateVariantMap.create(
                            ModProperties.wedge_count
                    ).register {
                        BlockStateVariant.create().put(
                                VariantSettings.MODEL, blockStateModelGenerator.createSubModel(wedge, "_$it", ModModels.wedges[it-1]
                        ) {
                            TextureMap.of(ModTextureKey.top, Identifier("block/stripped_${wedge.type}_${log_type}_top")).put(ModTextureKey.side, Identifier("block/stripped_${wedge.type}_$log_type"))
                        }
                        )
                    })
            )
        }
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator?) {
        for (wedge in ModBlocks.wedges) {
            itemModelGenerator?.register(wedge.asItem(), Models.GENERATED)
        }
    }
}

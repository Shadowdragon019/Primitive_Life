package taxxon.primitive_life.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.*
import net.minecraft.util.Identifier
import taxxon.primitive_life.ModIdentifier
import taxxon.primitive_life.ModModels
import taxxon.primitive_life.ModTextureKey
import taxxon.primitive_life.blocks.ModBlocks
import taxxon.primitive_life.blocks.ModProperties

class ModelGen(generator: FabricDataOutput) : FabricModelProvider(generator) {
    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator?) {
        for (wedge in ModBlocks.wedges) {
            val log_type = if (wedge.type == "crimson" || wedge.type == "warped") "stem" else "log"
            blockStateModelGenerator?.blockStateCollector?.accept(
                VariantsBlockStateSupplier.create(wedge).coordinate(BlockStateVariantMap.create(
                    ModProperties.wedges
                ).register {
                    ModModels.wedges[it-1].upload(
                        ModIdentifier("block/wedge/${wedge.type}/${it}"),
                        TextureMap.of(ModTextureKey.top, Identifier("block/stripped_${wedge.type}_${log_type}_top")).put(ModTextureKey.side, Identifier("block/stripped_${wedge.type}_$log_type")),
                        blockStateModelGenerator.modelCollector
                    )
                    BlockStateVariant.create().put(
                        VariantSettings.MODEL, ModIdentifier("block/wedge/${wedge.type}/$it")
                    )
                })
            )
        }

        for (plank in ModBlocks.planks) {
            blockStateModelGenerator?.blockStateCollector?.accept(
                VariantsBlockStateSupplier.create(plank).coordinate(BlockStateVariantMap.create(
                    ModProperties.planks
                ).register {
                    ModModels.planks[it-1].upload(
                        ModIdentifier("block/plank/${plank.type}/${it}"),
                        TextureMap.of(ModTextureKey.planks, Identifier("block/${plank.type}_planks")),
                        blockStateModelGenerator.modelCollector
                    )
                    BlockStateVariant.create().put(
                        VariantSettings.MODEL, ModIdentifier("block/plank/${plank.type}/$it")
                    )
                })
            )
        }
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator?) {
        for (wedge in ModBlocks.wedges) {
            itemModelGenerator?.register(wedge.asItem(), Models.GENERATED)
        }
        for (plank in ModBlocks.planks) {
            itemModelGenerator?.register(plank.asItem(), Models.GENERATED)
        }
    }
}

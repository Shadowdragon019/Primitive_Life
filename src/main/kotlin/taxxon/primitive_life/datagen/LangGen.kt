package taxxon.primitive_life.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import taxxon.primitive_life.blocks.ModBlocks

class LangGen(generator: FabricDataOutput): FabricLanguageProvider(generator, "en_us") {
    override fun generateTranslations(translationBuilder: TranslationBuilder?) {
        translationBuilder?.add(ModBlocks.oak_wedge, "Oak Wedge")
        translationBuilder?.add(ModBlocks.spruce_wedge, "Spruce Wedge")
        translationBuilder?.add(ModBlocks.birch_wedge, "Birch Wedge")
        translationBuilder?.add(ModBlocks.jungle_wedge, "Jungle Wedge")
        translationBuilder?.add(ModBlocks.acacia_wedge, "Acacia Wedge")
        translationBuilder?.add(ModBlocks.dark_oak_wedge, "Dark Oak Wedge")
        translationBuilder?.add(ModBlocks.mangrove_wedge, "Mangrove Wedge")
        translationBuilder?.add(ModBlocks.cherry_wedge, "Cherry Wedge")
        translationBuilder?.add(ModBlocks.crimson_wedge, "Crimson Wedge")
        translationBuilder?.add(ModBlocks.warped_wedge, "Warped Wedge")
    }
}
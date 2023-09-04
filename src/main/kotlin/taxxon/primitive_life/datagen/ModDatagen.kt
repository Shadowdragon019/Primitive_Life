package taxxon.primitive_life.datagen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object ModDatagen : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		val pack = fabricDataGenerator.createPack()
		pack.addProvider(::ModelGen)
		pack.addProvider(::LangGen)
		pack.addProvider(::BlockTagGen)
		pack.addProvider(::LootGen)
	}
}

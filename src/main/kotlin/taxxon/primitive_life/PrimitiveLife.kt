package taxxon.primitive_life

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object PrimitiveLife : ModInitializer {
	val id = "primitive_life"
    private val logger = LoggerFactory.getLogger("Primitive Life")

	override fun onInitialize() {
		logger.info("Hello from Kotlin! :3")
	}
}

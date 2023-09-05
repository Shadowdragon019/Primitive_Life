package taxxon.primitive_life.blocks

import net.minecraft.state.property.IntProperty

object ModProperties {
    val wedges: IntProperty = IntProperty.of("wedges", 1, 4)
    val planks: IntProperty = IntProperty.of("planks", 1, 32)
}

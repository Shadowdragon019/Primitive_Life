package taxxon.primitive_life

import net.minecraft.data.client.Model
import net.minecraft.data.client.TextureKey
import java.util.*

object ModModels {
    val wedges = mutableListOf<Model>().also { list ->
        repeat(4) {
            list.add(block("wedge_${it+1}", ModTextureKey.top, ModTextureKey.side))
        }
    }

    private fun make(vararg requiredTextureKeys: TextureKey): Model {
        return Model(Optional.empty(), Optional.empty(), *requiredTextureKeys)
    }

    private fun block(parent: String, vararg requiredTextureKeys: TextureKey): Model {
        return Model(
            Optional.of(
                ModIdentifier(
                    "block/$parent"
                )
            ),
            Optional.empty(), *requiredTextureKeys
        )
    }

    private fun item(parent: String, vararg requiredTextureKeys: TextureKey): Model {
        return Model(
            Optional.of(
                ModIdentifier(
                    "item/$parent"
                )
            ),
            Optional.empty(), *requiredTextureKeys
        )
    }

    private fun block(parent: String, variant: String, vararg requiredTextureKeys: TextureKey): Model {
        return Model(
            Optional.of(
                ModIdentifier(
                    "block/$parent"
                )
            ),
            Optional.of(variant), *requiredTextureKeys
        )
    }
}
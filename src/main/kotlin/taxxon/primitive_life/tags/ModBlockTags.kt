package taxxon.primitive_life.tags

import net.minecraft.block.Block
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import taxxon.primitive_life.ModIdentifier

object ModBlockTags {
    val wedges: TagKey<Block> = TagKey.of(RegistryKeys.BLOCK, ModIdentifier("wedges"))
    val timber_barked_logs: TagKey<Block> = TagKey.of(RegistryKeys.BLOCK, ModIdentifier("timber/barked/logs"))
    val timber_barked_woods: TagKey<Block> = TagKey.of(RegistryKeys.BLOCK, ModIdentifier("timber/barked/woods"))
    val timber_stripped_logs: TagKey<Block> = TagKey.of(RegistryKeys.BLOCK, ModIdentifier("timber/stripped/logs"))
    val timber_stripped_woods: TagKey<Block> = TagKey.of(RegistryKeys.BLOCK, ModIdentifier("timber/stripped/woods"))
    val timber_barked: TagKey<Block> = TagKey.of(RegistryKeys.BLOCK, ModIdentifier("timber/barked"))
    val timber_stripped: TagKey<Block> = TagKey.of(RegistryKeys.BLOCK, ModIdentifier("timber/stripped"))
    val timber_logs: TagKey<Block> = TagKey.of(RegistryKeys.BLOCK, ModIdentifier("timber/logs"))
    val timber_woods: TagKey<Block> = TagKey.of(RegistryKeys.BLOCK, ModIdentifier("timber/woods"))
    val timber: TagKey<Block> = TagKey.of(RegistryKeys.BLOCK, ModIdentifier("timber"))
}

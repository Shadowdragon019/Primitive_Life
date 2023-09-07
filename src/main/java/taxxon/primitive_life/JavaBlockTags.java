package taxxon.primitive_life;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

// This exists because Kotlin & Mixins don't play well together period
public class JavaBlockTags {
    public static final TagKey<Block> requires_stone_tool_for_drops = TagKey.of(RegistryKeys.BLOCK, new Identifier("primitive_life", "requires_stone_tool_for_drops"));
}

package taxxon.primitive_life

import com.google.common.collect.ImmutableMap
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ToolItem
import net.minecraft.registry.tag.ItemTags
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import taxxon.primitive_life.blocks.ModBlocks
import taxxon.primitive_life.blocks.ModProperties
import taxxon.primitive_life.items.ModItems
import taxxon.primitive_life.tags.ModBlockTags

object PrimitiveLife : ModInitializer {
	val id = "primitive_life"
	val name = "Primitive Life"
	val logger: Logger = LoggerFactory.getLogger(name)

	override fun onInitialize() {
		val block_to_block: Map<Block, Block> = ImmutableMap.Builder<Block, Block>()
			.put(Blocks.STRIPPED_OAK_LOG, ModBlocks.oak_wedge).put(Blocks.STRIPPED_DARK_OAK_LOG, ModBlocks.dark_oak_wedge).put(Blocks.STRIPPED_ACACIA_LOG, ModBlocks.acacia_wedge).put(Blocks.STRIPPED_CHERRY_LOG, ModBlocks.cherry_wedge).put(Blocks.STRIPPED_BIRCH_LOG, ModBlocks.birch_wedge).put(Blocks.STRIPPED_JUNGLE_LOG, ModBlocks.jungle_wedge).put(Blocks.STRIPPED_SPRUCE_LOG, ModBlocks.spruce_wedge).put(Blocks.STRIPPED_WARPED_STEM, ModBlocks.warped_wedge).put(Blocks.STRIPPED_CRIMSON_STEM, ModBlocks.crimson_wedge).put(Blocks.STRIPPED_MANGROVE_LOG, ModBlocks.mangrove_wedge)
			.put(ModBlocks.oak_wedge, ModBlocks.oak_plank).put(ModBlocks.dark_oak_wedge, ModBlocks.dark_oak_plank).put(ModBlocks.acacia_wedge, ModBlocks.acacia_plank).put(ModBlocks.cherry_wedge, ModBlocks.cherry_plank).put(ModBlocks.birch_wedge, ModBlocks.birch_plank).put(ModBlocks.jungle_wedge, ModBlocks.jungle_plank).put(ModBlocks.spruce_wedge, ModBlocks.spruce_plank).put(ModBlocks.warped_wedge, ModBlocks.warped_plank).put(ModBlocks.crimson_wedge, ModBlocks.crimson_plank).put(ModBlocks.mangrove_wedge, ModBlocks.mangrove_plank)
		.build()
		ModItems.register_items()
		ModBlocks.register_blocks()

		UseBlockCallback.EVENT.register(UseBlockCallback { player: PlayerEntity, world: World, hand: Hand, result: BlockHitResult ->

			/*logger.info(Registries.BLOCK.streamTagsAndEntries().count().toString())
			for (pair in Registries.BLOCK.streamTagsAndEntries()) {
				logger.info(pair.first.id.toString())
				for (block in pair.second) {
					logger.info(block.value().name.string)
				}
			}*/

			val pos = result.blockPos
			val state = world.getBlockState(pos)
			val block = state.block
			val item = player.mainHandStack
			var success = false
			var tool: ToolItem? = null
			if (item.item is ToolItem) {
				tool = item.item as ToolItem
			}

			if (!player.isSpectator && item.isIn(ItemTags.AXES) && tool != null && tool.material.miningLevel > 0 && block_to_block.contains(block) && hand == Hand.MAIN_HAND) {
				if (state.isIn(ModBlockTags.timber_stripped_logs) && state.get(Properties.AXIS) == Direction.Axis.Y) {
					world.setBlockState(result.blockPos, block_to_block[block]?.defaultState?.with(ModProperties.wedges, 4))
					success = true
				} else if (state.isIn(ModBlockTags.wedges)) {
					world.setBlockState(result.blockPos, block_to_block[block]?.defaultState?.with(ModProperties.planks, state.get(ModProperties.wedges) * 2))
					success = true
				}
			}

			if (success) {
				world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f)
				player.swingHand(hand)
				player.mainHandStack.damage(1, player) { p: PlayerEntity ->
					p.sendToolBreakStatus(hand)
				}
				ActionResult.SUCCESS
			}

			ActionResult.PASS
		})
	}
}

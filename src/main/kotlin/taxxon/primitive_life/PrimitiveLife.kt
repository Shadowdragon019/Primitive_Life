package taxxon.primitive_life

import com.google.common.collect.ImmutableMap
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.entity.player.PlayerEntity
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


object PrimitiveLife : ModInitializer {
	val id = "primitive_life"
	val name = "Primitive Life"
	val logger: Logger = LoggerFactory.getLogger(name)

	override fun onInitialize() {
		val stripped_to_wedge: Map<Block, Block> = ImmutableMap.Builder<Block, Block>().put(Blocks.STRIPPED_OAK_LOG, ModBlocks.oak_wedge).put(Blocks.STRIPPED_DARK_OAK_LOG, ModBlocks.dark_oak_wedge).put(Blocks.STRIPPED_ACACIA_LOG, ModBlocks.acacia_wedge).put(Blocks.STRIPPED_CHERRY_LOG, ModBlocks.cherry_wedge).put(Blocks.STRIPPED_BIRCH_LOG, ModBlocks.birch_wedge).put(Blocks.STRIPPED_JUNGLE_LOG, ModBlocks.jungle_wedge).put(Blocks.STRIPPED_SPRUCE_LOG, ModBlocks.spruce_wedge).put(Blocks.STRIPPED_WARPED_STEM, ModBlocks.warped_wedge).put(Blocks.STRIPPED_CRIMSON_STEM, ModBlocks.crimson_wedge).put(Blocks.STRIPPED_MANGROVE_LOG, ModBlocks.mangrove_wedge).build()

		ModItems.register_items()
		ModBlocks.register_blocks()

		UseBlockCallback.EVENT.register(UseBlockCallback { player: PlayerEntity, world: World, hand: Hand, result: BlockHitResult ->
			val pos = result.blockPos
			val state = world.getBlockState(pos)
			val block = state.block
			if (!player.isSpectator && player.mainHandStack.isIn(ItemTags.AXES) && stripped_to_wedge.contains(block) && state.get(Properties.AXIS) == Direction.Axis.Y && hand == Hand.MAIN_HAND) {
				world.setBlockState(result.blockPos, stripped_to_wedge[block]?.defaultState?.with(ModProperties.wedge_count, 4))
				world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f)
				player.swingHand(hand)
				player.mainHandStack.damage(1, player) {
					p: PlayerEntity -> p.sendToolBreakStatus(hand)
				}
				ActionResult.SUCCESS
			}
			ActionResult.PASS
		})
	}
}

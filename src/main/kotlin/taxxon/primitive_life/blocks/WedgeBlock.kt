package taxxon.primitive_life.blocks

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry
import net.minecraft.block.*
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.Items
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import kotlin.math.min

class WedgeBlock(_type: String, settings: Settings): Block(settings), Waterloggable {
    private val wedge_1: VoxelShape = createCuboidShape(0.0, 0.0, 0.0, 7.0, 16.0, 7.0)
    private val wedge_2: VoxelShape = createCuboidShape(9.0, 0.0, 0.0, 16.0, 16.0, 7.0)
    private val wedge_3: VoxelShape = createCuboidShape(0.0, 0.0, 9.0, 7.0, 16.0, 16.0)
    private val wedge_4: VoxelShape = createCuboidShape(9.0, 0.0, 9.0, 16.0, 16.0, 16.0)
    private val wedge_shape = listOf(
            wedge_1,
            VoxelShapes.union(wedge_1, wedge_2),
            VoxelShapes.union(wedge_1, wedge_2, wedge_3),
            VoxelShapes.union(wedge_1, wedge_2, wedge_3, wedge_4),
    )
    val type: String = _type
    val is_nether = (type == "crimson" || type == "warped")

    init {
        //type = _type
        defaultState = defaultState
                .with(ModProperties.wedges, 1)
                .with(Properties.WATERLOGGED, false)
        if (!is_nether) {
            FlammableBlockRegistry.getDefaultInstance().add(this, 5, 5)
        }
    }

    override fun getOutlineShape(state: BlockState, world: BlockView?, pos: BlockPos?, context: ShapeContext?): VoxelShape {
        return wedge_shape[state.get(ModProperties.wedges)-1]
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState {
        val blockState = ctx.world.getBlockState(ctx.blockPos)
        if (blockState.isOf(this)) {
            return blockState.with(ModProperties.wedges, min(4.0, (blockState.get(ModProperties.wedges) + 1).toDouble()).toInt())
        }
        val fluidState = ctx.world.getFluidState(ctx.blockPos)
        val bl = fluidState.fluid === Fluids.WATER
        return super.getPlacementState(ctx)!!.with(Properties.WATERLOGGED, bl)
    }

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        if (!player.isSneaking && player.mainHandStack.isOf(Items.AIR)) {
            if (state.get(ModProperties.wedges)>1) {
                world.setBlockState(pos, state.with(ModProperties.wedges, state.get(ModProperties.wedges)-1))
            } else {
                world.setBlockState(pos, Blocks.AIR.defaultState)
            }
            world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, (world.random.nextFloat() - world.random.nextFloat()) * 1.4f + 2.0f)
            player.giveItemStack(state.block.asItem().defaultStack)
            return ActionResult.SUCCESS
        }
        return ActionResult.PASS
    }

    override fun canReplace(state: BlockState, context: ItemPlacementContext): Boolean {
        return if (!context.shouldCancelInteraction() && context.stack.isOf(asItem()) && state.get(ModProperties.wedges) < 4) {
            true
        } else super.canReplace(state, context)
    }

    override fun getFluidState(state: BlockState): FluidState {
        return if (state.get(Properties.WATERLOGGED)) {
            Fluids.WATER.getStill(false)
        } else {
            super.getFluidState(state)
        }
    }

    override fun getStateForNeighborUpdate(
        state: BlockState,
        direction: Direction?,
        neighborState: BlockState?,
        world: WorldAccess,
        pos: BlockPos?,
        neighborPos: BlockPos?
    ): BlockState {
        if (!state.canPlaceAt(world, pos)) {
            return Blocks.AIR.defaultState
        }
        if (state.get(Properties.WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world))
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
    }

    override fun appendProperties(builder: StateManager.Builder<Block?, BlockState?>) {
        builder.add(ModProperties.wedges, Properties.WATERLOGGED)
    }
}

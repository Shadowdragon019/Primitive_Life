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

class PlankBlock(_type: String, settings: Settings): Block(settings), Waterloggable {
    val type: String = _type
    val is_nether = is_nether(type)
    private fun x_shape(num: Int, height: Double): VoxelShape {
        return createCuboidShape(maxOf(16.25-4*num, 0.25), height, 0.0, 15.75, height+2.0, 16.0)
    }
    private fun y_shape(num: Int, height: Double): VoxelShape {
        return createCuboidShape(0.0, height, maxOf(16.25-4*num, 0.25), 16.0, height+2.0, 15.75)
    }
    private val hitboxes = mutableListOf<VoxelShape>().also { list -> // This isn't optimal as it does this for each PlankBlock instantiated (10 times total) instead of 1, but whatever
        repeat(32) {
            val num = it
            var final: VoxelShape
            final = if ((num%8)/4 == 0) {
                x_shape(num%4+1, num/4*2.0)
            } else {
                y_shape(num%4+1, num/4*2.0)
            }
            if (num>3) {
                final = VoxelShapes.union(final, list[num/4*4-1])
            }
            list.add(final)
        }
    }

    init {
        defaultState = defaultState
            .with(ModProperties.planks, 1)
            .with(Properties.WATERLOGGED, false)
        if (!is_nether) {
            FlammableBlockRegistry.getDefaultInstance().add(this, 5, 5)
        }
    }

    fun is_nether(string: String): Boolean {
        return (string == "crimson" || string == "warped")
    }

    override fun getOutlineShape(state: BlockState?, world: BlockView?, pos: BlockPos?, context: ShapeContext?): VoxelShape {
        return hitboxes[state?.get(ModProperties.planks)!!-1]
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState {
        val blockState = ctx.world.getBlockState(ctx.blockPos)
        if (blockState.isOf(this)) {
            return blockState.with(ModProperties.planks, min(32.0, (blockState.get(ModProperties.planks) + 1).toDouble()).toInt())
        }
        val fluidState = ctx.world.getFluidState(ctx.blockPos)
        val bl = fluidState.fluid === Fluids.WATER
        return super.getPlacementState(ctx)!!.with(Properties.WATERLOGGED, bl)
    }

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        if (!player.isSneaking && player.mainHandStack.isOf(Items.AIR)) {
            if (state.get(ModProperties.planks)>1) {
                world.setBlockState(pos, state.with(ModProperties.planks, state.get(ModProperties.planks)-1))
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
        return if (!context.shouldCancelInteraction() && context.stack.isOf(asItem()) && state.get(ModProperties.planks) < 32) {
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
        builder.add(ModProperties.planks, Properties.WATERLOGGED)
    }
}

package taxxon.primitive_life.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.include.com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Optional;

// Fuck this
@Mixin(AxeItem.class)
abstract public class AxeItemMixin extends MiningToolItem {
    @Unique
    //private static final Map<Block, Block> stripped_to_wedge = new ImmutableMap.Builder<Block, Block>().put(Blocks.STRIPPED_OAK_WOOD, ModBlocks.INSTANCE.getOak_wedge()).put(Blocks.STRIPPED_OAK_LOG, ModBlocks.INSTANCE.getOak_wedge()).put(Blocks.STRIPPED_DARK_OAK_WOOD, ModBlocks.INSTANCE.getDark_oak_wedge()).put(Blocks.STRIPPED_DARK_OAK_LOG, ModBlocks.INSTANCE.getDark_oak_wedge()).put(Blocks.STRIPPED_ACACIA_WOOD, ModBlocks.INSTANCE.getAcacia_wedge()).put(Blocks.STRIPPED_ACACIA_LOG, ModBlocks.INSTANCE.getAcacia_wedge()).put(Blocks.STRIPPED_CHERRY_WOOD, ModBlocks.INSTANCE.getCherry_wedge()).put(Blocks.STRIPPED_CHERRY_LOG, ModBlocks.INSTANCE.getCherry_wedge()).put(Blocks.STRIPPED_BIRCH_WOOD, ModBlocks.INSTANCE.getBirch_wedge()).put(Blocks.STRIPPED_BIRCH_LOG, ModBlocks.INSTANCE.getBirch_wedge()).put(Blocks.STRIPPED_JUNGLE_WOOD, ModBlocks.INSTANCE.getJungle_wedge()).put(Blocks.STRIPPED_JUNGLE_LOG, ModBlocks.INSTANCE.getJungle_wedge()).put(Blocks.STRIPPED_SPRUCE_WOOD, ModBlocks.INSTANCE.getSpruce_wedge()).put(Blocks.STRIPPED_SPRUCE_LOG, ModBlocks.INSTANCE.getSpruce_wedge()).put(Blocks.STRIPPED_WARPED_STEM, ModBlocks.INSTANCE.getWarped_wedge()).put(Blocks.STRIPPED_WARPED_HYPHAE, ModBlocks.INSTANCE.getWarped_wedge()).put(Blocks.STRIPPED_CRIMSON_STEM, ModBlocks.INSTANCE.getCrimson_wedge()).put(Blocks.STRIPPED_CRIMSON_HYPHAE, ModBlocks.INSTANCE.getCrimson_wedge()).put(Blocks.STRIPPED_MANGROVE_WOOD, ModBlocks.INSTANCE.getMangrove_wedge()).put(Blocks.STRIPPED_MANGROVE_LOG, ModBlocks.INSTANCE.getMangrove_wedge()).build();
    private static final Map<Block, Block> stripped_to_wedge = new ImmutableMap.Builder<Block, Block>().put(Blocks.DIRT, Blocks.STRIPPED_BIRCH_WOOD).build();
    public AxeItemMixin(float attackDamage, float attackSpeed, ToolMaterial material, TagKey<Block> effectiveBlocks, Settings settings) {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
    }

    @Inject(method = "useOnBlock(Lnet/minecraft/item/ItemUsageContext;)Lnet/minecraft/util/ActionResult;", at = @At(value = "INVOKE", target = "Ljava/util/Optional;isPresent()Z", shift = At.Shift.BEFORE, ordinal = 0), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void useOnBlock(ItemUsageContext context,
                            CallbackInfoReturnable<ActionResult> cir,
                            World world,
                            BlockPos blockPos,
                            PlayerEntity playerEntity,
                            BlockState blockState,
                            Optional<BlockState> optional,
                            Optional<BlockState> optional2,
                            Optional<BlockState> optional3,
                            ItemStack itemStack,
                            Optional<Object> optional4) {
        Optional<Block> wedge = Optional.ofNullable(stripped_to_wedge.get(world.getBlockState(blockPos).getBlock()));

        if (wedge.isPresent()) {
            world.playSound(playerEntity, blockPos, SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
            optional4 = Optional.of(wedge.get().getDefaultState());
        }
    }
}


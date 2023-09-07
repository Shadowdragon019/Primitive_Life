package taxxon.primitive_life.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import taxxon.primitive_life.JavaBlockTags;

@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin {
    @Final
    @Shadow
    private PlayerInventory inventory;

    @Inject(method = "canHarvest", at = @At("RETURN"), cancellable = true)
    public void can_harvest_mixin(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = inventory.getMainHandStack();

        if (stack.getItem() instanceof ToolItem tool) {
            if (state.isIn(JavaBlockTags.requires_stone_tool_for_drops) && tool.getMaterial().getMiningLevel() < 1) {
                cir.setReturnValue(false);
            }
        }
    }
}

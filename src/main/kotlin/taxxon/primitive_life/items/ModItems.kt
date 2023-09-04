package taxxon.primitive_life.items

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import taxxon.primitive_life.ModIdentifier
import taxxon.primitive_life.PrimitiveLife
import taxxon.primitive_life.blocks.ModBlocks

object ModItems {
    private fun register(path: String, item: Item): Item {
        return Registry.register(Registries.ITEM, ModIdentifier(path), item)
    }

    private fun add_to_tab_entries(tab: FabricItemGroupEntries) {
        for (wedge in ModBlocks.wedges) {
            tab.add(wedge)
        }
    }

    fun register_items() {
        PrimitiveLife.logger.info("Register items for ${PrimitiveLife.name}")

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::add_to_tab_entries) // :: gets the function itself
    }
}

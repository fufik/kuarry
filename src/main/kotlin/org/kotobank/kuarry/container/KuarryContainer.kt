package org.kotobank.kuarry.container

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.*
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler
import org.kotobank.kuarry.tile_entity.KuarryTileEntity

class KuarryContainer(inventoryPlayer: InventoryPlayer, tileEntity: KuarryTileEntity) : Container() {

    companion object {
        private const val xStart = 8

        private const val inventoryYStart = 84
        private const val playerInventoryYStart = 144
        private const val playerHotbarYStart = 202

        private const val slotSize = 18
    }

    init {
        val inventory = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)

        // Add all the slots from the kuarry inventory
        for (i in 0 until tileEntity.inventoryHeight) {
            for (j in 0 until tileEntity.inventoryWidth) {
                val positionInInventory = (j * tileEntity.inventoryHeight) + i

                addSlotToContainer(
                        addSlotToContainer(
                                object : SlotItemHandler(
                                        inventory,
                                        positionInInventory,
                                        xStart + (j * slotSize),
                                        inventoryYStart + (i * slotSize)
                                ) {
                                    override fun onSlotChanged() {
                                        tileEntity.markDirty()
                                    }
                                }
                        )
                )
            }
        }

        // Player inventory size is constant, 3 x 9 + toolbar of width 9
        val playerInventoryWidth = 9
        val playerInventoryHeight = 3
        val playerHotbarSize = 9

        for (i in 0 until playerInventoryHeight) {
            for (j in 0 until playerInventoryWidth) {
                // Hotbar is at the beginning of the inventory, need to skip that for now
                val positionInInventory = ((j * playerInventoryHeight) + i) + playerHotbarSize;

                addSlotToContainer(Slot(
                        inventoryPlayer,
                        positionInInventory,
                        xStart + (j * slotSize),
                        playerInventoryYStart + (i * slotSize)
                ))
            }
        }

        // Now draw the player's hotbar
        for (k in 0 until 9) {
            addSlotToContainer(Slot(inventoryPlayer, k, xStart + (k * slotSize), playerHotbarYStart))
        }
    }

    override fun canInteractWith(playerIn: EntityPlayer) = true

    override fun transferStackInSlot(playerIn: EntityPlayer, index: Int): ItemStack {
        // Copied from https://github.com/shadowfacts/ShadowMC/blob/1.11/src/main/java/net/shadowfacts/shadowmc/inventory/ContainerBase.java
        // TODO: rewrite

        var itemstack = ItemStack.EMPTY
        val slot = inventorySlots[index]

        if (slot != null && slot.hasStack) {
            val itemstack1 = slot.stack
            itemstack = itemstack1.copy()

            val containerSlots = inventorySlots.size - playerIn.inventory.mainInventory.size

            if (index < containerSlots) {
                if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size, true)) {
                    return ItemStack.EMPTY
                }
            } else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
                return ItemStack.EMPTY
            }

            if (itemstack1.count == 0) {
                slot.putStack(ItemStack.EMPTY)
            } else {
                slot.onSlotChanged()
            }

            if (itemstack1.count == itemstack.count) {
                return ItemStack.EMPTY
            }

            slot.onTake(playerIn, itemstack1)
        }

        return itemstack
    }
}
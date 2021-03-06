package org.kotobank.kuarry

import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@Mod.EventBusSubscriber(Side.CLIENT, modid = KuarryMod.MODID)
@SideOnly(Side.CLIENT)
object KuarryModIcons {

    @SubscribeEvent
    fun textureStitchPre(event: TextureStitchEvent.Pre) =
            registerIcons(event.map)

    private fun TextureMap.registerIcon(icon: String): TextureAtlasSprite =
        registerSprite(ResourceLocation(KuarryMod.MODID, "gui/icons/$icon"))

    /** This is the atlas resource location. The texture HAS to be bound to this location,
     *  or things will fail to draw in certain specific situations. */
    val atlasResourceLocation = ResourceLocation("textures/atlas/blocks.png")

    lateinit var button: TextureAtlasSprite
    lateinit var buttonHighlight: TextureAtlasSprite

    lateinit var alwaysEnable: TextureAtlasSprite
    lateinit var RSWithout: TextureAtlasSprite
    lateinit var RSWith: TextureAtlasSprite
    lateinit var alwaysDisable: TextureAtlasSprite

    lateinit var boundsDisable: TextureAtlasSprite
    lateinit var boundsEnable: TextureAtlasSprite

    lateinit var blacklist: TextureAtlasSprite
    lateinit var whitelist: TextureAtlasSprite

    lateinit var blacklistAdditional: TextureAtlasSprite
    lateinit var blacklistOnly: TextureAtlasSprite

    lateinit var autopushDisable: TextureAtlasSprite
    lateinit var autopushEnable: TextureAtlasSprite

    lateinit var back: TextureAtlasSprite
    lateinit var fluidInventory: TextureAtlasSprite

    private fun registerIcons(map: TextureMap) {
        with (map) {
            button = registerIcon("button")
            buttonHighlight = registerIcon("button_highlight")

            alwaysEnable = registerIcon("activation_always_enable")
            RSWithout = registerIcon("activation_rs_without")
            RSWith = registerIcon("activation_rs_with")
            alwaysDisable = registerIcon("activation_always_disable")

            boundsDisable = registerIcon("bounds_disable")
            boundsEnable = registerIcon("bounds_enable")

            blacklist = registerIcon("blacklist")
            whitelist = registerIcon("whitelist")

            blacklistAdditional = registerIcon("blacklist_additional")
            blacklistOnly = registerIcon("blacklist_only")

            autopushDisable = registerIcon("autopush_disable")
            autopushEnable = registerIcon("autopush_enable")

            back = registerIcon("back")
            fluidInventory = registerIcon("fluid_inventory")
        }
    }
}
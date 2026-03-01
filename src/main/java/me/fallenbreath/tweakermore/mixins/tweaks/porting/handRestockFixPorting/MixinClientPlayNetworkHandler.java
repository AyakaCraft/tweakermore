package me.fallenbreath.tweakermore.mixins.tweaks.porting.handRestockFixPorting;

import fi.dy.masa.tweakeroo.config.FeatureToggle;
import fi.dy.masa.tweakeroo.tweaks.PlacementTweaks;
import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import me.fallenbreath.tweakermore.impl.porting.handRestockFixPorting.HandRestockUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientPacketListener.class})
public abstract class MixinClientPlayNetworkHandler {

    @Inject(method = "handleEntityEvent",
            at = @At(value = "INVOKE", ordinal = 0,
                    target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;findTotem(Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/item/ItemStack;"))
    private void onPlayerUseTotemOfUndying(ClientboundEntityEventPacket packet, CallbackInfo ci)
    {
        if(!TweakerMoreConfigs.HAND_RESTOCK_FIX_PORTING.getBooleanValue()) return;
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null)
        {
            return;
        }
        if (FeatureToggle.TWEAK_HAND_RESTOCK.getBooleanValue())
        {
            for (InteractionHand hand : InteractionHand.values())
            {
                if (minecraft.player.getItemInHand(hand).getItem() == Items.TOTEM_OF_UNDYING)
                {
                    PlacementTweaks.cacheStackInHand(hand);
                    // the slot update packet goes after this packet, let's set it to empty and restock
                    minecraft.player.setItemInHand(hand, ItemStack.EMPTY);
                    HandRestockUtils.onProcessRightClickPost(minecraft.player, hand);
                }
            }
        }
    }
}

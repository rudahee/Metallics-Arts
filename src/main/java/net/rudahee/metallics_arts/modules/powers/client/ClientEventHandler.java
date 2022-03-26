package net.rudahee.metallics_arts.modules.powers.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.rudahee.metallics_arts.modules.client.GUI.MetalOverlay;
import net.rudahee.metallics_arts.modules.data_player.InvestedCapability;
import net.rudahee.metallics_arts.setup.enums.extras.MetalsNBTData;
import org.lwjgl.glfw.GLFW;

import java.util.HashSet;
import java.util.Set;

public class ClientEventHandler {


    private final Minecraft mc = Minecraft.getInstance();

    private final Set<Entity> metal_entities = new HashSet<>();
    private final Set<PlayerEntity> nearby_allomancers = new HashSet<>();

    private int tickOffset = 0;

    /*@OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {


            player.getCapability(InvestedCapability.PLAYER_CAP).ifPresent(
                    playerCapability -> {

                        for (MetalsNBTData metal : MetalsNBTData.values()) {
                            System.out.println(metal.getNameLower() + ":" + player.getMainHandItem().getItem().getTags().contains(metal.getGemNameLower()) + "\n");
                            System.out.println(metal.getNameLower() + ":" + playerCapability.hasAllomanticPower(metal) + "\n");

                        }
                        if (!playerCapability.isFullInvested()) {
                            for (MetalsNBTData metal : MetalsNBTData.values()) {
                                playerCapability.addAllomanticPower(metal);
                                playerCapability.setMistborn(true);
                                playerCapability.addFeruchemicPower(metal);
                                playerCapability.setFullInvested(true);
                                playerCapability.setFullFeruchemic(true);
                            }
                        }
                    });

        }

    }*/


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (event.getAction() == GLFW.GLFW_PRESS) {
            acceptInput();
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onRenderGameOverlay(final RenderGameOverlayEvent event) {
        MetalOverlay.drawMetalOverlay(event.getMatrixStack());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onMouseInput(final InputEvent.MouseInputEvent event) {
        if (event.getAction() == GLFW.GLFW_PRESS) {
            acceptInput();
        }
    }

    /**
     * Handles either mouse or button presses for the mod's keybinds
     */
    private void acceptInput() {

    }



    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {

    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onSound(PlaySoundEvent event) {

    }
}

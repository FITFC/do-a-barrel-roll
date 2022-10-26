package nl.enjarai.doabarrelroll.compat.midnightcontrols.mixin;

import net.minecraft.client.MinecraftClient;
import nl.enjarai.doabarrelroll.compat.midnightcontrols.ControllerInputHandler;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "eu.midnightdust.midnightcontrols.client.MidnightInput")
public abstract class MidnightInputMixin {

    @Dynamic
    @Inject(
            method = "handleLook(Lnet/minecraft/client/MinecraftClient;IFI)V",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void handleElytraLook(MinecraftClient client, int axis, float value, int state, CallbackInfo ci) {
        if (ControllerInputHandler.onControllerInput(client, axis, value, state)) {
            ci.cancel();
        }
    }

    // reset the left vector after turning using the controller
    @Dynamic
    @Inject(
            method = "onRender(Lnet/minecraft/client/MinecraftClient;)V",
            at = @At(value = "TAIL")
    )
    private void onRender(MinecraftClient client, CallbackInfo ci) {
        ControllerInputHandler.afterLookUpdate(client);
    }
}

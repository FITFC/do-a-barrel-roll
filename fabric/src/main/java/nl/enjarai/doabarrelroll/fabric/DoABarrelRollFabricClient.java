package nl.enjarai.doabarrelroll.fabric;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.impl.client.keybinding.KeyBindingRegistryImpl;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import nl.enjarai.cicada.api.conversation.ConversationManager;
import nl.enjarai.cicada.api.util.CicadaEntrypoint;
import nl.enjarai.cicada.api.util.JsonSource;
import nl.enjarai.cicada.api.util.ProperLogger;
import nl.enjarai.doabarrelroll.DoABarrelRollClient;
import nl.enjarai.doabarrelroll.ModKeybindings;
import nl.enjarai.doabarrelroll.config.ModConfig;
import org.slf4j.Logger;

public class DoABarrelRollFabricClient implements ClientModInitializer, PreLaunchEntrypoint, CicadaEntrypoint {
    public static final Logger LOGGER = ProperLogger.getLogger(DoABarrelRollClient.MODID);

    @Override
    public void onInitializeClient() {
        ModConfig.touch();

        // Register keybindings on fabric
        ModKeybindings.ALL.forEach(KeyBindingRegistryImpl::registerKeyBinding);

        ClientTickEvents.END_CLIENT_TICK.register(DoABarrelRollClient::clientTick);
    }

    @Override
    public void onPreLaunch() {
        MixinExtrasBootstrap.init();
    }

    @Override
    public void registerConversations(ConversationManager conversationManager) {
        conversationManager.registerSource(
                JsonSource.fromUrl("https://raw.githubusercontent.com/enjarai/do-a-barrel-roll/master/fabric/src/main/resources/cicada/do-a-barrel-roll/conversations.json")
                        .or(JsonSource.fromResource("cicada/do-a-barrel-roll/conversations.json")),
                LOGGER::info
        );
    }
}

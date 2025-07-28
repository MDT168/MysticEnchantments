package com.mdt168.mysticEnchantments;

import com.mdt168.mysticEnchantments.custom.dataUtils.EnchantmentsUtils;
import com.mdt168.mysticEnchantments.enchants.EnchantmentStack;
import io.papermc.paper.datapack.DatapackRegistrar;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import io.papermc.paper.registry.data.EnchantmentRegistryEntry;
import io.papermc.paper.registry.event.RegistryEvents;
import io.papermc.paper.registry.keys.EnchantmentKeys;
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public class MyPluginBootstrap implements PluginBootstrap {
    @Override
    public void bootstrap(BootstrapContext context) {
        final LifecycleEventManager<@NotNull BootstrapContext> manager = context.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.DATAPACK_DISCOVERY, event -> {
            DatapackRegistrar registrar = event.registrar();
            try {
                final URI uri = Objects.requireNonNull(
                        MyPluginBootstrap.class.getResource("/MysticDatapack")
                ).toURI();
                registrar.discoverPack(uri, "mystic_enchants");
            } catch (final URISyntaxException | IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

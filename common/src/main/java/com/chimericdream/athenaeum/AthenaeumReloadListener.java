package com.chimericdream.athenaeum;

import com.chimericdream.athenaeum.registries.AthenaeumRegistries;
import com.google.common.base.Predicates;
import dev.architectury.registry.ReloadListenerRegistry;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.util.Map;

public class AthenaeumReloadListener implements SynchronousResourceReloader {
    public static void register() {
        ReloadListenerRegistry.register(ResourceType.SERVER_DATA, new AthenaeumReloadListener());
    }

    @Override
    public void reload(ResourceManager manager) {
        AthenaeumRegistries.BOOKS.clear();

        Map<Identifier, Resource> resources = manager.findResources("athenaeum_books", Predicates.alwaysTrue());
        resources.forEach((id, resource) -> {
            Identifier bookId = Identifier.of(
                id.getNamespace(),
                id.getPath()
                    .replace("athenaeum_books/", "")
                    .replace(".json", "")
            );

            try (InputStream stream = resource.getInputStream()) {
                AthenaeumRegistries.BOOKS.addFromInputStream(bookId, stream);
            } catch (Exception e) {
                AthenaeumMod.LOGGER.error("Error occurred while loading resource json" + id.toString(), e);
            }
        });
    }

    @Override
    public String getName() {
        return Identifier.of(AthenaeumModInfo.MOD_ID, "athenaeum_book_resource_listener").toString();
    }
}

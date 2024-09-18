package com.chimericdream.athenaeum;

import com.chimericdream.athenaeum.loot.AthenaeumLootFunctionTypes;
import com.chimericdream.athenaeum.loot.AthenaeumLootTables;
import com.chimericdream.athenaeum.registries.AthenaeumRegistries;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.RegistrarManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AthenaeumMod {
    public static final Logger LOGGER = LoggerFactory.getLogger("athenaeum");

    public static final Supplier<RegistrarManager> MANAGER = Suppliers.memoize(() -> RegistrarManager.get(AthenaeumModInfo.MOD_ID));

    public static void init() {
        AthenaeumRegistries.init();
        AthenaeumLootFunctionTypes.register();
        AthenaeumLootTables.init();
        AthenaeumReloadListener.register();
    }
}

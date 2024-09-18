package com.chimericdream.athenaeum.fabric;

import com.chimericdream.athenaeum.AthenaeumMod;
import net.fabricmc.api.ModInitializer;

public final class AthenaeumModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        AthenaeumMod.init();
    }
}

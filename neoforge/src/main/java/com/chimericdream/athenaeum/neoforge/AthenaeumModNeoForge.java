package com.chimericdream.athenaeum.neoforge;

import com.chimericdream.athenaeum.AthenaeumMod;
import com.chimericdream.athenaeum.AthenaeumModInfo;
import net.neoforged.fml.common.Mod;

@Mod(AthenaeumModInfo.MOD_ID)
public final class AthenaeumModNeoForge {
    public AthenaeumModNeoForge() {
        // Run our common setup.
        AthenaeumMod.init();
    }
}

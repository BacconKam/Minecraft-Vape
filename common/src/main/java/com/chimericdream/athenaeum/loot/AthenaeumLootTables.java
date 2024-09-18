package com.chimericdream.athenaeum.loot;

import dev.architectury.event.events.common.LootEvent;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;

import java.util.ArrayList;
import java.util.List;

public class AthenaeumLootTables {
    private static LootPool.Builder makeBuilder(int minRolls, int maxRolls, int chance) {
        LootPoolEntry.Builder<?> itemBuilder = ItemEntry
            .builder(Items.WRITTEN_BOOK)
            .apply(GetRandomBookFunction.builder());

        LootPool.Builder builder = LootPool.builder()
            .with(itemBuilder)
            .rolls(UniformLootNumberProvider.create(minRolls, maxRolls));

        if (chance == 1) {
            return builder;
        }

        return builder.with(EmptyEntry.builder().weight(chance - 1));
    }

    private static void checkVanillaLootTables(
        RegistryKey<LootTable> key,
        List<LootPool.Builder> lootPools
    ) {
        if (LootTables.STRONGHOLD_LIBRARY_CHEST.equals(key)) {
            lootPools.add(makeBuilder(2, 6, 3));
        }

        if (LootTables.WOODLAND_MANSION_CHEST.equals(key)) {
            lootPools.add(makeBuilder(1, 4, 4));
        }
    }

    public static void init() {
        LootEvent.MODIFY_LOOT_TABLE.register((key, context, builtin) -> {
            if (!builtin) {
                return;
            }

            List<LootPool.Builder> lootPools = new ArrayList<>();
            checkVanillaLootTables(key, lootPools);

            for (LootPool.Builder builder : lootPools) {
                context.addPool(builder);
            }
        });
    }
}

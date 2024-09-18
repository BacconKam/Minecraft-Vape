package com.chimericdream.athenaeum.loot;

import com.chimericdream.athenaeum.registries.AthenaeumRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.WrittenBookContentComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProviderTypes;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class GetRandomBookFunction extends ConditionalLootFunction {
    public static final MapCodec<GetRandomBookFunction> CODEC = RecordCodecBuilder
        .mapCodec(
            (instance) -> addConditionsField(instance)
                .and(
                    instance.group(
                        Codec
                            .list(Codec.STRING)
                            .optionalFieldOf("author")
                            .forGetter((function) -> function.author),
                        LootNumberProviderTypes.CODEC
                            .optionalFieldOf("edition")
                            .forGetter((function) -> function.edition)
                    )
                )
                .apply(instance, GetRandomBookFunction::new)
        );

    private final Optional<List<String>> author;
    private final Optional<LootNumberProvider> edition;

    protected GetRandomBookFunction(
        List<LootCondition> conditions,
        Optional<List<String>> author,
        Optional<LootNumberProvider> edition
    ) {
        super(conditions);

        this.author = author;
        this.edition = edition;
    }

    @Override
    public LootFunctionType<GetRandomBookFunction> getType() {
        return AthenaeumLootFunctionTypes.GET_RANDOM_BOOK;
    }

    @Override
    protected ItemStack process(ItemStack stack, LootContext context) {
        if (!stack.isOf(Items.WRITTEN_BOOK)) {
            return stack;
        }

        List<String> authors = this.author.orElse(List.of());
        LootNumberProvider edition = this.edition.orElse(ConstantLootNumberProvider.create(-1));

        WrittenBookContentComponent content = AthenaeumRegistries.BOOKS.getRandomBookForLootTable(authors, edition, context);

        if (content == null) {
            return stack;
        }

        stack.apply(
            DataComponentTypes.WRITTEN_BOOK_CONTENT,
            content,
            UnaryOperator.identity()
        );

        return stack;
    }

    public static ConditionalLootFunction.Builder<?> builder() {
        return builder((list) -> new GetRandomBookFunction(list, Optional.empty(), Optional.empty()));
    }
}

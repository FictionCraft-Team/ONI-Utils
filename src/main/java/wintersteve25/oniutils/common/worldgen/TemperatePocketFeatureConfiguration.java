package wintersteve25.oniutils.common.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class TemperatePocketFeatureConfiguration implements FeatureConfiguration {
    public static final Codec<TemperatePocketFeatureConfiguration> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT.fieldOf("width").forGetter(TemperatePocketFeatureConfiguration::getWidth),
                    Codec.INT.fieldOf("height").forGetter(TemperatePocketFeatureConfiguration::getHeight),
                    TagKey.hashedCodec(Registry.BLOCK_REGISTRY).fieldOf("irreplaceableBlocks").forGetter(TemperatePocketFeatureConfiguration::getIrreplaceableBlocks)
            ).apply(instance, TemperatePocketFeatureConfiguration::new)
    );

    private final int width;
    private final int height;
    private final TagKey<Block> irreplaceableBlocks;

    public TemperatePocketFeatureConfiguration(int width, int height, TagKey<Block> irreplaceableBlocks) {
        this.width = width;
        this.height = height;
        this.irreplaceableBlocks = irreplaceableBlocks;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public TagKey<Block> getIrreplaceableBlocks() {
        return irreplaceableBlocks;
    }
}

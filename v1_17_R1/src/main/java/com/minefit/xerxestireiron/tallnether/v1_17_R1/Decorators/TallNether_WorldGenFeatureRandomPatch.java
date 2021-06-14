package com.minefit.xerxestireiron.tallnether.v1_17_R1.Decorators;

import java.util.Random;

import com.minefit.xerxestireiron.tallnether.ConfigAccessor;
import com.minefit.xerxestireiron.tallnether.WorldConfig;
import com.mojang.serialization.Codec;

import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagsFluid;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRandomPatchConfiguration;

public class TallNether_WorldGenFeatureRandomPatch extends WorldGenerator<WorldGenFeatureRandomPatchConfiguration> {

    private final ConfigAccessor configAccessor = new ConfigAccessor();

    public TallNether_WorldGenFeatureRandomPatch(Codec<WorldGenFeatureRandomPatchConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeaturePlaceContext<WorldGenFeatureRandomPatchConfiguration> featureplacecontext) {
        WorldGenFeatureRandomPatchConfiguration worldgenfeaturerandompatchconfiguration = (WorldGenFeatureRandomPatchConfiguration) featureplacecontext
                .e();
        Random random = featureplacecontext.c();
        BlockPosition blockposition = featureplacecontext.d();
        GeneratorAccessSeed generatoraccessseed = featureplacecontext.a();
        IBlockData iblockdata = worldgenfeaturerandompatchconfiguration.b.a(random, blockposition); // stateProvider -> b
        BlockPosition blockposition1;

        if (worldgenfeaturerandompatchconfiguration.k) { // project -> k
            blockposition1 = generatoraccessseed.getHighestBlockYAt(HeightMap.Type.a, blockposition); // WORLD_SURFACE_WG -> a
        } else {
            blockposition1 = blockposition;
        }

        int i = 0;
        BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

        WorldConfig worldConfig = this.configAccessor
                .getWorldConfig(generatoraccessseed.getMinecraftWorld().getWorld().getName());
        // Use this to double the tries to match double height nether
        int tries = (worldConfig.isVanilla) ? worldgenfeaturerandompatchconfiguration.f : worldgenfeaturerandompatchconfiguration.f * 2; // tries -> f

        for (int j = 0; j < tries; ++j) { // xspread -> g  yspread -> h  zspread -> i
            blockposition_mutableblockposition.a((BaseBlockPosition) blockposition1,
                    random.nextInt(worldgenfeaturerandompatchconfiguration.g + 1)
                            - random.nextInt(worldgenfeaturerandompatchconfiguration.g + 1),
                    random.nextInt(worldgenfeaturerandompatchconfiguration.h + 1)
                            - random.nextInt(worldgenfeaturerandompatchconfiguration.h + 1),
                    random.nextInt(worldgenfeaturerandompatchconfiguration.i + 1)
                            - random.nextInt(worldgenfeaturerandompatchconfiguration.i + 1));
            BlockPosition blockposition2 = blockposition_mutableblockposition.down();
            IBlockData iblockdata1 = generatoraccessseed.getType(blockposition2);
            // canReplace -> j  whitelist -> d  blacklist -> e  needWater -> l  blockPlacer -> c
            if ((generatoraccessseed.isEmpty(blockposition_mutableblockposition)
                    || worldgenfeaturerandompatchconfiguration.j && generatoraccessseed
                            .getType(blockposition_mutableblockposition).getMaterial().isReplaceable())
                    && iblockdata.canPlace(generatoraccessseed, blockposition_mutableblockposition)
                    && (worldgenfeaturerandompatchconfiguration.d.isEmpty()
                            || worldgenfeaturerandompatchconfiguration.d.contains(iblockdata1.getBlock()))
                    && !worldgenfeaturerandompatchconfiguration.e.contains(iblockdata1)
                    && (!worldgenfeaturerandompatchconfiguration.l
                            || generatoraccessseed.getFluid(blockposition2.west()).a((Tag) TagsFluid.b) // WATER -> b
                            || generatoraccessseed.getFluid(blockposition2.east()).a((Tag) TagsFluid.b)
                            || generatoraccessseed.getFluid(blockposition2.north()).a((Tag) TagsFluid.b)
                            || generatoraccessseed.getFluid(blockposition2.south()).a((Tag) TagsFluid.b))) {
                worldgenfeaturerandompatchconfiguration.c.a(generatoraccessseed, blockposition_mutableblockposition,
                        iblockdata, random);
                ++i;
            }
        }

        return i > 0;
    }
}

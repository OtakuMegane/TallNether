package com.minefit.xerxestireiron.tallnether.v1_13_R1;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Logger;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_13_R1.CraftWorld;
import org.bukkit.event.Listener;

import com.minefit.xerxestireiron.tallnether.Messages;

import net.minecraft.server.v1_13_R1.SchedulerBatch;
import net.minecraft.server.v1_13_R1.BiomeBase;
import net.minecraft.server.v1_13_R1.BiomeHell;
import net.minecraft.server.v1_13_R1.BiomeLayout;
import net.minecraft.server.v1_13_R1.Biomes;
import net.minecraft.server.v1_13_R1.Blocks;
import net.minecraft.server.v1_13_R1.ChunkGenerator;
import net.minecraft.server.v1_13_R1.ChunkTaskScheduler;
import net.minecraft.server.v1_13_R1.GeneratorSettingsNether;
import net.minecraft.server.v1_13_R1.IChunkLoader;
import net.minecraft.server.v1_13_R1.WorldProvider;
import net.minecraft.server.v1_13_R1.WorldServer;

public class LoadHell implements Listener {
    private final World world;
    private final WorldServer nmsWorld;
    private final String worldName;
    private String originalGenName;
    private WorldProvider worldProvider;
    private final Messages messages;
    private ChunkGenerator<?> originalGenerator;
    private final ConfigurationSection worldConfig;

    public LoadHell(World world, ConfigurationSection worldConfig, String pluginName) {
        this.world = world;
        this.worldConfig = worldConfig;
        this.nmsWorld = ((CraftWorld) world).getHandle();
        new ConfigValues(this.nmsWorld, this.worldConfig);
        this.messages = new Messages(pluginName);
        this.worldName = this.world.getName();
        this.originalGenerator = this.nmsWorld.getChunkProviderServer().chunkGenerator;
        this.originalGenName = this.originalGenerator.getClass().getSimpleName();
        this.worldProvider = this.nmsWorld.worldProvider;
        overrideGenerator();
    }

    public void restoreGenerator() {
        boolean success = setGenerator(this.originalGenerator, true);
    }

    public void overrideGenerator() {
        GeneratorSettingsNether generatorsettingsnether = new GeneratorSettingsNether();
        generatorsettingsnether.a(Blocks.NETHERRACK.getBlockData());
        generatorsettingsnether.b(Blocks.LAVA.getBlockData());
        Environment environment = this.world.getEnvironment();
        TallNether_ChunkProviderHell tallNetherGenerator = new TallNether_ChunkProviderHell(this.nmsWorld,
                BiomeLayout.c.a(BiomeLayout.c.a().a(Biomes.j)), generatorsettingsnether, this.worldConfig);
        new InitializeDecorators(this.worldConfig);

        if (environment != Environment.NETHER) {
            this.messages.unknownEnvironment(this.worldName, environment.toString());
            return;
        }

        if (this.originalGenName.equals("TallNether_ChunkProviderHell")) {
            this.messages.alreadyEnabled(this.worldName);
            return;
        }

        if (!this.originalGenName.equals("NetherChunkGenerator")
                && !this.originalGenName.equals("TimedChunkGenerator")) {
            this.messages.unknownGenerator(this.worldName, this.originalGenName);
            return;
        }

        boolean success = setGenerator(tallNetherGenerator, false);

        if (success) {
            this.messages.enabledSuccessfully(this.worldName);
        } else {
            this.messages.enableFailed(this.worldName);
        }
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
    private boolean setGenerator(ChunkGenerator<?> generator, boolean heightValue) {
        Logger.getLogger("Minecraft").info("" + nmsWorld.aa());

        try {
            BiomeHell biomeHell = (BiomeHell) BiomeBase.a(8);
            Field q = net.minecraft.server.v1_13_R1.BiomeBase.class.getDeclaredField("q");
            q.setAccessible(true);
            setFinal(q, new TallNether_WorldGenDecoratorChanceHeight(), biomeHell);

            Field chunkGenerator = net.minecraft.server.v1_13_R1.ChunkProviderServer.class
                    .getDeclaredField("chunkGenerator");
            chunkGenerator.setAccessible(true);
            setFinal(chunkGenerator, generator, this.nmsWorld.getChunkProviderServer());

            Field worldHeight = net.minecraft.server.v1_13_R1.WorldProvider.class.getDeclaredField("d");
            worldHeight.setAccessible(true);
            worldHeight.setBoolean(this.worldProvider, heightValue);

            Logger.getLogger("Minecraft").info("" + nmsWorld.aa());

            Field chunkLoader = net.minecraft.server.v1_13_R1.ChunkProviderServer.class.getDeclaredField("chunkLoader");
            chunkLoader.setAccessible(true);
            IChunkLoader ichunkLoader = (IChunkLoader) chunkLoader.get(this.nmsWorld.getChunkProviderServer());
            ChunkTaskScheduler newScheduler = new ChunkTaskScheduler(0, this.nmsWorld, generator, ichunkLoader,
                    this.nmsWorld);

            Field scheduler = net.minecraft.server.v1_13_R1.ChunkProviderServer.class.getDeclaredField("f");
            scheduler.setAccessible(true);
            setFinal(scheduler, newScheduler, this.nmsWorld.getChunkProviderServer());
            Field g = net.minecraft.server.v1_13_R1.ChunkProviderServer.class.getDeclaredField("g");
            g.setAccessible(true);
            setFinal(g, new SchedulerBatch(newScheduler), this.nmsWorld.getChunkProviderServer());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    private void setFinal(Field field, Object obj, Object instance) throws Exception {
        field.setAccessible(true);

        Field mf = Field.class.getDeclaredField("modifiers");
        mf.setAccessible(true);
        mf.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(instance, obj);
    }
}

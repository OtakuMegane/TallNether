package com.minefit.xerxestireiron.tallnether.v1_13_R1;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.TravelAgent;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_13_R1.CraftWorld;
import org.bukkit.event.Listener;

import com.minefit.xerxestireiron.tallnether.Messages;

import net.minecraft.server.v1_13_R1.RegistryBlockID;
import net.minecraft.server.v1_13_R1.SchedulerBatch;
import net.minecraft.server.v1_13_R1.WorldGenCavesHell;
import net.minecraft.server.v1_13_R1.BiomeBase;
import net.minecraft.server.v1_13_R1.BiomeLayout;
import net.minecraft.server.v1_13_R1.BiomeLayoutFixedConfiguration;
import net.minecraft.server.v1_13_R1.Biomes;
import net.minecraft.server.v1_13_R1.Blocks;
import net.minecraft.server.v1_13_R1.ChunkGenerator;
import net.minecraft.server.v1_13_R1.ChunkTaskScheduler;
import net.minecraft.server.v1_13_R1.GeneratorSettingsNether;
import net.minecraft.server.v1_13_R1.MinecraftKey;
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
        GeneratorSettingsNether generatorsettingsnether = new TallNether_GeneratorSettingsNether();
        generatorsettingsnether.a(Blocks.NETHERRACK.getBlockData());
        generatorsettingsnether.b(Blocks.LAVA.getBlockData());
        Environment environment = this.world.getEnvironment();
        TallNether_ChunkProviderHell tallNetherGenerator = new TallNether_ChunkProviderHell(this.nmsWorld,
                BiomeLayout.c.a(((BiomeLayoutFixedConfiguration) BiomeLayout.c.a()).a(Biomes.j)),
                generatorsettingsnether, this.worldConfig);
        /*TallNether_ChunkProviderHell_min tallNetherGenerator = new TallNether_ChunkProviderHell_min(this.nmsWorld,
                BiomeLayout.c.a(((BiomeLayoutFixedConfiguration) BiomeLayout.c.a()).a(Biomes.j)),
                generatorsettingsnether, this.worldConfig);*/

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

    private boolean setGenerator(ChunkGenerator<?> generator, boolean heightValue) {

        //BiomeBase.t();
        Logger.getLogger("Minecraft").info("" + nmsWorld.aa());

        try {
            Field cavesHell = net.minecraft.server.v1_13_R1.BiomeBase.class.getDeclaredField("c");
            cavesHell.setAccessible(true);
            setFinal(cavesHell, new TallNether_WorldGenCavesHell(), null);
            Field chunkGenerator = net.minecraft.server.v1_13_R1.ChunkProviderServer.class
                    .getDeclaredField("chunkGenerator");
            chunkGenerator.setAccessible(true);
            setFinal(chunkGenerator, generator, this.nmsWorld.getChunkProviderServer());

            Field worldHeight = net.minecraft.server.v1_13_R1.WorldProvider.class.getDeclaredField("d");
            worldHeight.setAccessible(true);
            //this.worldProvider = new TallNether_WorldProviderHell(worldConfig);
            worldHeight.setBoolean(this.worldProvider, heightValue);
            Logger.getLogger("Minecraft").info("" + nmsWorld.aa());
            // For new CraftBukkit stuff; check during Spigot updates
            Field scheduler = net.minecraft.server.v1_13_R1.ChunkProviderServer.class.getDeclaredField("f");
            scheduler.setAccessible(true);
            ChunkTaskScheduler schedulerInstance = (ChunkTaskScheduler) scheduler.get(this.nmsWorld.getChunkProviderServer());
            Field d = schedulerInstance.getClass().getDeclaredField("d");
            d.setAccessible(true);
            setFinal(d, generator, schedulerInstance);
            Logger.getLogger("Minecraft").info("" + nmsWorld.aa());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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

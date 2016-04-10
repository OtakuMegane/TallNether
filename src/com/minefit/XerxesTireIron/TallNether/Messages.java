package com.minefit.XerxesTireIron.TallNether;

import java.util.logging.Logger;

import com.minefit.XerxesTireIron.TallNether.TallNether;

public class Messages {
    private TallNether plugin;
    private String pluginName;
    private Logger logger = Logger.getLogger("Minecraft");

    public Messages(TallNether instance) {
        this.plugin = instance;
        this.pluginName = plugin.getName();
    }

    public void unknownGenerator(String worldName, String generatorName) {
        logger.info(
                "[" + this.pluginName + " Error] The world '" + worldName + "' does not have a recognized generator.");
        logger.info("[" + this.pluginName
                + " Error] A custom generator may already be in place or Mojang changed something.");
        logger.info("[" + this.pluginName + " Error] The generator detected is: '" + generatorName + "'.");
        logger.info("[" + this.pluginName + " Error] For safety, TallNether will not be enabled on this world.");
    }

    public void unknownEnvironment(String worldName, String environment) {
        logger.info("[" + this.pluginName + " Error] The world '" + worldName + "' is not a Nether environment.");
        logger.info("[" + this.pluginName + " Error] TallNether will not be enabled on this world.");
    }

    public void incompatibleVersion() {
        logger.info("[" + this.pluginName + " Error] This version of Minecraft is not supported. Disabling plugin.");
    }

    public void enabledSuccessfully(String worldName) {
        logger.info(
                "[" + this.pluginName + " Success] The world '" + worldName + "' will have a 256-block high nether!");
    }

    public void pluginReady() {
        logger.info("[" + this.pluginName + "] Everything is ready to go!");
    }

    public void pluginDisable() {
        logger.info("[" + pluginName + "] " + pluginName + " now disabled.");
    }

    public void alreadyEnabled(String worldName) {
        logger.info("[" + this.pluginName + " Success] TallNether appears to already be enabled for this world.");
        logger.info(
                "[" + this.pluginName + " Success] The world '" + worldName + "' will have a 256-block high nether!");
    }
}

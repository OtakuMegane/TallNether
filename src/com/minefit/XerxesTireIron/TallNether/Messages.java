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
        this.logger.info(
                "[" + this.pluginName + " Error] The world '" + worldName + "' does not have a recognized generator.");
        this.logger.info("[" + this.pluginName
                + " Error] A custom generator may already be in place or Mojang changed something.");
        this.logger.info("[" + this.pluginName + " Error] The generator detected is: '" + generatorName + "'.");
        this.logger.info("[" + this.pluginName + " Error] For safety, TallNether will not be enabled on this world.");
    }

    public void unknownEnvironment(String worldName, String environment) {
        this.logger.info("[" + this.pluginName + " Error] The world '" + worldName + "' is not a Nether environment.");
        this.logger.info("[" + this.pluginName + " Error] TallNether will not be enabled on this world.");
    }

    public void incompatibleVersion() {
        this.logger.info("[" + this.pluginName + " Error] This version of Minecraft is not supported. Disabling plugin.");
    }

    public void enabledSuccessfully(String worldName) {
        this.logger.info(
                "[" + this.pluginName + " Success] The world '" + worldName + "' will have a 256-block high nether!");
    }

    public void pluginReady() {
        this.logger.info("[" + this.pluginName + "] Everything is ready to go!");
    }

    public void pluginDisable() {
        this.logger.info("[" + this.pluginName + "] " + this.pluginName + " now disabled.");
    }

    public void alreadyEnabled(String worldName) {
        this.logger.info("[" + this.pluginName + " Success] TallNether appears to already be enabled for this world.");
        this.logger.info(
                "[" + this.pluginName + " Success] The world '" + worldName + "' will have a 256-block high nether!");
    }
}

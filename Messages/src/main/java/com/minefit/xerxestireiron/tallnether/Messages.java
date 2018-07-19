package com.minefit.xerxestireiron.tallnether;

import java.util.logging.Logger;

public class Messages {
    private final String pluginName;
    private final Logger logger = Logger.getLogger("Minecraft");

    public Messages(String pluginName) {
        this.pluginName = pluginName;
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
        this.logger
                .info("[" + this.pluginName + " Error] This version of Minecraft is not supported. Disabling plugin.");
    }

    public void enableSuccess(String worldName) {
        this.logger.info(
                "[" + this.pluginName + " Success] The world '" + worldName + "' will have a 256-block high nether!");
    }

    public void enableFailed(String worldName) {
        this.logger.info("[" + this.pluginName + " Error] Something went wrong enabling TallNether on world '"
                + worldName + "'.");
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

    public void restoreFailed(String worldName) {
        this.logger.info("[" + this.pluginName + " Error] Something went wrong while restoring the original Nether generation.");
    }
}

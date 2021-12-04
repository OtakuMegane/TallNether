# TallNether
## About
A plugin for Spigot and Paper that doubles the height of the vanilla Nether and allows some customization of the generator.

## Usage
The official page, downloads and instructions can be found here: [TallNether](https://www.spigotmc.org/resources/tallnether.22561/)

## Compiling
To compile the plugin yourself you will need:
 - Maven
 - The CraftBukkit jars for each NMS revision of Minecraft 1.12 - 1.14
 - The Spigot jars for each NMS revision of Minecraft 1.15 - 1.18
 - The Paper jars for each NMS revision of Minecraft 1.12 and 1.15 - 1.18
 
Any necessary dependencies are specified in the pom.xml for each module. Spigot/CraftBukkit APIs will be downloaded automatically when building the module but the CraftBukkit, Spigot and Paper jars will need to be compiled and added to your local Maven repo.
 
Once dependencies are in place the plugin can be built by doing `mvn install` on the parent project. The plugin will be found in the `TallNether/target/` directory.
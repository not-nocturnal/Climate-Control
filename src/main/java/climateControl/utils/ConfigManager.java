package climateControl.utils;

import java.io.File;
import java.util.logging.Logger;

import net.minecraft.world.WorldServer;
import net.minecraftforge.common.config.Configuration;

/**
 * This class manages overall and world-specific configs
 * 
 * @author Zeno410
 */
public class ConfigManager<Type extends Settings> {

    static Logger logger = new Zeno410Logger("ConfigManager").logger();
    public final static String CONFIG_DIRECTORY = "worldSpecificConfig";
    private Configuration general;
    private File generalConfigFile;
    private Type settings;
    private File worldConfigFile;
    private Configuration worldSpecific;

    public ConfigManager(Configuration general, Type settings, File generalFile) {
        this.general = general;
        this.settings = settings;
        this.generalConfigFile = generalFile;
    }

    private boolean usable(File tested) {
        return tested != null;
    }

    public void saveWorldSpecific() {
        worldSpecific.save();
    }

    private void setWorldConfigFile(File newFile) {
        if ((worldConfigFile == null) || (!newFile.getAbsolutePath()
            .equals(worldConfigFile.getAbsolutePath()))) {
            worldConfigFile = newFile;
            if (usable(worldConfigFile)) {
                // usable world
                if (newFile.exists()) {
                    worldSpecific = new Configuration(worldConfigFile);
                    worldSpecific.load();
                    settings.readFrom(worldSpecific);
                } else {
                    worldSpecific = new Configuration(worldConfigFile);
                    // else we use the default;
                    settings.readFrom(general);
                    settings.copyTo(worldSpecific);
                }
                worldSpecific.save();
            } else {
                worldSpecific = null;
                settings.readFrom(general);
            }
        }
    }

    public void setWorldFile(File newFile) {
        // this is the world save directory
        File configDirectory = new File(newFile, CONFIG_DIRECTORY);
        configDirectory.mkdir();

        String configName = generalConfigFile.getPath();
        String generalConfigDirectoryName = generalConfigFile.getParentFile()
            .getPath();
        String detailName = configName.substring(generalConfigDirectoryName.length() + 1);
        File localConfigFile = new File(configDirectory, detailName);
        setWorldConfigFile(localConfigFile);
    }

    public void clearWorldFile() {
        worldConfigFile = null;
        worldSpecific = null;
    }

    public void setWorldFile(WorldServer server) {
        setWorldFile(server.getChunkSaveLocation());
    }

}

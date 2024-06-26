
package climateControl.utils;

import java.io.File;
import java.util.logging.Logger;

import net.minecraft.world.WorldServer;
import net.minecraftforge.common.config.Configuration;

import climateControl.api.BiomeSettings;

/**
 *
 * @author Zeno410
 */

public class TaggedConfigManager<Type extends Settings> {

    private final String modConfigName;
    private final String groupDirectoryName;
    public final static String worldSpecificConfigFileName = "worldSpecificConfig";
    public static Logger logger = new Zeno410Logger("TaggedConfigManager").logger();

    public TaggedConfigManager(String generalConfigName, String groupDirectoryName) {
        this.modConfigName = generalConfigName;
        this.groupDirectoryName = groupDirectoryName;
    }

    public void updateConfig(Named<Type> namedSettings, File generalDirectory, File specificDirectory) {
        Settings settings = namedSettings.object;
        { // block to localize vars
            File generalModFile = new File(generalDirectory, modConfigName);
            File generalAddOnDirectory = new File(generalDirectory, groupDirectoryName);
            if (!generalAddOnDirectory.exists()) generalAddOnDirectory.mkdir();
            File generalAddonFile = new File(generalAddOnDirectory, namedSettings.name);
            readConfigs(generalModFile, generalAddonFile, settings, generalDirectory, true);
            // nativise IDS
            try {
                BiomeSettings biomeSettings = (BiomeSettings) settings;
                // biomeSettings.stripFrom(general);
                biomeSettings.setNativeBiomeIDs(generalDirectory);
            } catch (ClassCastException ex) {
                // not Biome Settings
            }
        }

        File specificModFile = new File(specificDirectory, modConfigName);
        File specificAddOnDirectory = new File(specificDirectory, groupDirectoryName);
        if (!specificAddOnDirectory.exists()) specificAddOnDirectory.mkdir();
        if (!specificAddOnDirectory.exists()) throw new RuntimeException(specificAddOnDirectory.getAbsolutePath());
        File specificAddonFile = new File(specificAddOnDirectory, namedSettings.name);
        readConfigs(specificModFile, specificAddonFile, settings, generalDirectory, false);

    }

    public void initializeConfig(Named<Type> namedSettings, File generalDirectory) {
        Settings settings = namedSettings.object;
        File generalModFile = new File(generalDirectory, modConfigName);
        File generalAddOnDirectory = new File(generalDirectory, groupDirectoryName);
        File generalAddonFile = new File(generalAddOnDirectory, namedSettings.name);
        readConfigs(generalModFile, generalAddonFile, settings, generalDirectory, true);
        // taking out the IDs from the general addon File - they don't do anything
        try {
            BiomeSettings biomeSettings = (BiomeSettings) settings;
            Configuration sample = new Configuration(generalAddonFile);// assured to exist now
            biomeSettings.stripIDsFrom(sample);
            sample.save();
            biomeSettings.setNativeBiomeIDs(generalDirectory);
        } catch (ClassCastException e) {
            // not a biome settings
        }
    }

    private void readConfigs(File generalFile, File specificFile, Settings settings, File generalDirectory,
        boolean isGeneral) {
        Configuration specific = null;
        try {
            settings.readForeignConfigs(generalDirectory);

        } catch (ClassCastException e) {
            // not a biome settings
        }
        if (specificFile.exists()) {
            specific = new Configuration(specificFile);
            settings.readFrom(specific);
        } else {
            if (generalFile.exists()) {
                Configuration general = new Configuration(generalFile);
                // we are no longer retrieving old config data
                // using this to clean up stuff that should not be there and is confusing users
                // settings.readFrom(new Configuration(generalFile));
                settings.readForeignConfigs(generalDirectory);
                try {
                    BiomeSettings biomeSettings = (BiomeSettings) settings;
                    // biomeSettings.stripFrom(general);
                    biomeSettings.setNativeBiomeIDs(generalDirectory);
                } catch (ClassCastException ex) {
                    // not Biome Settings
                }
                // settings.copyTo(general);
                general.save();
            }
            specific = new Configuration(specificFile);
            settings.copyTo(specific);
        }
        settings.copyTo(specific);
        try {
            BiomeSettings biomeSettings = (BiomeSettings) settings;
            if (isGeneral) biomeSettings.stripIDsFrom(specific);
        } catch (ClassCastException ex) {
            // not Biome Settings
        }
        // se
        specific.save();
    }

    public void saveConfigs(File generalDirectory, File specificDirectory, Named<Settings> namedSettings) {
        File specificModFile = new File(specificDirectory, modConfigName);

        Configuration general = new Configuration(specificModFile);
        // we are no longer retrieving old config data
        // using this to clean up stuff that should not be there and is confusing users
        // settings.readFrom(new Configuration(generalFile));
        try {
            BiomeSettings biomeSettings = (BiomeSettings) namedSettings.object;
            // biomeSettings.stripFrom(general);
        } catch (ClassCastException ex) {
            // not Biome Settings
        }
        // settings.copyTo(general);
        general.save();

        File specificAddOnDirectory = new File(specificDirectory, groupDirectoryName);
        if (!specificAddOnDirectory.exists()) specificAddOnDirectory.mkdir();
        if (!specificAddOnDirectory.exists()) throw new RuntimeException(specificAddOnDirectory.getAbsolutePath());
        File specificAddonFile = new File(specificAddOnDirectory, namedSettings.name);
        Configuration specific = new Configuration(specificAddonFile);
        namedSettings.object.copyTo(specific);
        try {
            BiomeSettings biomeSettings = (BiomeSettings) namedSettings.object;
            // presently only called from dimensions
            biomeSettings.setNativeBiomeIDs(generalDirectory);
            // biomeSettings.stripIDsFrom(specific);

        } catch (ClassCastException ex) {
            // not Biome Settings
        }
        specific.save();
    }

    private boolean usable(File tested) {
        return tested != null;
    }

    public void updateConfig(Named<Type> namedSettings, File generalDirectory, WorldServer server) {
        File configDirectory = new File(server.getChunkSaveLocation(), worldSpecificConfigFileName);
        configDirectory.mkdir();
        this.updateConfig(namedSettings, generalDirectory, configDirectory);
    }
}

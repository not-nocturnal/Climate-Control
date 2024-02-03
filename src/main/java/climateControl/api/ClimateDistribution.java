
package climateControl.api;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Zeno410
 */
public class ClimateDistribution {

    public static final ArrayList<ClimateDistribution> list = new ArrayList<ClimateDistribution>();

    private ArrayList<Climate> climates = new ArrayList<Climate>();
    private final String name;
    public static ClimateDistribution SNOWY = new ClimateDistribution("SNOWY", Climate.SNOWY);
    public static ClimateDistribution COOL = new ClimateDistribution("COOL", Climate.COOL);
    public static ClimateDistribution WARM = new ClimateDistribution("WARM", Climate.WARM);
    public static ClimateDistribution HOT = new ClimateDistribution("HOT", Climate.HOT);
    public static ClimateDistribution SNOWYCOOL = new ClimateDistribution("SNOWYCOOL", Climate.SNOWY, Climate.COOL);
    public static ClimateDistribution SNOWYWARM = new ClimateDistribution("SNOWYWARM", Climate.SNOWY, Climate.WARM);
    public static ClimateDistribution SNOWYHOT = new ClimateDistribution("SNOWYHOT", Climate.SNOWY, Climate.HOT);
    // left in for existing config compatibility:
    public static ClimateDistribution MEDIUM = new ClimateDistribution("MEDIUM", Climate.COOL, Climate.WARM);
    // same as MEDIUM:
    public static ClimateDistribution COOLWARM = new ClimateDistribution("COOLWARM", Climate.COOL, Climate.WARM);
    public static ClimateDistribution COOLHOT = new ClimateDistribution("COOLHOT", Climate.COOL, Climate.HOT);
    public static ClimateDistribution WARMHOT = new ClimateDistribution("WARMHOT", Climate.WARM, Climate.HOT);
    public static ClimateDistribution NOTHOT = new ClimateDistribution(
        "NOTHOT",
        Climate.SNOWY,
        Climate.COOL,
        Climate.WARM);
    public static ClimateDistribution NOTWARM = new ClimateDistribution(
        "NOTWARM",
        Climate.SNOWY,
        Climate.COOL,
        Climate.HOT);
    public static ClimateDistribution NOTCOOL = new ClimateDistribution(
        "NOTCOOL",
        Climate.SNOWY,
        Climate.WARM,
        Climate.HOT);
    public static ClimateDistribution PLAINS = new ClimateDistribution( // left in for existing config compatibility
        "PLAINS",
        Climate.COOL,
        Climate.WARM,
        Climate.HOT);
    public static ClimateDistribution NOTSNOWY = new ClimateDistribution( // same as PLAINS
        "NOTSNOWY",
        Climate.COOL,
        Climate.WARM,
        Climate.HOT);
    public static ClimateDistribution OCEAN = new ClimateDistribution("OCEAN", Climate.OCEAN);
    public static ClimateDistribution DEEP_OCEAN = new ClimateDistribution("DEEP_OCEAN", Climate.DEEP_OCEAN);
    public static ClimateDistribution LAND = new ClimateDistribution(
        "LAND",
        Climate.SNOWY,
        Climate.COOL,
        Climate.WARM,
        Climate.HOT);
    public static ClimateDistribution WATER = new ClimateDistribution("WATER", Climate.OCEAN, Climate.DEEP_OCEAN);
    public static ClimateDistribution ALL = new ClimateDistribution(
        "ALL",
        Climate.SNOWY,
        Climate.COOL,
        Climate.WARM,
        Climate.HOT,
        Climate.OCEAN,
        Climate.DEEP_OCEAN);

    public ClimateDistribution(String name, Climate... base) {
        for (int i = 0; i < base.length; i++) {
            climates.add(base[i]);
        }
        this.name = name;
        list.add(this);
    }

    public String name() {
        return name;
    }

    public class Incidence {

        public final int biome;
        public final int incidence; // final because it's constantly regenerated
        public final Climate climate;

        private Incidence(int biome, int incidence, Climate climate) {
            this.biome = biome;
            this.incidence = incidence;
            this.climate = climate;
        }
    }

    public Collection<Incidence> incidences(BiomeSettings.Element element) {
        // return a list of Incidences with that element's incidence split up amont that distribution's
        // climates;
        ArrayList<Incidence> result = new ArrayList<Incidence>();
        int doneSoFar = 0;
        int remainingIncidence = element.biomeIncidences()
            .value();
        for (Climate climate : climates) {
            int thisIncidence = remainingIncidence / (climates.size() - doneSoFar);
            doneSoFar++;
            result.add(
                new Incidence(
                    element.biomeID()
                        .value(),
                    thisIncidence,
                    climate));
            remainingIncidence -= thisIncidence;
        }
        return result;
    }
}

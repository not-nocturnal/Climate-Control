
package climateControl.customGenLayer;
import climateControl.utils.Zeno410Logger;
import climateControl.genLayerPack.GenLayerPack;
import climateControl.utils.PlaneLocated;
import climateControl.utils.PlaneLocation;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import java.util.logging.Logger;

/**
 *
 * @author Zeno410
 */
public class GenLayerCache extends GenLayerPack{

    //public static Logger logger = new Zeno410Logger("Cache").logger();

    private PlaneLocated<Integer> storedVals  = new PlaneLocated<Integer>();
    private int excludeEdge = 4;

    public GenLayerCache(GenLayer parent) {
        super(0L);
        this.parent = parent;
    }

    public int [] getInts(int x0, int z0, int xSize, int zSize) {
        //logger.info("location " + x0 + " " + z0 + " " + xSize + " " + zSize);
        PlaneLocation.Probe probe = new PlaneLocation.Probe(x0,z0);
        int [] parentInts = null;
        int[] result = IntCache.getIntCache(xSize * zSize);
        for (int x=0; x<xSize;x++) {
            probe.setX(x+x0);
            for (int z = 0; z<zSize;z++) {
                probe.setZ(z+z0);
                Integer locked = storedVals.get(probe);
                if (locked == null) {
                    // not stored, get from parent
                    if (parentInts == null) {
                        parentInts = parent.getInts(x0, z0, xSize, zSize);
                    }
                    locked = parentInts[(z)*(xSize)+x];
                    result[(z)*xSize+x] = locked;
                    // and store for future reference
                    PlaneLocation location = new PlaneLocation(probe.x(),probe.z());
                    storedVals.put(location, locked);
                } else {
                    //logger.info("hit "+probe.toString()+locked.intValue());
                    // already stored
                    result[(z)*xSize+x] = locked;
                }
            }
        }
        return result;
    }

}

package climateControl.genLayerPack;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import com.google.common.collect.ObjectArrays;

public class GenLayerOneSixBiome extends GenLayer {

    /** this sets all the biomes that are allowed to appear in the overworld */
    private BiomeGenBase[] allowedBiomes;
    public static final BiomeGenBase[] base11Biomes = new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.forest,
        BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.plains, BiomeGenBase.coldTaiga };
    public static final BiomeGenBase[] base12Biomes = ObjectArrays.concat(base11Biomes, BiomeGenBase.jungle);

    public GenLayerOneSixBiome(long par1, GenLayer par3GenLayer, WorldType par4WorldType) {
        super(par1);
        // this.allowedBiomes = par4WorldType.;
        allowedBiomes = base12Biomes;
        this.parent = par3GenLayer;

    }

    /**
     * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall
     * amounts, or biomeList[] indices based on the particular GenLayer subclass.
     */
    public int[] getInts(int par1, int par2, int par3, int par4) {
        int[] aint = this.parent.getInts(par1, par2, par3, par4);
        int[] aint1 = IntCache.getIntCache(par3 * par4);

        for (int i1 = 0; i1 < par4; ++i1) {
            for (int j1 = 0; j1 < par3; ++j1) {
                this.initChunkSeed((long) (j1 + par1), (long) (i1 + par2));
                int k1 = aint[j1 + i1 * par3];

                if (k1 == 0) {
                    aint1[j1 + i1 * par3] = 0;
                } else if (k1 == BiomeGenBase.mushroomIsland.biomeID) {
                    aint1[j1 + i1 * par3] = k1;
                } else if (k1 == 1) {
                    aint1[j1 + i1 * par3] = this.allowedBiomes[this.nextInt(this.allowedBiomes.length)].biomeID;
                } else {
                    int l1 = this.allowedBiomes[this.nextInt(this.allowedBiomes.length)].biomeID;

                    if (l1 == BiomeGenBase.coldTaiga.biomeID) {
                        aint1[j1 + i1 * par3] = l1;
                    } else {
                        aint1[j1 + i1 * par3] = BiomeGenBase.icePlains.biomeID;
                    }
                }
            }
        }

        return aint1;
    }
}


package climateControl.customGenLayer;

import climateControl.api.ClimateControlRules;
import climateControl.genLayerPack.GenLayerPack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenJungle;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerShoreCC extends GenLayerPack
{
    private static final String __OBFID = "CL_00000568";
    private final ClimateControlRules rules;

    public GenLayerShoreCC(long par1, GenLayer par3GenLayer, ClimateControlRules rules)
    {
        super(par1);
        this.parent = par3GenLayer;
        this.rules = rules;
    }
    private boolean waterBiome(int biomeID) {
        if (isOceanic(biomeID)) return true;
        return rules.noBeachesAllowed(biomeID);
    }
    /**
     * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall
     * amounts, or biomeList[] indices based on the particular GenLayer subclass.
     */
    public int[] getInts(int par1, int par2, int par3, int par4)
    {
        int[] aint = this.parent.getInts(par1 - 1, par2 - 1, par3 + 2, par4 + 2);
        int[] aint1 = IntCache.getIntCache(par3 * par4);

        for (int i1 = 0; i1 < par4; ++i1){
            for (int j1 = 0; j1 < par3; ++j1){
                this.initChunkSeed((long)(j1 + par1), (long)(i1 + par2));
                int k1 = aint[j1 + 1 + (i1 + 1) * (par3 + 2)];
                BiomeGenBase biomegenbase = BiomeGenBase.getBiome(k1);
                int l1;
                int i2;
                int j2;
                int k2;

                if (k1 == BiomeGenBase.mushroomIsland.biomeID) {
                    l1 = aint[j1 + 1 + (i1 + 1 - 1) * (par3 + 2)];
                    i2 = aint[j1 + 1 + 1 + (i1 + 1) * (par3 + 2)];
                    j2 = aint[j1 + 1 - 1 + (i1 + 1) * (par3 + 2)];
                    k2 = aint[j1 + 1 + (i1 + 1 + 1) * (par3 + 2)];

                    if (l1 != BiomeGenBase.ocean.biomeID && i2 != BiomeGenBase.ocean.biomeID && j2 != BiomeGenBase.ocean.biomeID && k2 != BiomeGenBase.ocean.biomeID)
                    {
                        aint1[j1 + i1 * par3] = k1;
                    }
                    else
                    {
                        aint1[j1 + i1 * par3] = BiomeGenBase.mushroomIslandShore.biomeID;
                    }
                }
                else if (biomegenbase != null && biomegenbase.biomeID >20&& biomegenbase.biomeID <24 ) //jungle
                {
                    l1 = aint[j1 + 1 + (i1 + 1 - 1) * (par3 + 2)];
                    i2 = aint[j1 + 1 + 1 + (i1 + 1) * (par3 + 2)];
                    j2 = aint[j1 + 1 - 1 + (i1 + 1) * (par3 + 2)];
                    k2 = aint[j1 + 1 + (i1 + 1 + 1) * (par3 + 2)];

                    if (this.func_151631_c(l1) && this.func_151631_c(i2) && this.func_151631_c(j2) && this.func_151631_c(k2))
                    {
                        if (!waterBiome(l1) && !waterBiome(i2) && !waterBiome(j2) && !waterBiome(k2))
                        {
                            aint1[j1 + i1 * par3] = k1;
                        }
                        else
                        {
                            aint1[j1 + i1 * par3] = BiomeGenBase.beach.biomeID;
                        }
                    }
                    else
                    {
                        aint1[j1 + i1 * par3] = BiomeGenBase.jungleEdge.biomeID;
                    }
                }
                else if (k1 != BiomeGenBase.extremeHills.biomeID && k1 != BiomeGenBase.extremeHillsPlus.biomeID && k1 != BiomeGenBase.extremeHillsEdge.biomeID)
                {
                    if ((biomegenbase != null )&& biomegenbase.func_150559_j())
                    {
                        this.func_151632_a(aint, aint1, j1, i1, par3, k1, BiomeGenBase.coldBeach.biomeID);
                    }
                    else if (k1 != BiomeGenBase.mesa.biomeID && k1 != BiomeGenBase.mesaPlateau_F.biomeID)
                    {
                        if (!waterBiome(k1))
                        {
                            l1 = aint[j1 + 1 + (i1 + 1 - 1) * (par3 + 2)];
                            i2 = aint[j1 + 1 + 1 + (i1 + 1) * (par3 + 2)];
                            j2 = aint[j1 + 1 - 1 + (i1 + 1) * (par3 + 2)];
                            k2 = aint[j1 + 1 + (i1 + 1 + 1) * (par3 + 2)];

                            if (!waterBiome(l1) && !waterBiome(i2) && !waterBiome(j2) && !waterBiome(k2))
                            {
                                aint1[j1 + i1 * par3] = k1;
                            }
                            else
                            {
                                aint1[j1 + i1 * par3] = BiomeGenBase.beach.biomeID;
                            }
                        }
                        else
                        {
                            aint1[j1 + i1 * par3] = k1;
                        }
                    }
                    else
                    {
                        l1 = aint[j1 + 1 + (i1 + 1 - 1) * (par3 + 2)];
                        i2 = aint[j1 + 1 + 1 + (i1 + 1) * (par3 + 2)];
                        j2 = aint[j1 + 1 - 1 + (i1 + 1) * (par3 + 2)];
                        k2 = aint[j1 + 1 + (i1 + 1 + 1) * (par3 + 2)];

                        if (!waterBiome(l1) && !waterBiome(i2) && !waterBiome(j2) && !waterBiome(k2))
                        {
                            if (this.func_151633_d(l1) && this.func_151633_d(i2) && this.func_151633_d(j2) && this.func_151633_d(k2))
                            {
                                aint1[j1 + i1 * par3] = k1;
                            }
                            else
                            {
                                aint1[j1 + i1 * par3] = BiomeGenBase.desert.biomeID;
                            }
                        }
                        else
                        {
                            aint1[j1 + i1 * par3] = k1;
                        }
                    }
                }
                else {
                    this.func_151632_a(aint, aint1, j1, i1, par3, k1, BiomeGenBase.stoneBeach.biomeID);
                }
            }
        }

        return aint1;
    }

    private void func_151632_a(int[] parentVals, int[] resultVals, int p_151632_3_, int p_151632_4_, int p_151632_5_, int originalBiome, int replacementBeach)
    {
        if (waterBiome(originalBiome))
        {
            resultVals[p_151632_3_ + p_151632_4_ * p_151632_5_] = originalBiome;
        }
        else
        {
            int j1 = parentVals[p_151632_3_ + 1 + (p_151632_4_ + 1 - 1) * (p_151632_5_ + 2)];
            int k1 = parentVals[p_151632_3_ + 1 + 1 + (p_151632_4_ + 1) * (p_151632_5_ + 2)];
            int l1 = parentVals[p_151632_3_ + 1 - 1 + (p_151632_4_ + 1) * (p_151632_5_ + 2)];
            int i2 = parentVals[p_151632_3_ + 1 + (p_151632_4_ + 1 + 1) * (p_151632_5_ + 2)];

            if (!waterBiome(j1) && !waterBiome(k1) && !waterBiome(l1) && !waterBiome(i2))
            {
                resultVals[p_151632_3_ + p_151632_4_ * p_151632_5_] = originalBiome;
            }
            else
            {
                resultVals[p_151632_3_ + p_151632_4_ * p_151632_5_] = replacementBeach;
            }
        }
    }

    private boolean func_151631_c(int p_151631_1_)
    {
        return BiomeGenBase.getBiome(p_151631_1_) != null && BiomeGenBase.getBiome(p_151631_1_).getBiomeClass() == BiomeGenJungle.class ? true : p_151631_1_ == BiomeGenBase.jungleEdge.biomeID || p_151631_1_ == BiomeGenBase.jungle.biomeID || p_151631_1_ == BiomeGenBase.jungleHills.biomeID || p_151631_1_ == BiomeGenBase.forest.biomeID || p_151631_1_ == BiomeGenBase.taiga.biomeID || isOceanic(p_151631_1_);
    }

    private boolean func_151633_d(int p_151633_1_){
        return BiomeGenBase.getBiome(p_151633_1_) != null && BiomeGenBase.getBiome(p_151633_1_).biomeID > 36&& BiomeGenBase.getBiome(p_151633_1_).biomeID > 40;
    }
}
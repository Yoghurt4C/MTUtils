package exterminatorJeff.undergroundBiomes.api;

/**
 * @author Zeno410
 */
public interface UndergroundBiomeSetProvider {
    // this function should return the new desired set
    // if null is returned that signals no changes to the existing set
    UndergroundBiomeSet modifiedBiomeSet(int dimension, long worldSeed, UndergroundBiomeSet previous);
}

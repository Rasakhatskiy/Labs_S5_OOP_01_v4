package lab.v4.coffee

class QualityRange(
    private var priceRange:  Pair<Double, Double> = Pair<Double, Double>(0.0, 0.0),
    private var weightRange: Pair<Double, Double> = Pair<Double, Double>(0.0, 0.0),
    private var volumeRange: Pair<Double, Double> = Pair<Double, Double>(0.0, 0.0)
) {
    fun Contains(product: IProduct) : Boolean {
        if (priceRange.first   > 0 && product.price  < priceRange.first) return false;
        if (priceRange.second  > 0 && product.price  > priceRange.second) return false;
        if (weightRange.first  > 0 && product.weight < weightRange.first) return false;
        if (weightRange.second > 0 && product.weight > weightRange.second) return false;
        if (volumeRange.first  > 0 && product.volume < volumeRange.first) return false;
        if (volumeRange.second > 0 && product.volume > volumeRange.second) return false;
        return true;
    }
}
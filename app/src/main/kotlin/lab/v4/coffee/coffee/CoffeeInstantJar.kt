package lab.v4.coffee.coffee

import lab.v4.coffee.coffee.Coffee

class CoffeeInstantJar(
    price:  Double,
    weight: Double,
    volume: Double,
    sort:   String,
    private var jarMaterial: String,
    private var lidMaterial: String,
    private var isPromo: Boolean
) : Coffee(price, weight, volume, sort) {
    override fun toString(): String {
        var result = super<Coffee>.toString()
        result = "Instant coffee in jar. $result"
        result += if (isPromo) "Promo" else "Regular"
        result += " package, jar material: $jarMaterial, lid material: $lidMaterial."
        return result;
    }
}
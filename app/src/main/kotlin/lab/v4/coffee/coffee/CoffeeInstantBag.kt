package app.coffee

import lab.v4.coffee.coffee.Coffee

class CoffeeInstantBag(
    price:  Double,
    weight: Double,
    volume: Double,
    sort:   String,
    private var sugar: Double,
    private var cream: Double,
    private var isDouble: Boolean
) : Coffee(price, weight, volume, sort) {
    override fun toString(): String {
        var result = super<Coffee>.toString()
        result = "Instant coffee in bag. $result"
        result += if (isDouble) "Double" else "Single"
        result += " portion, sugar: $sugar%, cream: $cream%."
        return result;
    }


}
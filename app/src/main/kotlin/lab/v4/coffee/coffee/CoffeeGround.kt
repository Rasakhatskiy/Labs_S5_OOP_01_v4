package lab.v4.coffee.coffee

import lab.v4.coffee.coffee.Coffee

class CoffeeGround(
    price:  Double,
    weight: Double,
    volume: Double,
    sort:   String,
    private var groundLevel: Int,
    private var isVacuumed: Boolean
) : Coffee(price, weight, volume, sort) {
    override fun toString(): String {
        var result = super<Coffee>.toString()
        result = "Ground cofee. $result"
        result += if (isVacuumed) "Vacuumed" else "Not vacuumed"
        result += ", Ground level: $groundLevel."
        return result;
    }
}
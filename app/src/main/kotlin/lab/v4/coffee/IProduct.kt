package lab.v4.coffee

interface IProduct {
    var price:  Double
    var weight: Double
    var volume: Double
    override fun toString() : String
}
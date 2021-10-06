package lab.v4.coffee

interface IVan {
    var products: MutableList<IProduct>
    var volume: Double
    fun sortProducts()
    fun loadProducts(toLoad: List<IProduct>)
    fun clear()
    override fun toString() : String
    fun findProduct(range: QualityRange) : IProduct?
    fun loadFromDB()
}
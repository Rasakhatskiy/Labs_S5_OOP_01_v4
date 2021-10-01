package lab.v4.coffee

import io.github.cdimascio.dotenv.dotenv
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException


class Van : IVan {
    override var products: MutableList<IProduct>
    override var volume: Double

    constructor(products: MutableList<IProduct>, volume: Double) {
        this.products = mutableListOf()
        this.volume = volume
        loadProducts(products)
    }

    override fun sortProducts() {
        products = products.sortedBy { it.price / it.weight }.toMutableList()
    }

    override fun loadProducts(toLoad: List<IProduct>) {
        clear()
        val stonks = toLoad.sortedByDescending { it.price / it.volume }
        var volumeLeft = volume
        stonks.forEach { it ->
            if(volumeLeft >= it.volume)
            {
                products.add(it)
                volumeLeft -= it.volume
            }
        }
    }

    override fun clear() {
        products.clear()
    }

    override fun toString() : String {
        var result = "lab.v4.coffee.Van. Volume: $volume. Products:\n"
        products.forEach{ it ->
            result += "\t${it.toString()}\n\n"
        }
        return result
    }

    override fun findProduct(range: QualityRange): IProduct? {
        products.forEach { it ->
            if (range.Contains(it))
                return it
        }
        return null
    }

    override fun loadFromDB(): List<IProduct>? {
        println("Testing connection to PostgreSQL JDBC");
        try {
            Class.forName("org.postgresql.Driver")
        } catch (e: ClassNotFoundException) {
            println("PostgreSQL JDBC Driver is not found. Include it in your library path ")
            e.printStackTrace()
            return null
        }

        println("PostgreSQL JDBC Driver successfully connected")
        var connection: Connection?

        val dotenv = dotenv()
        val DB_URL = dotenv["DB_URL"]
        val USER = dotenv["USER"]
        val PASS = dotenv["PASS"]

        connection = try {
            DriverManager
                .getConnection(DB_URL, USER, PASS)
        } catch (e: SQLException) {
            println("Connection Failed")
            e.printStackTrace()
            return null
        }

        if (connection != null) {
            println("You successfully connected to database now")
        } else {
            println("Failed to make connection to database")
        }

        return null
    }

}
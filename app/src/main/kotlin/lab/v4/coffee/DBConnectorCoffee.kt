package lab.v4.coffee

import app.coffee.CoffeeInstantBag
import io.github.cdimascio.dotenv.dotenv
import lab.v4.coffee.coffee.Coffee
import lab.v4.coffee.coffee.CoffeeBeans
import lab.v4.coffee.coffee.CoffeeGround
import lab.v4.coffee.coffee.CoffeeInstantJar
import java.lang.RuntimeException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

class DBConnectorCoffee {
    private var connection: Connection? = null
    private var url: String? = null
    private var user: String? = null
    private var password: String? = null

    init {
        println("Testing driver to PostgreSQL JDBC")
        try {
            Class.forName("org.postgresql.Driver")
            println("\tDriver connected")
        } catch (e: ClassNotFoundException) {
            throw RuntimeException("PostgreSQL JDBC Driver is not found", e)
        }
        val dotenv = dotenv()
        url = dotenv["DB_URL"]
        user = dotenv["USER"]
        password = dotenv["PASS"]
        connection = try {
            DriverManager.getConnection(url, user, password)
        } catch (e: SQLException) {
            println(e.stackTrace)
            throw RuntimeException("Connection failed", e)
        }
        if (connection != null) {
            println("Successfully connected to database")
        } else {
            throw RuntimeException("Failed to make connection to database")
        }
    }

    fun getCoffee(): List<Coffee> {
        return concatenateCoffeeLists(
            queryCoffeeBean(),
            queryCoffeeGround(),
            queryCoffeeInstantBag(),
            queryCoffeeInstantJar()
        )
    }

    private fun queryCoffeeBean(): List<CoffeeBeans> {
        val resultSet = executeQuery("SELECT * FROM coffee_van_schema.coffee_bean;")
        val list = mutableListOf<CoffeeBeans>()
        while (resultSet!!.next()) {
            list.add(
                CoffeeBeans(
                    resultSet.getString("price").toDouble(),
                    resultSet.getString("weight").toDouble(),
                    resultSet.getString("volume").toDouble(),
                    resultSet.getString("sort"),
                    resultSet.getString("bean_caliber").toDouble(),
                    resultSet.getString("is_roasted") == "t"
                )
            )
        }
        return list.toList()
    }

    private fun queryCoffeeGround(): List<CoffeeGround> {
        val resultSet = executeQuery("SELECT * FROM coffee_van_schema.coffee_ground")
        val list = mutableListOf<CoffeeGround>()
        while (resultSet!!.next()) {
            list.add(
                CoffeeGround(
                    resultSet.getString("price").toDouble(),
                    resultSet.getString("weight").toDouble(),
                    resultSet.getString("volume").toDouble(),
                    resultSet.getString("sort"),
                    resultSet.getString("ground_level").toInt(),
                    resultSet.getString("is_vacuumed") == "t"
                )
            )
        }
        return list.toList()
    }

    private fun queryCoffeeInstantBag(): List<CoffeeInstantBag> {
        val resultSet = executeQuery("SELECT * FROM coffee_van_schema.coffee_instant_bag;")
        val list = mutableListOf<CoffeeInstantBag>()
        while (resultSet!!.next()) {
            list.add(
                CoffeeInstantBag(
                    resultSet.getString("price").toDouble(),
                    resultSet.getString("weight").toDouble(),
                    resultSet.getString("volume").toDouble(),
                    resultSet.getString("sort"),
                    resultSet.getString("sugar").toDouble(),
                    resultSet.getString("cream").toDouble(),
                    resultSet.getString("is_double") == "t"
                )
            )
        }
        return list.toList()
    }

    private fun queryCoffeeInstantJar(): List<CoffeeInstantJar> {
        val resultSet = executeQuery("SELECT * FROM coffee_van_schema.coffee_instant_jar;")
        val list = mutableListOf<CoffeeInstantJar>()
        while (resultSet!!.next()) {
            list.add(
                CoffeeInstantJar(
                    resultSet.getString("price").toDouble(),
                    resultSet.getString("weight").toDouble(),
                    resultSet.getString("volume").toDouble(),
                    resultSet.getString("sort"),
                    resultSet.getString("jar_material"),
                    resultSet.getString("lid_material"),
                    resultSet.getString("is_promo") == "t"
                )
            )
        }
        return list.toList()
    }

    private fun executeQuery(query: String): ResultSet? {
        val statement = connection!!.createStatement()
        return statement!!.executeQuery(query)
    }

    private fun concatenateCoffeeLists(vararg lists: List<Coffee>): List<Coffee> {
        val result: MutableList<Coffee> = ArrayList()
        for (list in lists) {
            result.addAll(list)
        }
        return result
    }
}
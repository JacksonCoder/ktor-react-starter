package me.andrewda.models

import com.google.gson.annotations.Expose
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

data class NewItem(
    @Expose val name: String?,
    @Expose val image: String?,
    @Expose val price: Double?,
    @Expose val inventory: Int?
) {
    val isValid get() = name != null && price != null
}

object Items : IntIdTable() {
    val name = varchar("name", 50)
    val image = blob("image").nullable()
    val price = double("price")
    val inventory = integer("inventory").default(0).nullable()
}

class Item(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Item>(Items) {
        data class ApiItem(
            @Expose val id: Int,
            @Expose val name: String,
            @Expose val image: String?,
            @Expose val price: Double
        )

        data class ApiAdminItem(
            @Expose val id: Int,
            @Expose val name: String,
            @Expose val image: String?,
            @Expose val price: Double,
            @Expose val inventory: Int?
        )
    }

    var name by Items.name
    var image by Items.image
    var price by Items.price
    var inventory by Items.inventory

    val api get() = ApiItem(id.value, name, image?.toString(), price)
    val apiAdmin get() = ApiAdminItem(id.value, name, image?.toString(), price, inventory)
}

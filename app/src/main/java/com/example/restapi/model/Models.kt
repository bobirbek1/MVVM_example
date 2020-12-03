package com.example.restapi.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(tableName = "post")
data class Post(
    @PrimaryKey
    @SerializedName("id") val id: Int = -1,
    @SerializedName("userId") val userId: Int = -1,
    @SerializedName("title") val title: String = "",
    @SerializedName("body") val body: String = ""
)

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val id: Int = -1,
    val name: String = "",
    val username: String = "",
    val email: String = "",
    @TypeConverters(AddressConverter::class)
    val address: Address? = null,
    val phone: String = "",
    val website: String = "",
    @TypeConverters(CompanyConverter::class)
    val company: Company?
)

//@Entity(tableName = "comments")
//data class ListComments(
//    @TypeConverters(CommentsConverter::class)
//    val comments:List<Comment>
//)

//@Entity(tableName = "company")
data class Company(
    val name: String = "",
    val catchPhrase: String = "",
    val bs: String = ""
)

//@Entity(tableName = "address")
data class Address(
    val street: String = "",
    val suite: String = "",
    val city: String = "",
    val zipcode: String = ""
)

@Entity(tableName = "comment")
data class Comment(
    val postId: Int = -1,
    @PrimaryKey
    val id: Int = -1,
    val name: String = "",
    val email: String = "",
    val body: String = ""
)

class CompanyConverter {

    private val gson = Gson()

    @TypeConverter
    fun objectToString(company: Company?): String? {
        return gson.toJson(company)
    }

    @TypeConverter
    fun stringToObject(string: String): Company? {
        return gson.fromJson(string, Company::class.java)
    }
}

class AddressConverter {

    private val gson = Gson()

    @TypeConverter
    fun objectToString(address: Address?): String? {
        return gson.toJson(address)
    }

    @TypeConverter
    fun stringToObject(string: String): Address? {
        return gson.fromJson(string, Address::class.java)
    }
}

class CommentsConverter {

    private val gson = Gson()

    @TypeConverter
    fun objectToString(comments:List<Comment>): String? {
        return gson.toJson(comments)
    }

    @TypeConverter
    fun stringToObject(string: String): List<Comment>? {
        val type = object : TypeToken<List<Comment>>() {}.type
        return gson.fromJson<List<Comment>>(string,type)
    }
}
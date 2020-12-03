package com.example.restapi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.restapi.model.*

@Database(entities = [Post::class, User::class, Comment::class], version = 1, exportSchema = false,)
@TypeConverters(AddressConverter::class, CompanyConverter::class)
abstract class MyRoomDatabase : RoomDatabase() {

    abstract fun dao(): MyDao

    companion object {
        private var INSTANCE: MyRoomDatabase? = null

        fun getDatabase(context: Context): MyRoomDatabase {

            if (INSTANCE != null) {
                return INSTANCE!!
            }

            synchronized(this) {
//                val migration_1_2 = object : Migration(1,2){
//                    override fun migrate(database: SupportSQLiteDatabase) {
//                        database.execSQL("Delete from comment")
//                    }
//                }
                INSTANCE = Room.databaseBuilder(context, MyRoomDatabase::class.java, "example")
//                    .addMigrations(migration_1_2)
                    .build()
                return INSTANCE!!
            }
        }
    }

}
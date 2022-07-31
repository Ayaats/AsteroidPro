package com.udacity.asteroidradar.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DbEntities::class], version = 1)
abstract class RoomDb : RoomDatabase() {
    abstract val Dao: RoomDao
}
private lateinit var INSTANCE: RoomDb

fun getDatabase(context: Context): RoomDb {
    synchronized(RoomDb::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                RoomDb::class.java,
                "aster").build()
        }
    }


    return INSTANCE
}
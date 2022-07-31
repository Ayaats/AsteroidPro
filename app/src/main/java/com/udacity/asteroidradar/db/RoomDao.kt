package com.udacity.asteroidradar.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RoomDao {
    @Query("select * from aster")
    fun getAsteroids(): LiveData<List<DbEntities>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DbEntities)
    @Query("SELECT * FROM aster WHERE closeApproachDate = :s ")
    fun getToday(s: String): LiveData<List<DbEntities>>
    @Query("SELECT * FROM aster WHERE closeApproachDate BETWEEN :s AND :e")
    fun getWeek(s: String, e: String): LiveData<List<DbEntities>>

}

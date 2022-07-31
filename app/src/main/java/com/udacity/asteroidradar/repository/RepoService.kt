package com.udacity.asteroidradar.repository


import android.util.Log
import androidx.lifecycle.map
import com.example.android.marsrealestate.network.AsterApi
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.Constants.end
import com.udacity.asteroidradar.Constants.start
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.asDomainModel
import com.udacity.asteroidradar.db.RoomDb
import com.udacity.asteroidradar.db.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.format.DateTimeFormatter

class RepoService (private val Db : RoomDb){


    val allAsteroids  = Db.Dao.getAsteroids().map { it.asDomainModel() }
    val today=Db.Dao.getToday(start.format(DateTimeFormatter.ISO_DATE)).map { it.asDomainModel() }
    val week=Db.Dao.getWeek(end.format(DateTimeFormatter.ISO_DATE), start.format(DateTimeFormatter.ISO_DATE)).map { it.asDomainModel() }

    suspend fun refreshData() {
        withContext(Dispatchers.IO) {
            try {
                val asteroid = AsterApi.retrofitService.getAsters(API_KEY)
                Log.i("AAA",asteroid)
                val asteroidDB = parseAsteroidsJsonResult(JSONObject(asteroid))
                Db.Dao.insertAll(*asteroidDB.asDatabaseModel() )
                Log.i("AAAAAA",Db.Dao.getAsteroids().toString())

            } catch (err: Exception) {
            }
        }
    }
}
package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.android.marsrealestate.network.AsterApi
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.asDomainModel
import com.udacity.asteroidradar.db.DbEntities
import com.udacity.asteroidradar.db.getDatabase
import com.udacity.asteroidradar.repository.RepoService
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val database = getDatabase(app)
    private val Repository = RepoService(database)
   //for pic
    private val _pic = MutableLiveData<PictureOfDay>()
    val pic: LiveData<PictureOfDay>
        get() = _pic
    //for returned data

    val filterAster=MutableLiveData(filter.all_Aster)

    val returnedAsteroids:LiveData<List<Asteroid>> = Transformations.switchMap(filterAster){
        when(it){
        filter.all_Aster->Repository.allAsteroids
        filter.today_Aster->Repository.week
        else->Repository.today
    }}


    fun filterChanged(filterX: filter) {
        filterAster.postValue(filterX)
    }


    private val _ToDetails = MutableLiveData<Asteroid?>()
    val ToDetails: LiveData<Asteroid?>
        get() = _ToDetails

    fun onClicked(aster: Asteroid) {
        _ToDetails.value = aster
    }

    fun onNavigated() {
        _ToDetails.value = null
    }


    init {
        refreshPictureOfDay()
        viewModelScope.launch {
            Repository.refreshData()
           // Log.i("AAAA",allAsteroids.toString())
        }



    }

    private fun refreshPictureOfDay() {
        viewModelScope.launch {
            try {
                _pic.value = AsterApi.retrofitService.getPicOfDay(API_KEY)
               // Log.i("AAA",allAsteroids.value.toString())

            } catch (err: Exception) {
            }
        }
    }
}
package com.example.android.hilt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.hilt.data.Car
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor() : ViewModel() {
    // ViewModel logic here

        // Define LiveData to hold shared data
        private val carLiveData = MutableLiveData<Car>()
        private var caridLiveData = MutableLiveData<Long?>()
        private var updateCarLiveData = MutableLiveData(false)

        // Function to set the shared data
        fun setCarData(car: Car) {
            carLiveData.value = car
        }
        fun setUpdateCar(flag: Boolean) {
            updateCarLiveData.value = flag
        }
        fun getUpdateCar() = updateCarLiveData.value!!

        // Function to get the shared data as LiveData
        fun getCarData(): LiveData<Car> {
            return carLiveData
        }

    // Function to get the shared data as LiveData
    fun getCar(): Car? {

        return carLiveData.value

    }

    fun setCarid(carId: Long?) {
        caridLiveData.value = carId
    }

    // Function to get the shared data as LiveData
    fun getCarid(): LiveData<Long?> {
        return caridLiveData
    }

    fun getCarId(): Long? {

        return caridLiveData.value

    }


}
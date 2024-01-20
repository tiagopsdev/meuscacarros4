package com.example.android.hilt.data

import androidx.room.Delete
import androidx.room.Query

interface CarDataSource {

    fun addCar(car: Car)
    fun getAllCars(callback: (List<Car>) -> Unit)
    fun removeCars()
    fun findCarById(id: Long, callback: (Car?) -> Unit)
    fun deleteCar(car: Car, callback: (Int) -> Unit)


}
package com.example.android.hilt

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.hilt.data.AppDatabase
import com.example.android.hilt.data.CarLocalDataSource
import com.example.android.hilt.di.CarDbModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import javax.inject.Inject

@HiltAndroidTest
//@UninstallModules(CarDbModule::class)
@RunWith(AndroidJUnit4::class)
class CarLocalDataSourceTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var carLocalDataSource: CarLocalDataSource

    private lateinit var appDatabase: AppDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        carLocalDataSource = CarLocalDataSource(appDatabase.carDao())
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        appDatabase.close()
    }

    @Test
    fun testAddCar() {
        val carMap = mapOf(
            "carName" to "Car 1",
            "kmActual" to "1000",
            "kmOilChange" to "5000",
            "kmWheels" to "3000",
            "kmBreakes" to "2000",
            "kmFilters" to "10000"
        )

        carLocalDataSource.addCar(carMap)

        // Retrieve cars and assert that the added car is in the list
        carLocalDataSource.getAllCars { cars ->
            assert(cars.isNotEmpty())
            val addedCar = cars.first()
            assert(addedCar.carName == "Car 1")
        }
    }

    @Test
    fun testGetAllCars() {
        // Add some cars to the database
        val carMap1 = mapOf("carName" to "Car 1", "kmActual" to "1000", "kmOilChange" to "5000", "kmWheels" to "3000", "kmBreakes" to "2000", "kmFilters" to "10000")
        val carMap2 = mapOf("carName" to "Car 2", "kmActual" to "2000", "kmOilChange" to "6000", "kmWheels" to "4000", "kmBreakes" to "3000", "kmFilters" to "11000")
        val carMap3 = mapOf("carName" to "Car 3", "kmActual" to "3000", "kmOilChange" to "7000", "kmWheels" to "5000", "kmBreakes" to "4000", "kmFilters" to "12000")
        carLocalDataSource.addCar(carMap1)
        carLocalDataSource.addCar(carMap2)
        carLocalDataSource.addCar(carMap3)

        // Retrieve all cars and assert the expected count
        carLocalDataSource.getAllCars { cars ->
            assert(cars.size == 3)
        }
    }

    @Test
    fun testFindCarById() {
        // Add a car to the database
        val carMap = mapOf("carName" to "Car 1", "kmActual" to "1000", "kmOilChange" to "5000", "kmWheels" to "3000", "kmBreakes" to "2000", "kmFilters" to "10000")
        carLocalDataSource.addCar(carMap)

        // Find the car by ID and assert its properties
        carLocalDataSource.findCarById(1) { car ->
            assert(car != null)
            assert(car?.carName == "Car 1")
        }
    }

    @Test
    fun testDeleteCar() {
        // Add a car to the database
        val carMap = mapOf("carName" to "Car 1", "kmActual" to "1000", "kmOilChange" to "5000", "kmWheels" to "3000", "kmBreakes" to "2000", "kmFilters" to "10000")
        carLocalDataSource.addCar(carMap)

        // Find and delete the car
        carLocalDataSource.findCarById(1) { car ->
            car?.let {
                carLocalDataSource.deleteCar(it) { result ->
                    assert(result == 1)
                }
            }
        }
    }
}
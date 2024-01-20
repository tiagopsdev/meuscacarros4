package com.example.android.hilt

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.hilt.data.AppDatabase
import com.example.android.hilt.data.Car
import com.example.android.hilt.data.CarLocalDataSource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
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
        val testCar = Car(
            "Car 1",
            1000,
            5000,
            3000,
            2000,
            10000
        )

        carLocalDataSource.addCar(testCar)

        // Retrieve cars and assert that the added car is in the list
        carLocalDataSource.getAllCars { cars ->
            assert(cars.isNotEmpty())
            val addedCar = cars.first()
            assert(addedCar.carName == "Car 1")
        }
    }

    @Test
    fun testGetAllCars() {
        // Add some cars to the databa
        val testCar1 = Car("Car 1", 1000,5000,3000, 2000,10000)
        val testCar2 = Car("Car 2", 2000,6000,4000, 3000,11000)
        val testCar3 = Car("Car 3", 3000,7000,5000, 4000,12000)
        carLocalDataSource.addCar(testCar1)
        carLocalDataSource.addCar(testCar2)
        carLocalDataSource.addCar(testCar3)

        // Retrieve all cars and assert the expected count
        carLocalDataSource.getAllCars { cars ->
            assert(cars.size == 3)
        }
    }

    @Test
    fun testFindCarById() {
        // Add a car to the database
        val testCar = Car("Car 1", 1000,5000,3000,2000,10000)
        carLocalDataSource.addCar(testCar)

        // Find the car by ID and assert its properties
        carLocalDataSource.findCarById(1) { car ->
            assert(car != null)
            assert(car?.carName == "Car 1")
        }
    }

    @Test
    fun testDeleteCar() {
        // Add a car to the database
        val testCar = Car("Car 1", 1000,5000,3000,2000,10000)
        carLocalDataSource.addCar(testCar)

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
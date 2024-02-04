/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.hilt.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.android.hilt.R
import com.example.android.hilt.analytics.logCreateOrUpdateCar
import com.example.android.hilt.analytics.logListCars
import com.example.android.hilt.data.Car
import com.example.android.hilt.data.CarDataSource
import com.example.android.hilt.di.CarDbModule.DatabaseCar
import com.example.android.hilt.navigator.AppNavigator
import com.example.android.hilt.navigator.Screens
import com.example.android.hilt.util.DateFormatter
import com.example.android.hilt.util.FirebaseAnalyticsProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Fragment that displays the database logs.
 */
@AndroidEntryPoint
class CarsFragment : Fragment() {

    //@Inject
    //lateinit var logger: CarLocalDataSource
    @DatabaseCar
    @Inject
    lateinit var carDataSource: CarDataSource
    @Inject
    lateinit var dateFormatter: DateFormatter

    @Inject
    lateinit var navigator: AppNavigator

    private lateinit var recyclerView: RecyclerView

    private val firebaseAnalytics = FirebaseAnalyticsProvider()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cars, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
        }
        firebaseAnalytics.firebaseAnalytics(requireContext()).logListCars()

        

    }

    /*override fun onAttach(context: Context) {
        super.onAttach(context)

        populateFields(context)
    }

    private fun populateFields(context: Context) {
        logger = (context.applicationContext as CarAndServiceApplication).serviceLocator.loggerLocalDataSource
        dateFormatter =
            (context.applicationContext as CarAndServiceApplication).serviceLocator.provideDateFormatter()
    }*/

    override fun onResume() {
        super.onResume()

        carDataSource.getAllCars { cars ->
            recyclerView.adapter =
                CarsViewAdapter(
                    cars
                ){ car ->
                    // Handle the click event for the selected car here
                    // You can access the selected car using the 'car' parameter
                    // For example, you can display a toast with car details:
                    val carDetails = "${car.id} - ${car.carName}"
                    Toast.makeText(requireContext(), carDetails, Toast.LENGTH_SHORT).show()
                    navigator.navigateTo(Screens.UPDATECAR, car)

                }
        }
    }
}

/**
 * RecyclerView adapter for the logs list.
 */
private class CarsViewAdapter(
    private val carsDataSet: List<Car>,
    private val onItemClick: (Car) -> Unit
    //private val daterFormatter: DateFormatter
) : RecyclerView.Adapter<CarsViewAdapter.CarsViewHolder>() {

    class CarsViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        return CarsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.car_row_item, parent, false)
        )
    }



    override fun getItemCount(): Int {
        return carsDataSet.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        val car = carsDataSet[position]
        holder.view.findViewById<TextView>(R.id.text).text =
            "${car.id} - " + "${car.carName}"
        holder.itemView.setOnClickListener {
            onItemClick(car) // Handle the click event here
        }

    }
}

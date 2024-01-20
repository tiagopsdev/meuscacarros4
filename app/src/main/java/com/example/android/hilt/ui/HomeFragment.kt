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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.android.hilt.R
import com.example.android.hilt.data.CarDataSource
import com.example.android.hilt.di.CarDbModule.DatabaseCar
import com.example.android.hilt.navigator.AppNavigator
import com.example.android.hilt.navigator.Screens
import com.example.android.hilt.util.DateFormatter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * Fragment that displays buttons whose interactions are recorded.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    //@Inject
    //lateinit var logger: CarLocalDataSource
    @DatabaseCar
    @Inject
    lateinit var carDataSource: CarDataSource
    @Inject
    lateinit var navigator: AppNavigator
    @Inject
    lateinit var dateFormatter: DateFormatter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    /*override fun onAttach(context: Context) {
        super.onAttach(context)

        populateFields(context)
    }*/

   /* private fun populateFields(context: Context) {
        logger = (context.applicationContext as CarAndServiceApplication).
            serviceLocator.loggerLocalDataSource

        navigator = (context.applicationContext as CarAndServiceApplication).
            serviceLocator.provideNavigator(requireActivity())
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*view.findViewById<Button>(R.id.btnListCarsGarage).setOnClickListener {
            carDataSource.addCar(
                mapOf("carName" to "Value1", "carLicensPlate" to "Value2")
            )
        }*/

        view.findViewById<Button>(R.id.btnListCarsGarage).setOnClickListener {
            navigator.navigateTo(Screens.CARS)
        }
        view.findViewById<Button>(R.id.btnAddCar).setOnClickListener {
            navigator.navigateTo(Screens.`ADDCAR`)
        }
        view.findViewById<Button>(R.id.btnConfig).setOnClickListener {
            navigator.navigateTo(Screens.CONFIGURATION)
        }

        view.findViewById<Button>(R.id.delete_cars).setOnClickListener {
            carDataSource.removeCars()
        }
    }
}

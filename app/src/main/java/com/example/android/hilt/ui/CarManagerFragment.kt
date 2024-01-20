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
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.android.hilt.R
import com.example.android.hilt.data.Car
import com.example.android.hilt.data.CarDataSource
import com.example.android.hilt.di.CarDbModule.DatabaseCar
import com.example.android.hilt.navigator.AppNavigator
import com.example.android.hilt.util.CarConverter
import com.example.android.hilt.viewmodel.CarViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * Fragment that displays buttons whose interactions are recorded.
 */
@AndroidEntryPoint
class CarManagerFragment(private var carParam: Car? = null) : Fragment() {

    //@Inject
    //lateinit var logger: CarLocalDataSource
    @DatabaseCar
    @Inject
    lateinit var carDataSource: CarDataSource

    @Inject
    lateinit var navigator: AppNavigator

    @Inject
    lateinit var carConverter: CarConverter
    val carViewModel: CarViewModel by activityViewModels()

    lateinit  var car: Car


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_car_manenger, container, false)
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


        /* Fields */

        val carNameEditText = view.findViewById<EditText>(R.id.extCarName)
        val actualKmEditText = view.findViewById<EditText>(R.id.extActualKM)
        val oilChangeKmEditText = view.findViewById<EditText>(R.id.extOilChange)
        val wheelsKmEditText = view.findViewById<EditText>(R.id.extWheels)
        val brakesKmEditText = view.findViewById<EditText>(R.id.extBreakes)
        val filtersKmEditText = view.findViewById<EditText>(R.id.extFilters)

        //
        if (carParam != null) {

            car = carParam as Car
            if (car != null){

                carViewModel.setCarData(car!!)

                carNameEditText.setText(car.carName)
                actualKmEditText.setText(car.kmActual.toString())
                oilChangeKmEditText.setText(car.kmOilChange.toString())
                wheelsKmEditText.setText(car.kmWheels.toString())
                brakesKmEditText.setText(car.kmBreakes.toString())
                filtersKmEditText.setText(car.kmFilters.toString())

            }

        }else{

            car = Car("",0,0,0,0,0)
            car = getCarFromForm(view)

        }


        //


        // Add focus change listeners for each EditText field
        carNameEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                carViewModel.setCarData(getCarFromForm(view))
            }
        }

        actualKmEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                carViewModel.setCarData(getCarFromForm(view))
            }
        }

        oilChangeKmEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                carViewModel.setCarData(getCarFromForm(view))
            }
        }

        wheelsKmEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                carViewModel.setCarData(getCarFromForm(view))
            }
        }

        brakesKmEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                carViewModel.setCarData(getCarFromForm(view))
            }
        }

        filtersKmEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {

                carViewModel.setCarData(getCarFromForm(view))
            }
        }


        view.findViewById<Button>(R.id.save_car).setOnClickListener {
            val carToAdd = getCarFromForm(view)
            if (carToAdd.carName != "")
                carDataSource.addCar(carToAdd)
        }


        // Define an array of EditText fields
        val editTextFields = arrayOf(
            carNameEditText,
            actualKmEditText,
            oilChangeKmEditText,
            wheelsKmEditText,
            brakesKmEditText,
            filtersKmEditText
        )

        // Add a common focus change listener to all EditText fields

        for (editText in editTextFields) {
            //
            carNameEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    carViewModel.setCarData(getCarFromForm(view))
                }
                //
            })
        }


        //////////////////////////////////


    }

    private fun getCarFromForm(view: View): Car {


        val carName = view.findViewById<EditText>(R.id.extCarName).text.toString()
        val actualKm =
            view.findViewById<EditText>(R.id.extActualKM).text.toString().toIntOrNull() ?: 0
        val oilChangeKm =
            view.findViewById<EditText>(R.id.extOilChange).text.toString().toIntOrNull() ?: 0
        val wheelsKm =
            view.findViewById<EditText>(R.id.extWheels).text.toString().toIntOrNull() ?: 0
        val brakesKm =
            view.findViewById<EditText>(R.id.extBreakes).text.toString().toIntOrNull() ?: 0
        val filtersKm =
            view.findViewById<EditText>(R.id.extFilters).text.toString().toIntOrNull() ?: 0


        car.carName = carName
        car.kmActual = actualKm
        car.kmOilChange = oilChangeKm
        car.kmWheels = wheelsKm
        car.kmBreakes = brakesKm
        car.kmFilters = filtersKm

        return car

    }

    private fun getIdCar(intParam: Int?): Long? {


        if (intParam != null) {

            return intParam.toLong()

        }else{

            return null
        }




    }
}



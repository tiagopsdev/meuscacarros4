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
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.example.android.hilt.R
import com.example.android.hilt.data.Configuration
import com.example.android.hilt.data.ConfigurationDataSource
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
class ConfigurationManagerFragment() : Fragment() {

    //@Inject
    //lateinit var logger: CarLocalDataSource
    @DatabaseCar
    @Inject
    lateinit var configurationDataSource: ConfigurationDataSource

    @Inject
    lateinit var navigator: AppNavigator

    //@Inject
    //lateinit var carConverter: CarConverter
    //val carViewModel: CarViewModel by activityViewModels()

    lateinit  var config: Configuration


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_configuration_manenger, container, false)
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



        //
        configurationDataSource.getAllConfigurations { it ->
            if (it.isEmpty() || it.first() == null){
                config = Configuration(0,0,0,0)
                config = getConfigFromForm(view)
            }else{
                config = it.first()

            }


        }




        view.findViewById<Button>(R.id.save_configuration).setOnClickListener {
            val configToAdd = getConfigFromForm(view)
            configurationDataSource.addConfiguration(configToAdd)
        }

    }

    private fun getConfigFromForm(view: View): Configuration {


        val ext1 = view.findViewById<AppCompatEditText>(R.id.extOilKMToCheck)
        val str1 = ext1.text.toString()
        val num1 = str1.toIntOrNull() ?: 0
        config.kmToOilChangeCheck = num1

        val ext2 = view.findViewById<AppCompatEditText>(R.id.extBreakesKMToCheck)
        val str2 = ext2.text.toString()
        val num2 = str2.toIntOrNull() ?: 0
        config.kmToBreakesCheck = num2

        val ext3 = view.findViewById<AppCompatEditText>(R.id.extFiltersKMToCheck)
        val str3 = ext3.text.toString()
        val num3 = str3.toIntOrNull() ?: 0
        config.kmToFiltersCheck = num3

        //val wheelsKmToCheck = view.findViewById<AppCompatEditText>(R.id.extWheelsKMToCheck).text.toString().toIntOrNull() ?: 0
        //val brakesKmToCheck = view.findViewById<AppCompatEditText>(R.id.extBreakesKMToCheck).text.toString().toIntOrNull() ?: 0
        //val filtersKmToCheck = view.findViewById<AppCompatEditText>(R.id.extFiltersKMToCheck).text.toString().toIntOrNull() ?: 0

            return config

    }




}



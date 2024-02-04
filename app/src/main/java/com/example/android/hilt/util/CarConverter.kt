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

package com.example.android.hilt.util

import com.example.android.hilt.data.Car
import javax.inject.Inject

/**
 * Convert Car to Map and and Map " to car.
 */
class CarConverter @Inject constructor(){




    fun toMap(car: Car): Map<String, String> {
        return mapOf(

            "carName" to car.carName,
            "carModel" to car.carModel,
            "kmActual" to car.kmActual.toString(),
            "kmOilChange" to car.kmOilChange.toString(),
            "kmWheels" to car.kmWheels.toString(),
            "kmBreakes" to car.kmBreakes.toString(),
            "kmFilters" to car.kmFilters.toString(),
        )
    }

    fun fromMap(map: Map<String, String>): Car {
        return Car(

            map["carName"].toString(),
            map["carModel"].toString(),
            map["kmActual"]?.toInt()!!,
            map["kmOilChange"]?.toInt()!!,
            map["kmWheels"]?.toInt()!!,
            map["kmBreakes"]?.toInt()!!,
            map["kmFilters"]?.toInt()!!,
        )
    }
}

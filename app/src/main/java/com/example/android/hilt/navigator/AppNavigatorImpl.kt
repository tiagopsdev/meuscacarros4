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

package com.example.android.hilt.navigator

import androidx.fragment.app.FragmentActivity
import com.example.android.hilt.R
import com.example.android.hilt.data.Car
import com.example.android.hilt.ui.CarManagerFragment
import com.example.android.hilt.ui.HomeFragment
import com.example.android.hilt.ui.CarsFragment
import com.example.android.hilt.ui.ConfigurationManagerFragment
import javax.inject.Inject

/**
 * Navigator implementation.
 */
class AppNavigatorImpl @Inject constructor(private val activity: FragmentActivity) : AppNavigator {

    override fun navigateTo(screen: Screens, param: Car?) {
        val fragment = when (screen) {
            Screens.HOME -> HomeFragment()
            Screens.CARS -> CarsFragment()
            Screens.ADDCAR -> CarManagerFragment()
            Screens.UPDATECAR -> CarManagerFragment(param)
            Screens.CONFIGURATION -> ConfigurationManagerFragment()
        }

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .addToBackStack(fragment::class.java.canonicalName)
            .commit()
    }
}

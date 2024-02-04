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
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.android.hilt.R
import com.example.android.hilt.analytics.logOpenAPP
import com.example.android.hilt.data.CarDataSource
import com.example.android.hilt.di.CarDbModule
import com.example.android.hilt.navigator.AppNavigator
import com.example.android.hilt.navigator.Screens
import com.example.android.hilt.util.CarConverter
import com.example.android.hilt.util.FirebaseAnalyticsProvider
import com.example.android.hilt.viewmodel.CarViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Main activity of the application.
 *
 * Container for the Buttons & Logs fragments. This activity simply tracks clicks on buttons.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @CarDbModule.DatabaseCar
    @Inject
    lateinit var carDataSource: CarDataSource
    @Inject
    lateinit var navigator: AppNavigator
    @Inject
    lateinit var carConverter: CarConverter


    private var firebaseAnalytics = FirebaseAnalyticsProvider()


    val carViewModel: CarViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)



        //navigator = (applicationContext as CarAndServiceApplication).serviceLocator.provideNavigator(this)
        firebaseAnalytics.firebaseAnalytics(applicationContext).logOpenAPP()
        if (savedInstanceState == null) {
            navigator.navigateTo(Screens.HOME)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()

        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_appbar, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //TODO(Create a work menu)
        return true
    }
    fun showmessage(msg: String = "Default message"){


        Toast.makeText(this, msg, Toast.LENGTH_LONG)

    }

}

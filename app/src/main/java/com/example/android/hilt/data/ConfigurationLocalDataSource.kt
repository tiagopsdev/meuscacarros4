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

package com.example.android.hilt.data

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data manager class that handles data manipulation between the database and the UI.
 */
@Singleton
class ConfigurationLocalDataSource @Inject constructor (private val configdao: ConfigurationDao) : ConfigurationDataSource {

    private val executorService: ExecutorService = Executors.newFixedThreadPool(4)
    private val mainThreadHandler by lazy {
        Handler(Looper.getMainLooper())
    }

    override fun addConfiguration(config: Configuration) {

        executorService.execute {
            configdao.insertAll(config)

        }
    }

    override fun getAllConfigurations(callback: (List<Configuration>) -> Unit) {
        executorService.execute {
            val configs = configdao.getAll()
            mainThreadHandler.post { callback(configs) }
        }
    }

    override fun removeConfigurations() {
        executorService.execute {
            configdao.nukeTable()
        }
    }

    override fun findConfigurationById(id: Long, callback: (Configuration?) -> Unit) {

        executorService.execute {
            val config = configdao.findById(id)
            mainThreadHandler.post { callback(config) }
        }

    }

    override fun deleteConfiguration(config: Configuration, callback: (Int) -> Unit) {

        executorService.execute {
            val result = configdao.delete(config)
            mainThreadHandler.post { callback(result) }
        }

    }


}

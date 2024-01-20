package com.example.android.hilt.data

import androidx.room.Delete
import androidx.room.Query

interface ConfigurationDataSource {

    fun addConfiguration(config: Configuration)
    fun getAllConfigurations(callback: (List<Configuration>) -> Unit)
    fun removeConfigurations()
    fun findConfigurationById(id: Long, callback: (Configuration?) -> Unit)
    fun deleteConfiguration(config: Configuration, callback: (Int) -> Unit)


}
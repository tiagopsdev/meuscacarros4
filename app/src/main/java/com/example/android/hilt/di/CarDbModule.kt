package com.example.android.hilt.di

//import com.example.android.hilt.data.LoggerInMemoryDataSource
import com.example.android.hilt.data.CarDataSource
import com.example.android.hilt.data.CarLocalDataSource
import com.example.android.hilt.data.Configuration
import com.example.android.hilt.data.ConfigurationDataSource
import com.example.android.hilt.data.ConfigurationLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

class CarDbModule {


    @Qualifier
    annotation class DatabaseCar

    @InstallIn(SingletonComponent::class)
    @Module
    abstract class LoggingDatabaseModule {

        @DatabaseCar
        @Singleton
        @Binds
        abstract fun bindDatabaseLogger(impl: CarLocalDataSource): CarDataSource

        @DatabaseCar
        @Singleton
        @Binds
        abstract fun bindDatabaseConfiguration(impl: ConfigurationLocalDataSource): ConfigurationDataSource
    }


}
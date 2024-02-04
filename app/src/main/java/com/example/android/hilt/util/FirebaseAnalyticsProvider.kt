package com.example.android.hilt.util

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


class FirebaseAnalyticsProvider {


    fun firebaseAnalytics(@ApplicationContext appContext: Context): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(appContext)
    }

}
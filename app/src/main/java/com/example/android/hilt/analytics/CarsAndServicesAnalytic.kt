package com.example.android.hilt.analytics


import android.util.Log
import com.example.android.hilt.util.MyLogs
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent


private const val EVENT_CREATE_CAR = "create_car"
private const val EVENT_DELETE_ALL_CARS = "delete_all_cars"
private const val EVENT_UPDATE_CAR = "update_car"
private const val EVENT_LIST_CARS = "list_car"


private const val PARAM_CAR_NAME = "car_name"
private const val PARAM_CAR_MODEL = "car_model"
private const val PARAM_TIME_ADDorUPDATE = "time_add_or_update"


fun FirebaseAnalytics.logOpenAPP() = logEvent(FirebaseAnalytics.Event.APP_OPEN, null)

fun FirebaseAnalytics.logCreateOrUpdateCar(
    carModel: String, update: Boolean, strclass: String, timeSec: Long
) {
    Log.i(
        MyLogs.TAG,
        "carModel ${carModel}, update: ${update},strclass: ${strclass},timeSec: ${timeSec} "
    )
    var event = if (update) {

        EVENT_UPDATE_CAR

    } else {

        EVENT_CREATE_CAR


    }

    //
    logEvent(event) {
        param(PARAM_CAR_MODEL, carModel.lowercase())
        param(PARAM_TIME_ADDorUPDATE, timeSec)

    }



}
fun FirebaseAnalytics.logListCars(){

    logEvent(EVENT_LIST_CARS, null)
}
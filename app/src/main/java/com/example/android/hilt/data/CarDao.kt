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

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * Data access object to query the database.
 */
@Dao
interface CarDao {

    @Query("SELECT * FROM cars ORDER BY id DESC")
    fun getAll(): List<Car>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg car: Car)

    @Query("DELETE FROM cars")
    fun nukeTable()

    @Query("SELECT * FROM cars WHERE id == :id")
    fun findById(id: Long): Car?

    @Delete
    fun delete(car: Car): Int





}

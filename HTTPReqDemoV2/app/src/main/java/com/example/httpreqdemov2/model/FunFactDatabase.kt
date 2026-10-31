package com.example.httpreqdemov2.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Fun fact database
 *
 * By Ryan Pointer 10/30/26
 */
@Database(
    entities = [FunFact::class],
    version = 1,
    exportSchema = false
)
abstract class FunFactDatabase : RoomDatabase() {

    abstract fun funFactDao(): FunFactDao
}
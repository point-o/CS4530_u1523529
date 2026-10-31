package com.example.httpreqdemov2

import android.app.Application
import androidx.room.Room
import com.example.httpreqdemov2.data.ApiClient
import com.example.httpreqdemov2.data.FunFactDatabase
import com.example.httpreqdemov2.data.FunFactRepository

/**
 * Lazily initializes the Room database, Ktor HTTP client,
 * and singleton Repository.
 *
 * By Ryan Pointer
 * 10/30/26
 */
class FunFactsApplication : Application() {

    val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            FunFactDatabase::class.java,
            "fun_facts_database"
        ).build()
    }

    val httpClient by lazy {
        ApiClient.client
    }

    val repository by lazy {
        FunFactRepository.getInstance(
            database.funFactDao(),
            httpClient
        )
    }
}
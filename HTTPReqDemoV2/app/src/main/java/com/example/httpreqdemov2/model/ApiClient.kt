package com.example.httpreqdemov2.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Configures the singleton Ktor HTTP client.
 *
 * By Ryan Pointer
 * 10/30/26
 */
object ApiClient {

    val client = HttpClient(Android) {

        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
}
package com.example.httpreqdemov2.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow

/**
 * Repository layer
 *
 * By Ryan Pointer
 * 10/30/26
 */
class FunFactRepository private constructor(
    private val dao: FunFactDao,
    private val client: HttpClient
) {

    val allFacts: Flow<List<FunFact>> = dao.getAllFacts()

    suspend fun fetchAndSaveFact() {

        val response: FunFactResponse = client.get(
            "https://uselessfacts.jsph.pl/random.json?language=en"
        ).body()

        val fact = FunFact(text = response.text)

        dao.insertFact(fact)
    }

    companion object {
        @Volatile
        private var INSTANCE: FunFactRepository? = null

        fun getInstance(
            dao: FunFactDao,
            client: HttpClient
        ): FunFactRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: FunFactRepository(dao, client).also {
                    INSTANCE = it
                }
            }
        }
    }
}
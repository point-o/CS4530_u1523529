package com.example.httpreqdemov2.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * DAO for the Room database.
 *
 * By Ryan Pointer
 * 10/30/26
 */
@Dao
interface FunFactDao {

    @Insert
    suspend fun insertFact(fact: FunFact)

    @Query("SELECT * FROM fun_facts ORDER BY id DESC")
    fun getAllFacts(): Flow<List<FunFact>>
}
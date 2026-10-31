package com.example.httpreqdemov2.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * FunFactResponse represents JSON received from the API.
 * FunFact represents a locally stored fact in the Room database,
 * with an automatically generated primary key.
 * By Ryan Pointer
 * 10/30/26
 */
@Serializable
data class FunFactResponse(
    val text: String
)

@Entity(tableName = "fun_facts")
data class FunFact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String
)
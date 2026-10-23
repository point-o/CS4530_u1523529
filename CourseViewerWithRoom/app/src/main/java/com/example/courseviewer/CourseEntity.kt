package com.example.courseviewer

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val department: String,
    val number: String,
    val location: String
)
package com.example.courseviewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object CourseViewModelFactory : ViewModelProvider.Factory {

    lateinit var repository: CourseRepository

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(CourseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CourseViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
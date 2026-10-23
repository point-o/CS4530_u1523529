package com.example.courseviewer

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CourseViewModel(
    private val repository: CourseRepository
) : ViewModel() {

    val courses = repository.getAllCourses()

    private var nextId = 0

    fun addCourse(
        course: CourseEntity
    ) {
        viewModelScope.launch {
            repository.insert(course)
        }
    }

    fun deleteCourse(course: CourseEntity) {
        viewModelScope.launch {
            repository.delete(course)
        }
    }

    fun updateCourse(course: CourseEntity) {
        viewModelScope.launch {
            repository.update(course)
        }
    }

}
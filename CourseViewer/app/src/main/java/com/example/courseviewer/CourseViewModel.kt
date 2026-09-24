package com.example.courseviewer

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class CourseViewModel : ViewModel() {

    val courses = mutableStateListOf<Course>()

    private var nextId = 0

    fun addCourse(
        department: String,
        number: String,
        location: String
    ) {
        courses.add(
            Course(
                id = nextId++,
                department = department,
                number = number,
                location = location
            )
        )
    }

    fun deleteCourse(course: Course) {
        courses.remove(course)
    }

    fun updateCourse(updatedCourse: Course) {
        val index = courses.indexOfFirst {
            it.id == updatedCourse.id
        }

        if (index != -1) {
            courses[index] = updatedCourse
        }
    }

}
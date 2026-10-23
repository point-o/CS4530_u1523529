package com.example.courseviewer

class CourseRepository(
    private val dao: CourseDao
) {

    fun getAllCourses() = dao.getAllCourses()

    suspend fun insert(course: CourseEntity) {
        dao.insertCourse(course)
    }

    suspend fun update(course: CourseEntity) {
        dao.updateCourse(course)
    }

    suspend fun delete(course: CourseEntity) {
        dao.deleteCourse(course)
    }
}
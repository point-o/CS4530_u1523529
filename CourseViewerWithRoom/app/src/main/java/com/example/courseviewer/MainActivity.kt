package com.example.courseviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = CourseDatabase.getDatabase(applicationContext)

        CourseViewModelFactory.repository =
            CourseRepository(database.courseDao())

        setContent {
            val courseViewModel: CourseViewModel =
                viewModel(factory = CourseViewModelFactory)

            CourseApp(courseViewModel)
        }
    }
}
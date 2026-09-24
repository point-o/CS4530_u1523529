
package com.example.courseviewer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseApp(viewModel: CourseViewModel) {

    // Input field state
    var department by rememberSaveable {
        mutableStateOf("")
    }

    var number by rememberSaveable {
        mutableStateOf("")
    }

    var location by rememberSaveable {
        mutableStateOf("")
    }

    // Currently selected course
    var selectedCourse by remember {
        mutableStateOf<Course?>(null)
    }

    var editingCourse by remember {
        mutableStateOf<Course?>(null)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("My Courses")
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // ADD COURSE FORM
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = if (editingCourse == null) {
                            "Add a Course"
                        } else {
                            "Edit Course"
                        },
                        style = MaterialTheme.typography.titleLarge
                    )

                    OutlinedTextField(
                        value = department,
                        onValueChange = {
                            department = it
                        },
                        label = {
                            Text("Department")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = number,
                        onValueChange = {
                            number = it
                        },
                        label = {
                            Text("Course Number")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = location,
                        onValueChange = {
                            location = it
                        },
                        label = {
                            Text("Location")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {

                            if (editingCourse == null) {

                                // Create a new course
                                viewModel.addCourse(
                                    department,
                                    number,
                                    location
                                )

                            } else {

                                // Update the existing course
                                viewModel.updateCourse(
                                    editingCourse!!.copy(
                                        department = department,
                                        number = number,
                                        location = location
                                    )
                                )
                            }

                            // Reset the form
                            department = ""
                            number = ""
                            location = ""

                            editingCourse = null
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = department.isNotBlank() &&
                                number.isNotBlank() &&
                                location.isNotBlank()
                    ) {
                        Text("Save Course")
                    }
                }
            }

            // COURSE LIST HEADER
            Text(
                text = "Saved Courses",
                style = MaterialTheme.typography.titleLarge
            )

            // COURSE LIST
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),

                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(
                    items = viewModel.courses,
                    key = { course -> course.id }
                ) { course ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedCourse = course
                            }
                    ) {

                        Text(
                            text = "${course.department} ${course.number}",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }

    // COURSE DETAILS DIALOG
    selectedCourse?.let { course ->

        AlertDialog(
            onDismissRequest = {
                selectedCourse = null
            },

            title = {
                Text("${course.department} ${course.number}")
            },

            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Department: ${course.department}")
                    Text("Number: ${course.number}")
                    Text("Location: ${course.location}")
                }
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteCourse(course)
                        selectedCourse = null
                    }
                ) {
                    Text("Delete")
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        editingCourse = course

                        department = course.department
                        number = course.number
                        location = course.location

                        selectedCourse = null
                    }
                ) {
                    Text("Edit")
                }
            }
        )
    }
}

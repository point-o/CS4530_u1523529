
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
import androidx.compose.runtime.collectAsState
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

    val courses by viewModel.courses.collectAsState(
        initial = emptyList()
    )

    // Form fields survive rotation.
    var department by rememberSaveable {
        mutableStateOf("")
    }

    var number by rememberSaveable {
        mutableStateOf("")
    }

    var location by rememberSaveable {
        mutableStateOf("")
    }

    // Store IDs rather than entire entities so selection
    // and editing survive configuration changes.
    var selectedCourseId by rememberSaveable {
        mutableStateOf<Int?>(null)
    }

    var editingCourseId by rememberSaveable {
        mutableStateOf<Int?>(null)
    }

    val selectedCourse = courses.find {
        it.id == selectedCourseId
    }

    // Capture the original entity before editing.
    // It lets us preserve the primary key during updates.
    var editingCourse by remember {
        mutableStateOf<CourseEntity?>(null)
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

            // ADD / EDIT COURSE FORM
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = if (editingCourseId == null) {
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
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = number,
                        onValueChange = {
                            number = it
                        },
                        label = {
                            Text("Course Number")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = location,
                        onValueChange = {
                            location = it
                        },
                        label = {
                            Text("Location")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Button(
                        onClick = {
                            if (editingCourseId == null) {
                                // Add a new course.
                                viewModel.addCourse(
                                    CourseEntity(
                                        department = department.trim(),
                                        number = number.trim(),
                                        location = location.trim()
                                    )
                                )
                            } else {
                                // Update the existing course,
                                // preserving its primary key.
                                val original = editingCourse
                                    ?: courses.find {
                                        it.id == editingCourseId
                                    }

                                original?.let { course ->
                                    viewModel.updateCourse(
                                        course.copy(
                                            department = department.trim(),
                                            number = number.trim(),
                                            location = location.trim()
                                        )
                                    )
                                }
                            }

                            // Reset form.
                            department = ""
                            number = ""
                            location = ""
                            editingCourseId = null
                            editingCourse = null
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = department.isNotBlank() &&
                                number.isNotBlank() &&
                                location.isNotBlank() &&
                                (
                                        editingCourseId == null ||
                                                courses.any {
                                                    it.id == editingCourseId
                                                }
                                        )
                    ) {
                        Text(
                            if (editingCourseId == null) {
                                "Add Course"
                            } else {
                                "Save Changes"
                            }
                        )
                    }

                    if (editingCourseId != null) {
                        TextButton(
                            onClick = {
                                editingCourseId = null
                                editingCourse = null
                                department = ""
                                number = ""
                                location = ""
                            }
                        ) {
                            Text("Cancel Editing")
                        }
                    }
                }
            }

            // COURSE LIST HEADER
            Text(
                text = "Saved Courses",
                style = MaterialTheme.typography.titleLarge
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = courses,
                    key = { course -> course.id }
                ) { course ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedCourseId = course.id
                            }
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "${course.department} ${course.number}",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = course.location,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }

    // COURSE DETAILS DIALOG
    selectedCourse?.let { course ->

        AlertDialog(
            onDismissRequest = {
                selectedCourseId = null
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

                        // Exit editing if this course was being edited.
                        if (editingCourseId == course.id) {
                            editingCourseId = null
                            editingCourse = null
                            department = ""
                            number = ""
                            location = ""
                        }

                        selectedCourseId = null
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        editingCourseId = course.id
                        editingCourse = course

                        department = course.department
                        number = course.number
                        location = course.location

                        selectedCourseId = null
                    }
                ) {
                    Text("Edit")
                }
            }
        )
    }
}

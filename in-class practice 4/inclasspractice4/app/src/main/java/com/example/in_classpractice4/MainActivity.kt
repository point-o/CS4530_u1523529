package com.example.in_classpractice4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.in_classpractice4.ui.theme.InclassPractice4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InclassPractice4Theme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    var first by remember { mutableStateOf("") }
    var second by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            OutlinedTextField(
                value = first,
                onValueChange = { first = it },
                label = { Text("first") },
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = second,
                onValueChange = { second = it },
                label = { Text("second") },
                modifier = Modifier.weight(1f)
            )
        }

        Button(
            onClick = {
                result = "$first $second"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }

        OutlinedTextField(
            value = result,
            onValueChange = { result = it },
            label = { Text("result") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
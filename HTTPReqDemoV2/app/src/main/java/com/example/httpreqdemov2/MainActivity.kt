package com.example.httpreqdemov2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.httpreqdemov2.ui.theme.HTTPReqDemoV2Theme
import com.example.httpreqdemov2.viewmodel.MyViewModel
import com.example.httpreqdemov2.viewmodel.MyViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as FunFactsApplication

        setContent {
            HTTPReqDemoV2Theme {

                val vm: MyViewModel = viewModel(
                    factory = MyViewModelFactory(app.repository)
                )

                FactsJPC(vm)
            }
        }
    }
}

@Composable
fun FactsJPC(myVM: MyViewModel) {

    val observableFacts by
    myVM.factsListReadOnly.collectAsStateWithLifecycle()

    val searchText by
    myVM.searchQuery.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                myVM.fetchItem()
            }
        ) {
            Text("Fetch Item")
        }

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = {
                myVM.updateSearch(it)
            },
            label = {
                Text("Search saved facts")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(20.dp))

        Text(
            "FunFact List",
            fontSize = 25.sp
        )

        Spacer(Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(
                items = observableFacts,
                key = { it.id }
            ) { fact ->

                Text(
                    text = fact.text,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                HorizontalDivider()
            }
        }
    }
}
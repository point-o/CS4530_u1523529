package com.example.httpreqdemov2.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.httpreqdemov2.data.FunFactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing the Fun Facts UI state.
 *
 * By Ryan Pointer
 * 10/30/26
 */
class MyViewModel(
    private val repository: FunFactRepository
) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    // combines observable list with current search query
    // recalcs the filtered list whenever either val changes
    // no additional network requests
    val factsListReadOnly = combine(
        repository.allFacts,
        searchQuery
    ) { facts, query ->

        if (query.isBlank()) {
            facts
        } else {
            facts.filter {
                it.text.contains(
                    query,
                    ignoreCase = true
                )
            }
        }

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun updateSearch(query: String) {
        searchQuery.value = query
    }

    fun fetchItem() {
        viewModelScope.launch {
            try {
                repository.fetchAndSaveFact()
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.CancellationException) {
                    throw e
                }
                Log.e("FunFact", "Error fetching fact", e)
            }
        }
    }
}
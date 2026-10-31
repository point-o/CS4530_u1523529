package com.example.httpreqdemov2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.httpreqdemov2.data.FunFactRepository

/**
 * Factory responsible for constructing MyViewModel.
 *
 * By Ryan Pointer
 * 10/30/26
 */
class MyViewModelFactory(
    private val repository: FunFactRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.example.roomcompose.Model

import MyGamesViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.roomcompose.Internal.MyGamesRepository

class MyGamesViewModelFactory(private val repository: MyGamesRepository) : ViewModelProvider.Factory {
    /**
     * Membuat instance dari ViewModel berdasarkan kelas yang diminta.
     *
     * @param modelClass Kelas ViewModel yang ingin dibuat.
     * @return Instance dari ViewModel yang diminta.
     * @throws IllegalArgumentException Jika kelas ViewModel tidak dikenal.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyGamesViewModel::class.java)) {
            class PurchasedGamesViewModelFactory(private val repository: PurchasedGamesRepo) : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                    if (modelClass.isAssignableFrom(PurchasedGamesViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return PurchasedGamesViewModel(repository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
            @Suppress("UNCHECKED_CAST")
            return MyGamesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


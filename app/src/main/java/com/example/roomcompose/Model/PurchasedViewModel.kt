package com.example.roomcompose.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.roomcompose.Object.PurchasedGame
import kotlinx.coroutines.launch

class PurchasedGamesViewModel(private val repository: PurchasedGamesRepo) : ViewModel() {
    val purchasedGames: LiveData<List<PurchasedGame>> = repository.purchasedGames.asLiveData()

    fun buyGame(game: PurchasedGame) {
        viewModelScope.launch {
            repository.buyGame(game)
        }
    }

    fun removeGame(game: PurchasedGame) {
        viewModelScope.launch {
            repository.removeGame(game)
        }
    }
}

class PurchasedGamesViewModelFactory(private val repository: PurchasedGamesRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PurchasedGamesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PurchasedGamesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}




package com.example.roomcompose.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.roomcompose.Object.PurchasedGame
import kotlinx.coroutines.launch

/**
 * ViewModel untuk mengelola operasi terkait game yang dibeli.
 * Class ini menyediakan akses ke data game yang dibeli dan operasi untuk membeli atau menghapus game.
 *
 * @property repository PurchasedGamesRepo repository yang menyediakan akses ke data game yang dibeli.
 */
class PurchasedGamesViewModel(private val repository: PurchasedGamesRepo) : ViewModel() {
    /**
     * LiveData yang berisi daftar game yang telah dibeli.
     * Data ini diambil dari repository dan dikonversi ke LiveData untuk observasi oleh UI.
     */
    val purchasedGames: LiveData<List<PurchasedGame>> = repository.purchasedGames.asLiveData()

    /**
     * Melakukan pembelian game baru.
     * Metode ini menambahkan game ke daftar game yang dibeli.
     *
     * @param game PurchasedGame objek game yang akan dibeli.
     */
    fun buyGame(game: PurchasedGame) {
        viewModelScope.launch {
            repository.buyGame(game)
        }
    }

    /**
     * Menghapus game dari daftar game yang dibeli.
     *
     * @param game PurchasedGame objek game yang akan dihapus.
     */
    fun removeGame(game: PurchasedGame) {
        viewModelScope.launch {
            repository.removeGame(game)
        }
    }
}

/**
 * Factory ini memungkinkan pembuatan ViewModel dengan repository yang diperlukan.
 */
class PurchasedGamesViewModelFactory(private val repository: PurchasedGamesRepo) : ViewModelProvider.Factory {
    /**
     * Membuat instance ViewModel dari kelas yang diberikan.
     *
     * @param modelClass Class<T> kelas ViewModel yang ingin dibuat.
     * @return T instance dari ViewModel yang diminta.
     * @throws IllegalArgumentException jika kelas yang diminta bukan PurchasedGamesViewModel.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PurchasedGamesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PurchasedGamesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}




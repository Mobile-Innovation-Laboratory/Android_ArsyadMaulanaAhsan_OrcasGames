package com.example.roomcompose.Model

import com.example.roomcompose.Object.PurchasedGame
import com.example.roomcompose.Object.PurchasedGameDao
import kotlinx.coroutines.flow.Flow

/**
 * Repository untuk mengelola operasi data game yang dibeli.
 * Class ini bertindak sebagai lapisan abstraksi antara ViewModel dan sumber data (DAO).
 *
 * @property dao PurchasedGameDao Data Access Object untuk operasi database pada game yang dibeli.
 */
class PurchasedGamesRepo(private val dao: PurchasedGameDao) {
    /**
     * Flow yang menyediakan daftar game yang telah dibeli.
     * Nilai ini terus diperbarui ketika ada perubahan dalam database.
     */
    val purchasedGames: Flow<List<PurchasedGame>> = dao.getPurchasedGames()

    /**
     * Menambahkan game baru ke dalam daftar game yang dibeli.
     *
     * @param game PurchasedGame objek game yang akan ditambahkan ke database.
     */
    suspend fun buyGame(game: PurchasedGame) {
        dao.insertPurchasedGame(game)
    }

    /**
     * Menghapus game dari daftar game yang dibeli.
     *
     * @param game PurchasedGame objek game yang berisi ID game yang akan dihapus.
     */
    suspend fun removeGame(game: PurchasedGame) {
        dao.deletePurchasedGame(game.id)
    }
}
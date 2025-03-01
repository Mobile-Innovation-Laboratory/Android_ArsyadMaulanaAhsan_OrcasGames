package com.example.roomcompose.Model

import com.example.roomcompose.Object.PurchasedGame
import com.example.roomcompose.Object.PurchasedGameDao
import kotlinx.coroutines.flow.Flow

class PurchasedGamesRepo(private val dao: PurchasedGameDao) {
    val purchasedGames: Flow<List<PurchasedGame>> = dao.getPurchasedGames()

    suspend fun buyGame(game: PurchasedGame) {
        dao.insertPurchasedGame(game)
    }

    suspend fun removeGame(game: PurchasedGame) {
        dao.deletePurchasedGame(game.id)
    }
}

package com.example.roomcompose.Object

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MyGamesDao {
    /**
     * Mengambil semua game yang tersimpan di database.
     *
     * @return Flow<List<Games>> yang akan diperbarui secara otomatis ketika data berubah.
     */
    @Query("SELECT * FROM gamesinfo")
    fun getAll(): Flow<List<Games>>

    /**
     * Menambahkan informasi game baru ke dalam database.
     *
     * @param game Objek game yang akan disimpan.
     * @return ID game yang baru dimasukkan.
     */
    @Insert
    suspend fun insertGamesInfo(game: Games): Long

    /**
     * Menghapus semua data game yang tersimpan dalam database.
     */
    @Query("DELETE FROM gamesinfo")
    suspend fun deleteAllGames()
}

@Dao
interface PurchasedGameDao {

    /**
     * Menyimpan game yang dibeli ke dalam database.
     * Jika game dengan ID yang sama sudah ada, akan menggantikannya (REPLACE).
     *
     * @param game PurchasedGame objek game yang akan disimpan.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPurchasedGame(game: PurchasedGame)

    /**
     * Mengambil semua game yang telah dibeli dari database.
     * Metode ini mengembalikan Flow yang akan mengemisikan nilai baru setiap kali
     * ada perubahan pada tabel purchased_games.
     *
     * @return Flow<List<PurchasedGame>> aliran daftar game yang dibeli.
     */
    @Query("SELECT * FROM purchased_games")
    fun getPurchasedGames(): Flow<List<PurchasedGame>>

    /**
     * Menghapus game yang dibeli berdasarkan ID.
     *
     * @param gameId String ID dari game yang akan dihapus.
     */
    @Query("DELETE FROM purchased_games WHERE id = :gameId")
    suspend fun deletePurchasedGame(gameId: String)
}

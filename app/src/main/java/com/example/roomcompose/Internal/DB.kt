package com.example.roomcompose.Internal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomcompose.Model.Gamee
import com.example.roomcompose.Object.Games
import com.example.roomcompose.Object.MyGamesDao
import com.example.roomcompose.Object.PurchasedGame
import com.example.roomcompose.Object.PurchasedGameDao
import com.example.roomcompose.utils.Game2

@Database(entities = [Games::class], version = 1)
abstract class GamesDB: RoomDatabase() {
    /**
     * Memberikan akses ke Data Access Object untuk entitas Games.
     *
     * @return MyGamesDao instance untuk melakukan operasi database pada entitas Games.
     */
    abstract fun GamesDao(): MyGamesDao

    companion object {
        /**
         * Instance singleton dari database.
         * Anotasi @Volatile memastikan bahwa perubahan pada Instance segera terlihat oleh thread lain.
         */
        @Volatile
        private var Instance: GamesDB? = null

        /**
         * Mendapatkan atau membuat instance database Games.
         * Implementasi singleton dengan double-checking lock untuk thread safety.
         *
         * @param context Context aplikasi yang digunakan untuk inisialisasi database.
         * @return GamesDB instance database yang dapat digunakan untuk operasi database.
         */
        fun getMyGamesDatabase(context: Context): GamesDB {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    klass = GamesDB::class.java,
                    name = "gamesinfo"
                )
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

@Database(entities = [PurchasedGame::class], version = 1, exportSchema = false)
abstract class PurchasedGameDB : RoomDatabase() {
    /**
     * Memberikan akses ke Data Access Object untuk entitas PurchasedGame.
     *
     * @return PurchasedGameDao instance untuk melakukan operasi database pada entitas PurchasedGame.
     */
    abstract fun purchasedGameDao(): PurchasedGameDao

    companion object {
        @Volatile
        private var INSTANCE: PurchasedGameDB? = null
        /**
         * Mendapatkan atau membuat instance database purchased games.
         * @param context Context aplikasi yang digunakan untuk inisialisasi database.
         * @return PurchasedGameDB instance database yang dapat digunakan untuk operasi database.
         */
        fun getPurchasedGamesDatabase(context: Context): PurchasedGameDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PurchasedGameDB::class.java,
                    "purchased_games"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}


package com.example.roomcompose.Object

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity yang merepresentasikan tabel `gamesinfo` dalam database.
 *
 * @property id ID unik game, dihasilkan secara otomatis.
 * @property name Nama game.
 * @property sales Jumlah penjualan game.
 * @property comments Komentar atau ulasan tentang game.
 * @property price Harga game.
 */
@Entity(tableName = "gamesinfo")
data class Games(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "sales")
    val sales: Int = 0,
    @ColumnInfo(name = "comments")
    val comments: String = "",
    @ColumnInfo(name = "price")
    val price: Int = 0
) {
    /**
     * Konstruktor tanpa argumen yang diperlukan oleh Firestore.
     */
    constructor() : this(0, "", 0, "", 0)
}

/**
 * Entitas yang merepresentasikan game yang telah dibeli oleh pengguna.
 * Class ini digunakan sebagai model data untuk penyimpanan dalam database Room.
 *
 * @property id String ID unik untuk mengidentifikasi game, digunakan sebagai primary key.
 * @property title String Judul atau nama game.
 * @property price Double Harga game dalam format desimal.
 * @property releaseDate String Tanggal rilis game dalam format string.
 * @property releaseYear Int Tahun rilis game.
 * @property imageRes Int Resource ID untuk gambar/icon game.
 * @property description String Deskripsi atau informasi tambahan tentang game. Default: string kosong.
 * @property purchaseDate Long Timestamp yang menunjukkan kapan game dibeli.
 *                          Default: waktu sistem saat objek dibuat.
 */
@Entity(tableName = "purchased_games")
data class PurchasedGame(
    @PrimaryKey val id: String,
    val title: String,
    val price: Double,
    val releaseDate: String,
    val releaseYear: Int,
    val imageRes: Int,
    val description: String = "",
    val purchaseDate: Long = System.currentTimeMillis() // Store when it was purchased
)


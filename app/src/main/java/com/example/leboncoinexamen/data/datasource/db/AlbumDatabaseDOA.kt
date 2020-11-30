package com.example.leboncoinexamen.data.datasource.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.leboncoinexamen.data.datasource.db.model.DbAlbum

@Dao
interface AlbumDatabaseDOA {
    /*@Insert
    fun insert(albums: Album)*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(albums: List<DbAlbum>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(night: List<DbAlbum>)

    /*@Update
    fun update(night: Album)*/

    @Query("DELETE FROM album_table")
    fun clear()

    @Query("SELECT * FROM album_table ORDER BY Id ASC")
    fun getAll(): LiveData<List<DbAlbum>>
}
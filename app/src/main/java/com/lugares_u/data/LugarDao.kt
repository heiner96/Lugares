package com.lugares_u.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lugares_u.model.Lugar

@Dao
interface LugarDao
{
    //CRUD
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLugar(lugar: Lugar)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateLugar(lugar: Lugar)

    @Delete
    suspend fun deleteLugar(lugar: Lugar)

    @Query("SELECT * FROM LUGAR")
    fun getLugares() : LiveData<List<Lugar>>

}
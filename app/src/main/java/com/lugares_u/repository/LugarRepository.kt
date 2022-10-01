package com.lugares_u.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lugares_u.data.LugarDao
import com.lugares_u.model.Lugar

class LugarRepository(private val lugarDao: LugarDao)
{

    suspend fun saveLugar(lugar: Lugar)
    {
        if(lugar.id==0){//lugar nuevo
            lugarDao.addLugar(lugar)
        }
        else{//lugar existe, se crea
            lugarDao.updateLugar(lugar)
        }

    }

    suspend fun deleteLugar(lugar: Lugar)
    {
        lugarDao.deleteLugar(lugar)
    }


    val getLugares : LiveData<List<Lugar>> =  lugarDao.getLugares()


}
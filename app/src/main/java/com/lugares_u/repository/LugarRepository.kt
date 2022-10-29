package com.lugares_u.repository

import androidx.lifecycle.MutableLiveData
import com.lugares_u.data.LugarDao
import com.lugares_u.model.Lugar

class LugarRepository(private val lugarDao: LugarDao)
{

    fun saveLugar(lugar: Lugar)
    {
        lugarDao.saveLugar(lugar)
    }

    fun deleteLugar(lugar: Lugar)
    {
        lugarDao.deleteLugar(lugar)
    }


    val getLugares : MutableLiveData<List<Lugar>> =  lugarDao.getLugares()


}
package com.lugares_u.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.lugares_u.data.LugarDataBase
import com.lugares_u.model.Lugar
import com.lugares_u.repository.LugarRepository
import kotlinx.coroutines.launch

class LugarViewModel(application: Application) : AndroidViewModel(application)
{
    private val repository : LugarRepository
    val getLugares : LiveData<List<Lugar>>

    init {
        val lugarDao = LugarDataBase.getDataBase(application).lugarDao()
        repository = LugarRepository(lugarDao)
        getLugares = repository.getLugares
    }

    suspend fun addLugar(lugar: Lugar)
    {
        viewModelScope.launch { repository.addLugar(lugar) }
    }


    suspend fun updateLugar(lugar: Lugar)
    {
        viewModelScope.launch { repository.updateLugar(lugar) }
    }


    suspend fun deleteLugar(lugar: Lugar)
    {
        viewModelScope.launch { repository.deleteLugar(lugar) }
    }






}
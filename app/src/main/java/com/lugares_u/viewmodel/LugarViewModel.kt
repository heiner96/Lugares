package com.lugares_u.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.lugares_u.data.LugarDao
import com.lugares_u.model.Lugar
import com.lugares_u.repository.LugarRepository
import kotlinx.coroutines.launch

class LugarViewModel(application: Application) : AndroidViewModel(application)
{
    private val repository : LugarRepository = LugarRepository(LugarDao())
    val getLugares : MutableLiveData<List<Lugar>>

    init {
        getLugares = repository.getLugares
    }

    fun saveLugar(lugar: Lugar)
    {
        viewModelScope.launch { repository.saveLugar(lugar) }
    }

    fun deleteLugar(lugar: Lugar)
    {
        viewModelScope.launch { repository.deleteLugar(lugar) }
    }






}
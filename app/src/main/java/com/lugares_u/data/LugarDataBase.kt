package com.lugares_u.data

import android.content.Context
import androidx.room.*
import com.lugares_u.model.Lugar

@Database(entities = [Lugar::class], version = 1, exportSchema = false)
abstract class LugarDataBase : RoomDatabase()
{
    abstract fun lugarDao() : LugarDao

    companion object{//variables globales y estaticas
        @Volatile
        private var INSTANCE: LugarDataBase? = null


        fun getDataBase(context: android.content.Context): LugarDataBase {
            val temp = INSTANCE
            if(temp != null){
                return temp
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LugarDataBase::class.java,
                    "lugar_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }

}
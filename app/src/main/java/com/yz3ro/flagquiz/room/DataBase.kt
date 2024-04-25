package com.yz3ro.flagquiz.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yz3ro.flagquiz.data.entity.Countries

@Database(entities = [Countries::class] ,version = 1)
abstract class DataBase : RoomDatabase(){
    abstract fun getCountryDao() : CountryDao

    companion object{
        var INSTANCE : DataBase? = null
        fun accessDatabase(context: Context) : DataBase? {
            if(INSTANCE== null){
                synchronized(DataBase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        DataBase::class.java,
                        "flagquiz.sqlite")
                        .createFromAsset("flagquiz.sqlite")
                        .build()
                }
            }
            return INSTANCE
        }
    }
}
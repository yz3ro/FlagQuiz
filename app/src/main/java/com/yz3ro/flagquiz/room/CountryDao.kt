package com.yz3ro.flagquiz.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yz3ro.flagquiz.data.entity.Countries

@Dao
interface CountryDao {
    @Query("SELECT * FROM counties")
    suspend fun GetAllCountry() : List<Countries>
    @Insert
    suspend fun addCountry(countries: List<Countries>)
}
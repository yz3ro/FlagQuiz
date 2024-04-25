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

    @Query("SELECT * FROM counties ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomCountry(): Countries

    @Query("SELECT * FROM counties WHERE country_name != :selectedCountryName ORDER BY RANDOM() LIMIT 3")
    suspend fun getThreeRandomCountryNames(selectedCountryName: String): List<Countries>
}
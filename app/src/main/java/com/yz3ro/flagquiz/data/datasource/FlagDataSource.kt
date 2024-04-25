package com.yz3ro.flagquiz.data.datasource

import androidx.lifecycle.LiveData
import com.yz3ro.flagquiz.data.entity.Countries
import com.yz3ro.flagquiz.data.entity.Country
import com.yz3ro.flagquiz.data.entity.CountryInfo
import com.yz3ro.flagquiz.data.retrofit.FlagAPI
import com.yz3ro.flagquiz.room.CountryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FlagDataSource(private val fapi : FlagAPI,var cdao : CountryDao) {
    suspend fun getCountry(): List<Country>{
        return fapi.getIndependentCountries()
    }
    suspend fun refreshFlags(flags: List<CountryInfo>) {
        cdao.addCountry(flags.map { country ->
            Countries(
               0,
                country.turkishName.toString(),
                country.flagUrl.toString(),
                country.region.toString()
            )
        })
    }
    suspend fun getAllCountry() : List<Countries> =
        withContext(Dispatchers.IO){
            return@withContext cdao.GetAllCountry()
        }
    suspend fun getRandomCountry() : Countries =
        withContext(Dispatchers.IO){
            return@withContext cdao.getRandomCountry()
        }
    suspend fun getThreeRandomCountryNames(selectedCountryName: String): List<Countries> =
        withContext(Dispatchers.IO){
            return@withContext cdao.getThreeRandomCountryNames(selectedCountryName)
        }

}
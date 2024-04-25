package com.yz3ro.flagquiz.data.repo

import com.yz3ro.flagquiz.data.datasource.FlagDataSource
import com.yz3ro.flagquiz.data.entity.Countries
import com.yz3ro.flagquiz.data.entity.Country
import com.yz3ro.flagquiz.data.entity.CountryInfo

class FlagRepository(private val fds : FlagDataSource) {
    suspend fun getCountry(): List<Country> = fds.getCountry()

    suspend fun refreshFlags(flags: List<CountryInfo>) = fds.refreshFlags(flags)

    suspend fun getAllCountry() : List<Countries> = fds.getAllCountry()
}
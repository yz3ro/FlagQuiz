package com.yz3ro.flagquiz.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.yz3ro.flagquiz.data.entity.Countries
import com.yz3ro.flagquiz.data.entity.CountryInfo
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
@ExperimentalCoroutinesApi
class CountryDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao : CountryDao
    private lateinit var database : DataBase

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),DataBase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getCountryDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun `insertAndGetAllCountries_success`() = runBlocking{
        val exampleCountry = Countries(1, "Turkey", "https://turkey.com", "Europe")
        dao.addCountry(listOf(exampleCountry))
        val countryList = dao.GetAllCountry()
        assert(countryList.contains(exampleCountry))
    }

    @Test
    fun `getRandomCountry_success`() = runBlocking {
        val exampleCountry = Countries(1, "Turkey", "https://turkey.com", "Europe")
        dao.addCountry(listOf(exampleCountry))

        val randomCountry = dao.getRandomCountry()

        assert(randomCountry == exampleCountry)
    }

    @Test
    fun `getThreeRandomCountryNames_success`() = runBlocking {
        val country1 = Countries(1, "Turkey", "https://turkey.com", "Europe")
        val country2 = Countries(2, "Germany", "https://flagcdn.com/w320/gr.png", "Europe")
        val country3 = Countries(3, "France", "https://flagcdn.com/w320/fr.png", "Europe")
        val country4 = Countries(4, "Japan", "https://flagcdn.com/w320/jp.png", "Asia")

        dao.addCountry(listOf(country1,country2,country3,country4))

        val threeRandomCountries = dao.getThreeRandomCountryNames("Turkey")

        assertTrue(threeRandomCountries.isNotEmpty())
        assertTrue(threeRandomCountries.size == 3)
    }

}
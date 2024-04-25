package com.yz3ro.flagquiz.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yz3ro.flagquiz.data.entity.Countries
import com.yz3ro.flagquiz.data.entity.Country
import com.yz3ro.flagquiz.data.entity.CountryInfo
import com.yz3ro.flagquiz.data.repo.FlagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor (private val repo : FlagRepository) : ViewModel() {
    private var dataLoaded = false

    private val _countryInfoList = MutableLiveData<List<CountryInfo>>()
    val countryInfoList: LiveData<List<CountryInfo>> = _countryInfoList
    init {
        if (!dataLoaded) {
            loadData()
            dataLoaded = true
        }
    }
    var countryList = MutableLiveData<List<Countries>>()

    fun getAllCountry() {
        CoroutineScope(Dispatchers.Main).launch{
            countryList.value = repo.getAllCountry()
        }
    }
    fun loadData(): Boolean {
        var success = false
        viewModelScope.launch {
            try {
                val countries = repo.getCountry()
                val countryInfoList = countries.map { country ->
                    CountryInfo(
                        turkishName = country.translations?.get("tur")?.get("official"),
                        flagUrl = country.flags["svg"],
                        region = country.region
                    )
                }
                repo.refreshFlags(countryInfoList)
                _countryInfoList.postValue(countryInfoList)
                success = true
            } catch (e: Exception) {
                Log.e("QuizViewModel", "Error loading and saving data to database: ${e.message}")
                success = false
            }
        }
        return success
    }
}

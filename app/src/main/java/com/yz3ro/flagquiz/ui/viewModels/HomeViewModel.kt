package com.yz3ro.flagquiz.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yz3ro.flagquiz.data.entity.CountryInfo
import com.yz3ro.flagquiz.data.repo.FlagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: FlagRepository) : ViewModel() {
    private var dataLoaded = false
    var isDataDownloaded = false
    private val _countryInfoList = MutableLiveData<List<CountryInfo>>()

    init {
        if (!dataLoaded) {
            loadData()
            dataLoaded = true
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
                        flagUrl = country.flags["png"],
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
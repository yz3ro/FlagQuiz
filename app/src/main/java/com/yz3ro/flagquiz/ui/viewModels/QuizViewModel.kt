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


    var countryList = MutableLiveData<List<Countries>>()

    fun getAllCountry() {
        CoroutineScope(Dispatchers.Main).launch{
            countryList.value = repo.getAllCountry()
        }
    }

    var randomCountryList = MutableLiveData<List<Countries>>()
    fun getRandomCountry() {
        CoroutineScope(Dispatchers.Main).launch{
            randomCountryList.value = listOf(repo.getRandomCountry())
        }
    }

    val randomCountryNameList = MutableLiveData<List<String>>()
    private var isRandomCountryNameRequested = false
    fun getRandomCountryName(selectedCountryName: String) {
        if (!isRandomCountryNameRequested) {
            isRandomCountryNameRequested = true
            Log.d("QuizViewModel", "Random country name request started.")
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val randomCountries = repo.getThreeRandomCountryNames(selectedCountryName)
                    val countryNames = randomCountries?.map { it.country_name } ?: emptyList()
                    randomCountryNameList.value = countryNames
                    Log.d("QuizViewModel", "Random country names: $countryNames")
                }finally {
                    isRandomCountryNameRequested = false
                    Log.d("QuizViewModel", "Random country name request finished.")
                }
            }
        }
    }

}

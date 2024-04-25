package com.yz3ro.flagquiz.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.yz3ro.flagquiz.R
import com.yz3ro.flagquiz.data.entity.CountryInfo
import com.yz3ro.flagquiz.databinding.FragmentQuizBinding
import com.yz3ro.flagquiz.ui.viewModels.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : Fragment() {
    private lateinit var binding : FragmentQuizBinding
    private val viewModel : QuizViewModel by viewModels()
    private val randomOptions = mutableListOf<String>()
    private lateinit var selectedCountry : String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_quiz, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.loadData()) {
            observeData()
        } else {
            viewModel.loadData()
            observeData()
        }
    }
    private fun observeData() {
        // LiveData'ı gözlemle ve gerekli işlemleri gerçekleştir
        viewModel.countryInfoList.observe(viewLifecycleOwner, Observer { countryInfoList ->
            Log.e("api", countryInfoList.toString())
        })
        viewModel.countryList.observe(viewLifecycleOwner, Observer {
            Log.e("room", it.toString())
        })
        viewModel.randomCountryList.observe(viewLifecycleOwner, Observer { countries ->
            for (country in countries) {
                 selectedCountry = country.country_name
                val flagUrl = country.flag_url
                val region = country.region
                Glide.with(this)
                    .load(flagUrl)
                    .into(binding.imgFlag)
                Log.e("random ",flagUrl)
               viewModel.getRandomCountryName(selectedCountry)
            }
        })
        viewModel.randomCountryNameList.observe(viewLifecycleOwner, Observer { countryNames ->
            if (countryNames.size >= 3) {
                val correctCountryName = selectedCountry
                setRandomButtonTexts(countryNames, correctCountryName)
            } else {

            }
        })
        viewModel.getRandomCountry()

    }
    private fun setRandomButtonTexts(countryNames: List<String>,correctCountryName:String) {
        randomOptions.clear()
        randomOptions.add(correctCountryName)
        randomOptions.addAll(countryNames.filter { it != correctCountryName })
        randomOptions.shuffle()
        binding.btnOption1.text = randomOptions[0]
        binding.btnOption2.text = randomOptions[1]
        binding.btnOption3.text = randomOptions[2]
        binding.btnOption4.text = correctCountryName
    }
    override fun onResume() {
        super.onResume()
        viewModel.getAllCountry()
    }
}
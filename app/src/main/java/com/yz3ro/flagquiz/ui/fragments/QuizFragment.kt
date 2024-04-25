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
import com.yz3ro.flagquiz.R
import com.yz3ro.flagquiz.data.entity.CountryInfo
import com.yz3ro.flagquiz.databinding.FragmentQuizBinding
import com.yz3ro.flagquiz.ui.viewModels.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : Fragment() {
    private lateinit var binding : FragmentQuizBinding
    private val viewModel : QuizViewModel by viewModels()
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
    }
    override fun onResume() {
        super.onResume()
        viewModel.getAllCountry()
    }
}
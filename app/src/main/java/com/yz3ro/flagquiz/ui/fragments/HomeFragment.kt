package com.yz3ro.flagquiz.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.yz3ro.flagquiz.R
import com.yz3ro.flagquiz.databinding.FragmentHomeBinding
import com.yz3ro.flagquiz.ui.viewModels.HomeViewModel
import com.yz3ro.flagquiz.util.navigatex
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!viewModel.isDataDownloaded) {
            viewModel.loadData()
            viewModel.isDataDownloaded = true
        }
        binding.button.setOnClickListener {
            Navigation.navigatex(it, R.id.action_homeFragment_to_quizFragment)
        }
    }
}
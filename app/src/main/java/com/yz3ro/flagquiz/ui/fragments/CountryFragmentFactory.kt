package com.yz3ro.flagquiz.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject

class CountryFragmentFactory @Inject constructor() : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            HomeFragment::class.java.name -> HomeFragment()
            QuizFragment::class.java.name -> QuizFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}